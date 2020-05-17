package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.models.User;
import be.iramps.florencemary._30jd_back.security.UserRoles;
import be.iramps.florencemary._30jd_back.repositories.UserRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Order(1)
@Component
public class AdminUsersDataLoader implements ApplicationRunner {
    private UserRepository userRepository;
    private String path = "./src/main/resources/admin_loader.xlsx";

    @Autowired
    public AdminUsersDataLoader(UserRepository userRepository)  { this.userRepository = userRepository; }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("1 - Init admin users");
        if (userRepository.findByUserRole(UserRoles.ADMIN).size() == 0) {
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(path));
                XSSFSheet sheet = workbook.getSheet("create");
                for (Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
                    Row row = it.next();
                    userRepository.save(new User(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), false, UserRoles.ADMIN));
                }
                System.out.println("ADMIN users created");
            } catch (FileNotFoundException fnf) {
                System.out.println("File path " + path + " is wrong or file is in use");
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
        } else {
            System.out.println("Admin users exist");
        }
    }
}
