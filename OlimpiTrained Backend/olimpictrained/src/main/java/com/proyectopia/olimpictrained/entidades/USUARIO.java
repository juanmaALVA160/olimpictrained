package com.proyectopia.olimpictrained.entidades;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class USUARIO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column( nullable = false)
    private String NombreCompleto;

    @Column( name = "correo_electronico", nullable = false)
    private String CorreoElectronico;

    @Column( nullable = false)
    private String Contrasena;

    @Column (nullable = false)
    private String Grado;

    @OneToOne
    @JoinColumn(name = "idPerfil_Usuario", nullable = false)
    private Perfil_Usuario perfilUsuario;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.NombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.CorreoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        this.Contrasena = contrasena;
    }

    public String getGrado() {
        return Grado;
    }

    public void setGrado(String grado) {
        this.Grado = grado;
    }

    public Perfil_Usuario getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(Perfil_Usuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    


}
