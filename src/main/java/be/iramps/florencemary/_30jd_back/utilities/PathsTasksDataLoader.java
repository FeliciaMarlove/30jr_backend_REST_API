package be.iramps.florencemary._30jd_back.utilities;

import be.iramps.florencemary._30jd_back.models.Path;
import be.iramps.florencemary._30jd_back.models.Task;
import be.iramps.florencemary._30jd_back.repositories.PathRepository;
import be.iramps.florencemary._30jd_back.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * For development and test purpose
 */
@Component
public class PathsTasksDataLoader implements ApplicationRunner {
    private TaskRepository taskRepository;
    private PathRepository pathRepository;

    @Autowired
    public PathsTasksDataLoader(TaskRepository taskRepository, PathRepository pathRepository) {
        this.taskRepository = taskRepository;
        this.pathRepository = pathRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (taskRepository.count() == 0) {
            taskRepository.save(new Task("Fais le bilan","Pourquoi as-tu commencé ce parcours ?", "Savoir pourquoi on s'engage dans quelque chose, c'est important pour garder la motivation jusqu'au bout ! Prends un carnet et note les raisons qui t'ont poussé.e.s à commencer aujourd'hui : quels sont tes problème aujourd'hui, que veux-tu changer, que veux tu atteindre ?"));
            taskRepository.save(new Task("Dresse un objectif","Où veux-tu être dans 30 jours ?", "Reprends ton carnet et dresse le portrait de ton objectif, à travers des mots, des dessins, des collages... Aucune limite ! Imagine le résultat auquel tu aspires sans te mettre de barrières. Il peut s'agir d'un objectif proche et concret, ou complètement fou, à long terme..."));
        }
        if (pathRepository.count() == 0) {
            pathRepository.save(new Path("Désencombrer sa garde-robe", "Trier ses vêtements et créer sa garde-robe capsule", "S'habiller plus facilement, plus rapidement, avec un budget shopping quasiment nul ? C'est la promesse de la garde-robe capsule ! Après avoir désencombré les vêtements superflus, tu pourras t'habiller en 5 minutes chrono, quelle que soit l'occasion."));
            pathRepository.save(new Path("Désencombrer son salon", "Trier ses objets loisirs, décoration, collections... pour une pièce à vivre plus sereine", "Dans ce parcours tu vas désencombrer de nombreuses catégories d'objets tels que ceux liés aux loisirs, ta bilbiothèque et tes autres médias, tes objets décoratifs..."));
            pathRepository.save(new Path("Désencombrement numérique", "Trier les objets immatériels et se débarrasser de ses mauvaises habitudes numériques", "Les objets numériques sont invisibles mais leur consommation énergétique est bien réelle ! Tu vas apprendre à trier et organiser tes emails, espaces de stockage en ligne, disques durs... après avoir fait du nettoyage. Tu vas mettre en place un système pratique pour retrouver facilement ce que tu cherches."));
            pathRepository.save(new Path("Désencombrer son mental", "Apaisir son esprit en triant les activités, les idées et les relations", "Après un bilan, tu vas apprendre à sélectionner avec soin les activités auxquelles tu prends part et les relations que tu maintiens, et à te vider la tête quand c'est nécessaire, pour un esprit plus léger !"));
            Path p = pathRepository.findById(1).get();
            Task gen1 = taskRepository.findById(1).get();
            Task gen2 = taskRepository.findById(2).get();
            p.getTasks().add(gen1);
            p.getTasks().add(gen2);
            pathRepository.save(p);
        }
    }
}
