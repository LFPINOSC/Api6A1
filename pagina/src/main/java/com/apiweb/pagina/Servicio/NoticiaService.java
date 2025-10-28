package com.apiweb.pagina.Servicio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiweb.pagina.Entidades.Noticia;
import com.apiweb.pagina.Entidades.NoticiaImagen;
import com.apiweb.pagina.Repositorios.NoticiasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticiaService {

    private final NoticiasRepository noticiasRepository;
    @Autowired
    private Environment env;

   @Transactional
    public Noticia saveFullNoticia(Noticia noticia) {
        Noticia noticia2 = new Noticia();
        noticia2.setTitulo(noticia.getTitulo());
        noticia2.setDescripcion(noticia.getDescripcion());
        noticia2.setEstadoNoticia(1);

        List<NoticiaImagen> noticiasImages = new ArrayList<>();

        if (noticia.getNoticiaImagens() != null) {
            for (NoticiaImagen bDto : noticia.getNoticiaImagens()) {
                NoticiaImagen imagen = new NoticiaImagen();
                imagen.setSecuencial(null);
                String url = bDto.getUrl();
                if (url != null && url.startsWith("data:image/")) {
                    // Es base64
                    try {
                        String filePath = guardarImagenBase64EnServidor(url);
                        imagen.setUrl(filePath); // Guarda solo la ruta relativa o completa
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue; // Saltar si hay error
                    }
                } else {
                    // Es una URL externa o local ya válida
                    imagen.setUrl(url);
                }

                imagen.setNoticia(noticia2); // relación inversa
                noticiasImages.add(imagen);
            }
        }

        noticia2.setNoticiaImagens(noticiasImages);
        return noticiasRepository.save(noticia2);
    }
    @Transactional
    public Optional<Noticia> updateNoticia(Long id, Noticia noticiaActualizada) {
        return noticiasRepository.findById(id).map(noticiaExistente -> {
            noticiaExistente.setTitulo(noticiaActualizada.getTitulo());
            noticiaExistente.setDescripcion(noticiaActualizada.getDescripcion());

            // Actualiza las imágenes: borrar las anteriores y agregar nuevas sin reemplazar la lista
            List<NoticiaImagen> listaActual = noticiaExistente.getNoticiaImagens();
            listaActual.clear();  // borra las referencias, y gracias a orphanRemoval se eliminan de la BD

            if (noticiaActualizada.getNoticiaImagens() != null) {
                for (NoticiaImagen imagenNueva : noticiaActualizada.getNoticiaImagens()) {
                    NoticiaImagen imagen = new NoticiaImagen();

                    if (imagenNueva.getUrl().startsWith("data:image/")) {
                        try {
                            String filePath = guardarImagenBase64EnServidor(imagenNueva.getUrl());
                            imagen.setUrl(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        }
                    } else {
                        imagen.setUrl(imagenNueva.getUrl());
                    }

                    imagen.setNoticia(noticiaExistente);
                    listaActual.add(imagen);
                }
            }

            return noticiasRepository.save(noticiaExistente);
        });
    }


    public String guardarImagenBase64EnServidor(String base64) throws IOException {
        // Directorio donde se guardarán las imágenes
        String directorioDestino = "C:/imagenes/noticias/";
        
        // Asegurarse de que el directorio existe
        File directorio = new File(directorioDestino);
        if (!directorio.exists()) {
            directorio.mkdirs(); // crear si no existe
        }

        // Separar el encabezado del base64 (ej: data:image/png;base64,...)
        String[] partes = base64.split(",");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato base64 inválido");
        }

        String base64Data = partes[1];
        byte[] imagenBytes = Base64.getDecoder().decode(base64Data);

        // Crear nombre único para la imagen
        String nombreArchivo = "imagen_" + System.currentTimeMillis() + ".png";
        String rutaCompleta = directorioDestino + nombreArchivo;

        // Guardar archivo
        try (FileOutputStream fos = new FileOutputStream(rutaCompleta)) {
            fos.write(imagenBytes);
        }

        // Devolver ruta relativa para la base de datos
        String baseUrl = env.getProperty("servidorImagens"); // "http://localhost:8081"
        String urlCompleta = baseUrl + "/imagenes/noticias/" + nombreArchivo;
        return urlCompleta;
    }

    public List<Noticia> listAll() {
        return noticiasRepository.findAll();
    }

    public Optional<Noticia> findById(Long id) {
        return noticiasRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (noticiasRepository.existsById(id)) {
            noticiasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
