package geiffel.da4.issuetracker.issue;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import geiffel.da4.issuetracker.commentaire.Commentaire;
import geiffel.da4.issuetracker.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "code")
public class Issue{

    @Id
    private Long code;

    private String title;
    private String content;

    private Timestamp dateClosed;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User emitter;
    private Timestamp dateCreated;

    @OneToMany
    @JoinColumn(name = "idCommentaire")
    private List<Commentaire> commentaires;

    public Issue(long l, String theIssue, String theContent, User user, Timestamp from, Object o) {
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Issue(Long code, String title, String content, User emitter) {
        this.title = title;
        this.content = content;
        this.code = code;
        this.emitter = emitter;
    }

    public Issue() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Timestamp getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Timestamp dateClosed) {
        this.dateClosed = dateClosed;
    }

    public User getEmitter() {
        return emitter;
    }

    public void setEmitter(User emitter) {
        this.emitter = emitter;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(code, issue.code) && Objects.equals(title, issue.title) && Objects.equals(content, issue.content) && Objects.equals(dateClosed, issue.dateClosed) && Objects.equals(emitter, issue.emitter) && Objects.equals(dateCreated, issue.dateCreated) && Objects.equals(commentaires, issue.commentaires);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, content, dateClosed, emitter, dateCreated, commentaires);
    }
}
