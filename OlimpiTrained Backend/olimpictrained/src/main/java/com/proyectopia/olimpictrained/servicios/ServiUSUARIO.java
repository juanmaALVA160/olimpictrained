package com.proyectopia.olimpictrained.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectopia.olimpictrained.entidades.USUARIO;
import com.proyectopia.olimpictrained.repositorios.RepoUSUARIO;

@Service
public class ServiUSUARIO {
    @Autowired
    private RepoUSUARIO repoUSUARIO;

    public List<USUARIO> listarUsuarios() {
        return repoUSUARIO.findAll();
    }
    public USUARIO crearUSUARIO(USUARIO u) {
        return repoUSUARIO.save(u);
    }
    public Optional<USUARIO> obtenerUsuario(int id) {
        return repoUSUARIO.findById(id);
    }
    public void eliminarUsuario(int id) {
        repoUSUARIO.deleteById(id);
    }
    // Otros métodos según se requiera...

}