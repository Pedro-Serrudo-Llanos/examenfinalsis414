package com.example.Examen1.repository;

import com.example.Examen1.models.Rueda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuedaRepository extends JpaRepository <Rueda, Long>{
}