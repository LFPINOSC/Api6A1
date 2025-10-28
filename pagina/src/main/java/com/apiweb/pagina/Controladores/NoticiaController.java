package com.apiweb.pagina.Controladores;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiweb.pagina.Entidades.Noticia;
import com.apiweb.pagina.Servicio.NoticiaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/noticia")
@RequiredArgsConstructor
public class NoticiaController {

    private final NoticiaService noticiaService;

   
   @GetMapping
    public ResponseEntity<List<Noticia>> getAllNoticias() {
        List<Noticia> noticias = noticiaService.listAll();
        return ResponseEntity.ok(noticias);
    }

    @PostMapping
    public ResponseEntity<Noticia> saveNoticia(@RequestBody Noticia noticia) {
        Noticia saved = noticiaService.saveFullNoticia(noticia);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Noticia> updateNoticia(@PathVariable Long id, @RequestBody Noticia noticia) {
        Optional<Noticia> updated = noticiaService.updateNoticia(id, noticia);
        return updated.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Noticia> getNoticiaById(@PathVariable Long id) {
        Optional<Noticia> noticia = noticiaService.findById(id);
        return noticia.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticias(@PathVariable Long id) {
        boolean deleted = noticiaService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

