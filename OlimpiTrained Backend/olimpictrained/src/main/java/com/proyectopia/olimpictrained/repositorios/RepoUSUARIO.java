package com.proyectopia.olimpictrained.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectopia.olimpictrained.entidades.USUARIO;



@Repository
public interface RepoUSUARIO extends JpaRepository<USUARIO, Integer> {
    // Método adicional para login
//    USUARIO findByCorreoElectronico(String CorreoElectronico);
}