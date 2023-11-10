package com.yasar.imagenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yasar.imagenes.model.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen,Integer>{
}
