package geiffel.da4.issuetracker.user;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User getById(Long id);

    User create(User newUser) throws ResourceAlreadyExistsException;

    void update(Long id, User updatedUser) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}
