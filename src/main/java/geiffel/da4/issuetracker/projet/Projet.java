package geiffel.da4.issuetracker.projet;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;


public class Projet {

    private Long id;

    private String libelle;

    public Projet() {

    }

    public Projet(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projet projet = (Projet) o;
        return Objects.equals(id, projet.id) && Objects.equals(libelle, projet.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
