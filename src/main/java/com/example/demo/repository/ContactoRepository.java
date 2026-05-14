package com.example.demo.repository;

import com.example.demo.model.Contacto; // Importamos el modelo
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findAllByOrderByIdDesc();
}