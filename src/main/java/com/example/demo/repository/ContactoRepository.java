package com.example.demo.repository;

import com.example.demo.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findAllByOrderByIdDesc();

    List<Contacto> findByUsuarioIdOrderByIdDesc(Long usuarioId);
}