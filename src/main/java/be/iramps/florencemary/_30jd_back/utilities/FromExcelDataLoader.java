package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.TaskPath;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskPathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

@Order(1)
@Component
public class FromExcelDataLoader implements ApplicationRunner {
    private TaskRepository taskRepository;
    private PathRepository pathRepository;
    private TaskPathRepository taskPathRepository;
    private final String paths = "./src/main/resources/populate/paths.txt";
    private final String folderPath = "./src/main/resources/populate/";
    private FileInputStream inputStream = null;
    private List<String> excelsPaths = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    public FromExcelDataLoader(TaskRepository taskRepository, PathRepository pathRepository, TaskPathRepository taskPathRepository) {
        this.taskRepository = taskRepository;
        this.pathRepository = pathRepository;
        this.taskPathRepository = taskPathRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Loading paths and tasks");
        Scanner sc = new Scanner(new File(paths));
        while (sc.hasNext()) {
            excelsPaths.add(folderPath + sc.nextLine());
        }
        for (String path : excelsPaths) {
            loadInDB(path);
        }
    }

    public void loadInDB(String path) {
        LOGGER.info("Excel file: " + path);
        XSSFWorkbook workbook = null;
        try {
            Path parcours = null;
            workbook = new XSSFWorkbook(new FileInputStream(path));
            XSSFSheet sheetT = workbook.getSheet("parcours");
            for (Iterator<Row> it = sheetT.rowIterator(); it.hasNext(); ) {
                Row row = it.next();
                parcours = pathRepository.findByPathName(row.getCell(0).getStringCellValue());  // si le parcours E -> récup le parcours
                if (parcours == null) { // sinon le crée avec les infos du fichier Excel
                    parcours = pathRepository.save(new Path(
                            row.getCell(0).getStringCellValue(),
                            row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "",
                            row.getCell(1) != null ? row.getCell(1).getStringCellValue() : ""
                    ));
                    LOGGER.info("Path " + parcours.getPathName() + " created.");
                }
            }
            // si on a un parcours mais qu'il est vide (pas de relation task-path E), on remplit avec les défis
            if (parcours != null && !taskPathRepository.findByPath(parcours).isPresent()) {
                Integer position = 0;
                XSSFSheet sheetP = workbook.getSheet("defis");
                for (Iterator<Row> it = sheetP.rowIterator(); it.hasNext(); ) {
                    Row row = it.next();
                    Task task = taskRepository.save(new Task(
                            row.getCell(0).getStringCellValue(),
                            row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "",
                            row.getCell(1) != null ? row.getCell(1).getStringCellValue() : ""
                    ));
                    position++;
                    taskPathRepository.save(new TaskPath(task, parcours, position));
                    parcours.setPathActive(true);
                }
                LOGGER.info(position + " tasks added to TaskPath relation.");
            }
        } catch (FileNotFoundException fnf) {
            LOGGER.warn("File path " + path + " is wrong or file is in use");
        } catch (IOException ioe) {
            LOGGER.warn("IOException ".toUpperCase() + ioe.getMessage());
        } catch (Exception e) {
            LOGGER.warn("Exception ".toUpperCase() + e.getMessage());
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException ioe) {
                LOGGER.warn(ioe.getMessage());
            }
        }
    }
}
