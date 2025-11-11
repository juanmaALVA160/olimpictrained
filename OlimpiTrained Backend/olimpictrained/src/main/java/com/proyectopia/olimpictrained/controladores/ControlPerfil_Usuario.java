package com.proyectopia.olimpictrained.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectopia.olimpictrained.entidades.Perfil_Usuario;
import com.proyectopia.olimpictrained.servicios.ServiPerfil_Usuario;


@RestController
@RequestMapping("/api/perfil_usuarios")
public class ControlPerfil_Usuario {
    @Autowired
    private final ServiPerfil_Usuario serviPerfil_Usuario;

    // Inyección de dependencias del servicio de usuario (capa de negocio)
    public ControlPerfil_Usuario(ServiPerfil_Usuario serviPerfil_Usuario) {
        this.serviPerfil_Usuario = serviPerfil_Usuario;
    }

    /**
     * Obtiene la lista de todos los usuarios.
     * @return lista de usuarios con HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Perfil_Usuario>> obtenerTodosPerfil_Usuario() {
        List<Perfil_Usuario> perfil_Usuario = serviPerfil_Usuario.listarPerfil_Usuarios();
        return ResponseEntity.ok(perfil_Usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario.
     * @return usuario encontrado con HTTP 200, o HTTP 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Perfil_Usuario> obtenerPerfil_UsuarioPorId(@PathVariable Integer id) {
        Optional<Perfil_Usuario> Perfil_UsuarioOpt = serviPerfil_Usuario.obtenerPerfil_Usuario(id);
        if (Perfil_UsuarioOpt.isPresent()) {
            return ResponseEntity.ok(Perfil_UsuarioOpt.get());
        } else {
            // Retorna 404 Not Found si no se encuentra el usuario
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo usuario.
     * @param USUARIO datos del usuario a crear.
     * @return usuario creado con HTTP 201 (Created), o HTTP 400 si hay error en los datos.
     */
    @PostMapping
    public ResponseEntity<Perfil_Usuario> crearPerfil_Usuario(@RequestBody Perfil_Usuario perfil_Usuario) {
        try {
            Perfil_Usuario nuevoPerfil_Usuario = serviPerfil_Usuario.crearPerfil_Usuario(perfil_Usuario);
            // Retorna 201 Created y el usuario creado en el cuerpo
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPerfil_Usuario);
        } catch (Exception e) {
            // En caso de error (por ejemplo, dato inválido), retorna 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un usuario existente.
     * @param id ID del usuario a actualizar.
     * @param USUARIO datos actualizados del usuario.
     * @return usuario actualizado con HTTP 200, o HTTP 404/400 según corresponda.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Perfil_Usuario> actualizarPerfil_Usuario(@PathVariable Integer id, @RequestBody Perfil_Usuario pf) {
        Optional<Perfil_Usuario> existente = serviPerfil_Usuario.obtenerPerfil_Usuario(id);
        if (!existente.isPresent()) {
            // No existe el usuario: retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
        try {
            pf.setIdPerfilUsuario(id);
            Perfil_Usuario usuarioActualizado = serviPerfil_Usuario.crearPerfil_Usuario(pf);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            // Error en la actualización: retorna 400 Bad Request
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar.
     * @return HTTP 204 (No Content) si se eliminó, o HTTP 404 si no se encontró.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil_Usuario(@PathVariable Integer id) {
        Optional<Perfil_Usuario> existente = serviPerfil_Usuario.obtenerPerfil_Usuario(id);
        if (!existente.isPresent()) {
            // No existe el usuario: retorna 404
            return ResponseEntity.notFound().build();
        }
        serviPerfil_Usuario.eliminarPerfil_Usuario(id);
        // Retorna 204 No Content
        return ResponseEntity.noContent().build();
    }

    
    
}