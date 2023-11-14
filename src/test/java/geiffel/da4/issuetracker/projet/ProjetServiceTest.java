package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjetServiceTest {

    private ProjetService projetService;

    private List<Projet> projets;

    @BeforeEach
    void setUp(){
        projets = new ArrayList<>(){{
            add(new Projet (1L, "Projet n°1"));
            add(new Projet (2L, "Projet n°2"));
            add(new Projet (3L, "Projet n°3"));
        }};
        projetService = new ProjetLocalService(projets);
    }

    @Test
    void whenGettingAll_shouldReturn3(){
        assertEquals(3, projetService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame(){
        assertAll(
                () -> assertEquals(projets.get(0), projetService.getById(1L)),
                () -> assertEquals(projets.get(2), projetService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> projetService.getById(12L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> projetService.getById(4L))
        );
    }

    @Test
    void whenCreating_ShouldReturnSame(){
        Projet toCreate = new Projet(5L, "Projet n°5");

        assertEquals(toCreate, projetService.create(toCreate));
    }

    @Test
    void whenCreating_shouldBeInTheList() {
        Projet toCreate = new Projet(5L, "Projet n°5");
        projetService.create(toCreate);
        assertEquals(toCreate, projetService.getById(toCreate.getId()));
    }

    @Test
    void whenCreatingWithSameId_shouldThrowException(){
        Projet same_projet = projets.get(0);
        assertThrows(ResourceAlreadyExistsException.class, () -> projetService.create(same_projet));
    }
    
    @Test
    void whenUpdating_shouldModifyProjet(){
        Projet initial_Projet = projets.get(2);
        Projet new_Projet = new Projet(initial_Projet.getId(), "Updaté");

        projetService.update(new_Projet.getId(), new_Projet);
        Projet updated_Projet = projetService.getById(initial_Projet.getId());
        assertEquals(new_Projet, updated_Projet);
        assertTrue(projetService.getAll().contains(new_Projet));
    }

    @Test
    void whenUpdatingNonExisting_shouldThrowException() {
        Projet projet = projets.get(2);

        assertThrows(ResourceNotFoundException.class, ()->projetService.update(75L, projet));
    }
    
    @Test
    void whenDeletingExistingProjet_shouldNotBeInprojetsAnymore() {
        Projet projet = projets.get(1);
        Long id = projet.getId();
 
        projetService.delete(id);
        assertFalse(projetService.getAll().contains(projet));
    }

    @Test
    void whenDeletingNonExisting_shouldThrowException() {
        Long id = 68L;
 
        assertThrows(ResourceNotFoundException.class, ()->projetService.delete(id));
    }
}
