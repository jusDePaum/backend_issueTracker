package geiffel.da4.issuetracker.commentaire;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("commentaireJPA")
public class commentaireJPAService implements CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Override
    public List<Commentaire> getAll() {
        return commentaireRepository.findAll();
    }

    @Override
    public Commentaire getById(Long id) {
        Optional<Commentaire> Commentaire = commentaireRepository.findById(id);
        if (Commentaire.isPresent()) {
            return Commentaire.get();
        } else {
            throw new ResourceNotFoundException("Commentaire", id);
        }
    }


    @Override
    public Commentaire create(Commentaire newCommentaire) throws ResourceAlreadyExistsException {
        if (commentaireRepository.existsById(newCommentaire.getId())) {
            throw new ResourceAlreadyExistsException("Commentaire", newCommentaire.getId());
        }
        return commentaireRepository.save(newCommentaire);
    }

    @Override
    public void update(Long id, Commentaire updatedCommentaire) throws ResourceNotFoundException {
        if (!commentaireRepository.existsById(id)) {
            throw new ResourceNotFoundException("Commentaire", id);
        }
        updatedCommentaire.setId(id);
        commentaireRepository.save(updatedCommentaire);

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        commentaireRepository.deleteById(id);
    }

    @Override
    public List<Commentaire> getAllByIssueCode(Long id) {
        return null;
    }

    @Override
    public List<Commentaire> getAllByAuthorId(Long id) {
        return null;
    }
}
