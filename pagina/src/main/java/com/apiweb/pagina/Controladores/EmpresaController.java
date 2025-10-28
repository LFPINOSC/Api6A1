package com.apiweb.pagina.Controladores;

import com.apiweb.pagina.Entidades.Empresa;
import com.apiweb.pagina.Servicio.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") 

public class EmpresaController {

    private final EmpresaService empresaService;

    // GET empresa
    @GetMapping
    public ResponseEntity<Empresa> getEmpresa() {
        Optional<Empresa> empresa = empresaService.findFirst();
        return empresa.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // POST/PUT empresa con banners
    @PostMapping
    public ResponseEntity<Empresa> saveEmpresa(@RequestBody Empresa empresa) {
        Empresa saved = empresaService.saveFullEmpresa(empresa);
        return ResponseEntity.ok(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        Optional<Empresa> empresaExistente = empresaService.findById(id);
        if (empresaExistente.isPresent()) {
            Empresa updated = empresaService.updateEmpresa(id, empresa);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // DELETE empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        boolean deleted = empresaService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
