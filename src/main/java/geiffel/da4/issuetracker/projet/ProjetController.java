package geiffel.da4.issuetracker.projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    private ProjetService projetService;

    @Autowired
    public ProjetController(ProjetService projetService){
        this.projetService = projetService;
    }

    @GetMapping
    public List<Projet> getAll(){
        return projetService.getAll();
    }

    @GetMapping("/{id}")
    public Projet getById(@PathVariable Long id){
        return projetService.getById(id);
    }

    @PostMapping
    public ResponseEntity createProjet(@RequestBody Projet projet){
        Projet createdProjet = projetService.create(projet);
        return ResponseEntity.status(201).header("Location", "/projets/"+createdProjet.getId().toString()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProjet(@PathVariable Long id, @RequestBody Projet projet){
        projetService.update(id, projet);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProjet(@PathVariable Long id){
        projetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
