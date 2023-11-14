package geiffel.da4.issuetracker;

import geiffel.da4.issuetracker.commentaire.Commentaire;
import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.user.Fonction;
import geiffel.da4.issuetracker.user.User;
import geiffel.da4.issuetracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import geiffel.da4.issuetracker.commentaire.CommentaireRepository;
import geiffel.da4.issuetracker.issue.IssueRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IssueTrackerStudentsApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private CommentaireRepository commentaireRepository;

    public static void main(String[] args) {
        SpringApplication.run(IssueTrackerStudentsApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpBDD() {
        return (args) -> {
            List<User> users = new ArrayList<>(){{
                add(new User(1L, "Machin", Fonction.USER));
                add(new User(2L, "Chose", Fonction.USER));
                add(new User(3L, "Truc", Fonction.DEVELOPPER));
            }};

            List<Issue> issues = new ArrayList<>(){{
                add(new Issue(1L, "1st comm", "C'est ma 1re issue", new User(1L, "Machin", Fonction.USER)));
                add(new Issue(2L, "2nd comm", "C'est ma 2nde issue", new User(1L, "Machin", Fonction.USER)));
                add(new Issue(3L, "3e comm", "C'est ma 1re issue", new User(3L, "Truc", Fonction.DEVELOPPER)));
            }};

            List<Commentaire> commentaires = new ArrayList<>(){{
                add(new Commentaire(1L, new User(3L, "Truc", Fonction.DEVELOPPER), new Issue(2L, "2nd comm", "C'est ma 2nde issue", new User(1L, "Machin", Fonction.USER)), "1er commentaire"));
                add(new Commentaire(2L, new User(2L, "Chose", Fonction.USER), (new Issue(3L, "3e comm", "C'est ma 1re issue", new User(3L, "Truc", Fonction.DEVELOPPER))), "1er commentaire"));
                add(new Commentaire(3L, new User(2L, "Chose", Fonction.USER), new Issue(2L, "2nd comm", "C'est ma 2nde issue", new User(1L, "Machin", Fonction.USER)), "2nd commentaire"));
            }};
            userRepository.saveAll(users);
            issueRepository.saveAll(issues);
            commentaireRepository.saveAll(commentaires);

            //FAIRE LES SERIALIZERS !!!! (pour User et Commentaire (cf. Issue))
        };
    }

}
