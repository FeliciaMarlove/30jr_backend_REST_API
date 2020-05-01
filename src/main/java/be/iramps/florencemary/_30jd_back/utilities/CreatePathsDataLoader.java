package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * For development and test purpose
 */
@Order(3)
@Component
public class CreatePathsDataLoader implements ApplicationRunner {
    private PathRepository pathRepository;
    private TaskRepository taskRepository;

    @Autowired
    public CreatePathsDataLoader(PathRepository pathRepository, TaskRepository taskRepository) {
        this.pathRepository = pathRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("3 - Creating paths filled with tasks");
        if (pathRepository.count() != 0 && taskRepository.count() != 0) {
            // TO CHANGE FOR PRODUCTION------------------------------------------------------
            Path testPath = pathRepository.findById(1).get();
            if (testPath.getTasks().size() == 0) {
                for(int i = 0 ; i < taskRepository.count() ; i ++) {
                    testPath.getTasks().add(taskRepository.findById(i+1).get());
                }
                pathRepository.save(testPath);
                System.out.println("Path filled");
            } else {
                System.out.println("Path already filled");
            }
            System.out.println("PATH SIZE: " + testPath.getTasks().size()+ " | TASKS LIST: ");
            System.out.print(testPath.getTasks());
            // TO CHANGE FOR PRODUCTION------------------------------------------------------
        } else {
            System.out.println("No paths or tasks in database");
        }
    }
}
