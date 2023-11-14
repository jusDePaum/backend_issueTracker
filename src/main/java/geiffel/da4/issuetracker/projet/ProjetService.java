package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjetService {

    void update(Long id, Projet newProjet) ;

    List<Projet> getAll();

    Projet getById(Long id);

    Projet create(Projet newProjet) throws ResourceAlreadyExistsException;

    void delete(Long id);
}
