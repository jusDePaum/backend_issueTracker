package geiffel.da4.issuetracker.projet;


import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.issuetracker.exceptions.ExceptionHandlingAdvice;
import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.user.Fonction;
import geiffel.da4.issuetracker.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = ProjetController.class)
@Import(ExceptionHandlingAdvice.class)
public class ProjetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjetService projetService;

    private List<Projet> projets;

    @BeforeEach
    void setUp() {
        projets = new ArrayList<>() {{
            add(new Projet(1L, "1er projet"));
            add(new Projet(2L, "2e projet"));
            add(new Projet(3L, "3e projet"));
            add(new Projet(14L, "4e projet"));
            add(new Projet(7L, "5e projet"));
            add(new Projet(28L, "6e projet"));
        }};
        when(projetService.getAll()).thenReturn(projets);
        when(projetService.getById(7L)).thenReturn(projets.get(4));
        when(projetService.getById(49L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenGettingAll_shouldGet6_andBe200() throws Exception {
        mockMvc.perform(get("/projets")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(6))
        ).andDo(print());
    }

    @Test
    void whenGettingId7L_shouldReturnSame() throws Exception{
        mockMvc.perform(get("/projets/7")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(7))
        ).andExpect(jsonPath("$.libelle", is("5e projet"))
        ).andReturn();
    }

    @Test
    void whenGettingUnexistingId_should404() throws Exception {
        mockMvc.perform(get("/projets/49")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreatingNew_shouldReturnLink_andShouldBeStatusCreated() throws Exception {
        Projet new_projet = new Projet(89L, "nouveau");
        ArgumentCaptor<Projet> projet_received = ArgumentCaptor.forClass(Projet.class);
        when(projetService.create(any())).thenReturn(new_projet);

        mockMvc.perform(post("/projets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new_projet))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/projets/"+new_projet.getId())
        ).andDo(print());

        verify(projetService).create(projet_received.capture());
        assertEquals(new_projet, projet_received.getValue());
    }

    @Test
    void whenCreatingWithExistingId_should404() throws Exception {
        when(projetService.create(any())).thenThrow(ResourceAlreadyExistsException.class);
        mockMvc.perform(post("/projets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(this.projets.get(2)))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReceiveUserToUpdate_andReturnNoContent() throws Exception {
        Projet initial_projet = projets.get(1);
        Projet updated_projet = new Projet(initial_projet.getId(), "updated");
        ArgumentCaptor<Projet> projet_received = ArgumentCaptor.forClass(Projet.class);

        mockMvc.perform(put("/projets/"+initial_projet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated_projet))
        ).andExpect(status().isNoContent());

        verify(projetService).update(anyLong(), projet_received.capture());
        assertEquals(updated_projet, projet_received.getValue());
    }

    @Test
    void whenDeletingExisting_shouldCallServiceWithCorrectId_andSendNoContent() throws Exception {
        Long id = 28L;

        mockMvc.perform(delete("/projets/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(projetService).delete(id_received.capture());
        assertEquals(id, id_received.getValue());
    }
}
