package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.user.User;
import geiffel.da4.issuetracker.utils.LocalService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("ProjetLocal")
public class ProjetLocalService extends LocalService<Projet, Long> implements ProjetService{
    public ProjetLocalService(List<Projet> projets) {
        super(projets);
    }

    @Override
    public String getIdentifier() {
        return "id";
    }

    @Override
    public List<Projet> getAll() {
        return super.getAll();
    }

    @Override
    public Projet getById(Long id) {
        return this.getByIdentifier(id);
    }

    @Override
    public Projet create(Projet newProjet) throws ResourceAlreadyExistsException {
        try {
            getByIdentifier(newProjet.getId());
            throw new ResourceAlreadyExistsException("Projet", newProjet.getId());
        } catch (ResourceNotFoundException e) {
            super.allValues.add(newProjet);
            return newProjet;
        }
    }

    @Override
    public void delete(Long id) {
        IndexAndValue<Projet> found = this.findById(id);
        this.allValues.remove(found.value());
    }

    @Override
    public void update(Long id, Projet updatedProjet) throws ResourceNotFoundException {
        IndexAndValue<Projet> found = this.findById(id);
        this.allValues.remove(found);
        this.allValues.add(found.index(), updatedProjet);
    }

    public IndexAndValue<Projet> findById(Long id) {
        return super.findByProperty(id);
    }
}
