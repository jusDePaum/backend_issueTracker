package geiffel.da4.issuetracker.commentaire;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.issue.IssueEmbeddedJSONSerializer;
import geiffel.da4.issuetracker.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Commentaire {

    @Id
    @NotNull
    private Long id;
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "idIssue", referencedColumnName = "code")
    @JsonSerialize(using = IssueEmbeddedJSONSerializer.class)
    private Issue issue;

    public Commentaire(Long id, User author, Issue issue, String contenu ) {
        this.id = id;
        this.contenu = contenu;
        this.author = author;
        this.issue = issue;
    }

    public Commentaire() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Long getAuthorId(){
        return this.author.getId();
    }

    public Long getIssueCode(){
        return this.issue.getCode();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return Objects.equals(id, that.id) && Objects.equals(contenu, that.contenu) && Objects.equals(author, that.author) && Objects.equals(issue, that.issue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contenu, author, issue);
    }
}
