package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Order(2)
@Component
public class FromExcelDataLoader implements ApplicationRunner {
    private TaskRepository taskRepository;
    private PathRepository pathRepository;
    private String path = "./src/main/resources/dataloader.xlsx";
    private FileInputStream inputStream = null;

    @Autowired
    public FromExcelDataLoader(TaskRepository taskRepository, PathRepository pathRepository) {
        this.taskRepository = taskRepository;
        this.pathRepository = pathRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("2 - Load tasks and paths from Excel file");
        if (taskRepository.count() == 0 && pathRepository.count() == 0) {
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(path));
                XSSFSheet sheetT = workbook.getSheet("tasks");
                for (Iterator<Row> it = sheetT.rowIterator() ; it.hasNext() ; ) {
                    Row row = it.next();
                    taskRepository.save(new Task(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue()));
                }
                XSSFSheet sheetP = workbook.getSheet("paths");
                for (Iterator<Row> it = sheetP.rowIterator() ; it.hasNext() ; ) {
                    Row row = it.next();
                    pathRepository.save(new Path(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue()));
                }
                System.out.println("Excel file loaded with success");
            } catch (FileNotFoundException fnf) {
                System.out.println("File path is wrong");
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            } finally {
                try {
                    if (workbook != null) workbook.close();
                    System.out.println("Workbook closed");
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }
        System.out.println("Data already loaded in database");
    }
}
