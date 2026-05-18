package com.example.demo.controller;

import com.example.demo.model.Contacto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ContactoRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contactos")
@CrossOrigin(origins = "http://localhost:4200")
@SuppressWarnings("null")
public class ContactoController {

    @Autowired
    private ContactoRepository repository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Contacto>> obtenerTodos(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(repository.findByUsuarioIdOrderByIdDesc(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestParam Long usuarioId, @RequestBody Contacto contacto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        contacto.setUsuario(usuarioOpt.get());
        return ResponseEntity.ok(repository.save(contacto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Long id, @RequestBody Contacto detalles) {
        return repository.findById(id)
                .map(contacto -> {
                    contacto.setNombre(detalles.getNombre());
                    contacto.setTelefono(detalles.getTelefono());
                    contacto.setEmail(detalles.getEmail());
                    return ResponseEntity.ok(repository.save(contacto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}