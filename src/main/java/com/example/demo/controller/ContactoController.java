package com.example.demo.controller;

import com.example.demo.model.Contacto; // Importamos el modelo
import com.example.demo.repository.ContactoRepository; // Importamos el repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contactos")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactoController {

    @Autowired
    private ContactoRepository repository;

    @GetMapping
    public List<Contacto> obtenerTodos() {
        return repository.findAllByOrderByIdDesc();
    }

    @PostMapping
    public Contacto guardar(@RequestBody Contacto contacto) {
        return repository.save(contacto);
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