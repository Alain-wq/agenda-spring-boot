package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        // Validar nombre de usuario (solo letras)
        if (nuevoUsuario.getUsername() == null || !nuevoUsuario.getUsername().matches("^[a-zA-Z]+$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario solo puede contener letras.");
        }
        
        // Validar contraseña (minimo 8 caracteres)
        if (nuevoUsuario.getPassword() == null || nuevoUsuario.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contraseña debe tener al menos 8 caracteres.");
        }

        // Verificar si el usuario ya existe
        if (usuarioRepository.findByUsername(nuevoUsuario.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }
        
        // Guardar el nuevo usuario
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario credenciales) {
        Optional<Usuario> usuarioDb = usuarioRepository.findByUsername(credenciales.getUsername());
        
        // Verificar si existe y la contraseña coincide
        if (usuarioDb.isPresent() && usuarioDb.get().getPassword().equals(credenciales.getPassword())) {
            return ResponseEntity.ok(usuarioDb.get()); // Login exitoso
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
