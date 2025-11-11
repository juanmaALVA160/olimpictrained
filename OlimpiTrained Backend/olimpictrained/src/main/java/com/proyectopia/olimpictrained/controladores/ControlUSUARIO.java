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

import com.proyectopia.olimpictrained.entidades.USUARIO;
import com.proyectopia.olimpictrained.servicios.ServiUSUARIO;


@RestController
@RequestMapping("/api/usuarios")
public class ControlUSUARIO {
    @Autowired
    private final ServiUSUARIO serviUSUARIO;

    // Inyección de dependencias del servicio de usuario (capa de negocio)
    public ControlUSUARIO(ServiUSUARIO serviUSUARIO) {
        this.serviUSUARIO = serviUSUARIO;
    }

    /**
     * Obtiene la lista de todos los usuarios.
     * @return lista de usuarios con HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<USUARIO>> obtenerTodosUSUARIO() {
        List<USUARIO> usuario = serviUSUARIO.listarUsuarios();
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario.
     * @return usuario encontrado con HTTP 200, o HTTP 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<USUARIO> obtenerUSUARIOPorId(@PathVariable Integer id) {
        Optional<USUARIO> USUARIOOpt = serviUSUARIO.obtenerUsuario(id);
        if (USUARIOOpt.isPresent()) {
            return ResponseEntity.ok(USUARIOOpt.get());
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
    public ResponseEntity<USUARIO> crearUSUARIO(@RequestBody USUARIO usuario) {
        try {
            USUARIO nuevoUSUARIO = serviUSUARIO.crearUSUARIO(usuario);
            // Retorna 201 Created y el usuario creado en el cuerpo
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUSUARIO);
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
    public ResponseEntity<USUARIO> actualizarUSUARIO(@PathVariable Integer id, @RequestBody USUARIO usuario) {
        Optional<USUARIO> existente = serviUSUARIO.obtenerUsuario(id);
        if (!existente.isPresent()) {
            // No existe el usuario: retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
        try {
            usuario.setIdUsuario(id);
            USUARIO usuarioActualizado = serviUSUARIO.crearUSUARIO(usuario);
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
    public ResponseEntity<Void> eliminarUSUARIO(@PathVariable Integer id) {
        Optional<USUARIO> existente = serviUSUARIO.obtenerUsuario(id);
        if (!existente.isPresent()) {
            // No existe el usuario: retorna 404
            return ResponseEntity.notFound().build();
        }
        serviUSUARIO.eliminarUsuario(id);
        // Retorna 204 No Content
        return ResponseEntity.noContent().build();
    }
/*
    @GetMapping("/login")
    public ResponseEntity<USUARIO> login(@RequestParam String CorreoElectronico, @RequestParam String Contrasena) {
        USUARIO u = repoUSUARIO.findByCorreoElectronico(CorreoElectronico);
        if (u != null && u.getContrasena().equals(Contrasena)) {
            return ResponseEntity.ok(u);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
*/
}