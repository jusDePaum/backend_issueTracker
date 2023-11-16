package geiffel.da4.issuetracker.issue;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("issueJPA")
public class IssueJPAService implements IssueService {

    private IssueRepository issueRepository;

    @Autowired
    public IssueJPAService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

    @Override
    public Issue getByCode(Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if(issue.isPresent())
        {
            return issue.get();
        }
        else
        {
            throw new ResourceNotFoundException("Issue", id);
        }
    }

    @Override
    public Issue create(Issue newIssue) throws ResourceAlreadyExistsException {
        if(issueRepository.existsById(newIssue.getCode())){
            throw new ResourceAlreadyExistsException("Issue", newIssue.getCode());
        }
        return issueRepository.save(newIssue);
    }

    @Override
    public Issue update(Long id, Issue updatedIssue) throws ResourceNotFoundException {
        Optional<Issue> issueAModif = issueRepository.findById(id);
        if(issueAModif.isPresent()){
            Issue issue = issueAModif.get();
            issue.setContent(updatedIssue.getContent());
            issue.setEmitter(updatedIssue.getEmitter());
            issue.setTitle(updatedIssue.getTitle());
            issue.setDateClosed(updatedIssue.getDateClosed());
            issue.setDateClosed(updatedIssue.getDateCreated());
            issueRepository.save(issue);
            return issue;
        }
        throw new ResourceNotFoundException("", id);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        issueRepository.deleteById(id);
    }
}
