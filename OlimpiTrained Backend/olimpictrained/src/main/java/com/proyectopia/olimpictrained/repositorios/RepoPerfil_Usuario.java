package com.proyectopia.olimpictrained.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectopia.olimpictrained.entidades.Perfil_Usuario;


@Repository
public interface RepoPerfil_Usuario extends JpaRepository<Perfil_Usuario, Integer> {
    
}
