package com.proyectopia.olimpictrained.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectopia.olimpictrained.entidades.Perfil_Usuario;
import com.proyectopia.olimpictrained.repositorios.RepoPerfil_Usuario;

@Service
public class ServiPerfil_Usuario {
    @Autowired
    private RepoPerfil_Usuario repoPerfil_Usuario;

    public List<Perfil_Usuario> listarPerfil_Usuarios() {
        return repoPerfil_Usuario.findAll();
    }
    public Perfil_Usuario crearPerfil_Usuario(Perfil_Usuario u) {
        return repoPerfil_Usuario.save(u);
    }
    public Optional<Perfil_Usuario> obtenerPerfil_Usuario(int id) {
        return repoPerfil_Usuario.findById(id);
    }
    public void eliminarPerfil_Usuario(int id) {
        repoPerfil_Usuario.deleteById(id);
    }
    // Otros métodos según se requiera...

    

}

