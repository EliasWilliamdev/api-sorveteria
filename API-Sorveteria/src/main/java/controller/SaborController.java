package controller;

import model.Sabor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.SaborRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sabores")
public class SaborController {

    @Autowired
    private SaborRepository saborRepository;

    @GetMapping
    public List<Sabor> GetAllSabores() {
        return saborRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sabor> getSaborById(@PathVariable Long id) {
        Optional<Sabor> sabor = saborRepository.findById(id);
        return sabor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sabor createSabor(@RequestBody Sabor sabor) {
        return saborRepository.save(sabor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sabor> updateSabor(@PathVariable Long id, @RequestBody Sabor saborDetails) {
        Optional<Sabor> optionalSabor = saborRepository.findById(id);
        if (optionalSabor.isPresent()) {
            Sabor sabor = optionalSabor.get();
            sabor.setNome(saborDetails.getNome());
            return ResponseEntity.ok(saborRepository.save(sabor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSabor(@PathVariable Long id) {
        if (saborRepository.existsById(id)) {
            saborRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
