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
@Table(name = "Perfil_Usuario")
public class Perfil_Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerfil_Usuario")
    private Integer idPerfilUsuario;
    
    @Column(name = "Tabla_Puntuacion_Mtms", nullable = false)
    private Long tablaPuntuacionMtms;
    
    @Column(name = "Tabla_Puntuacion_Lct", nullable = false)
    private Long tablaPuntuacionLct;
    
    @Column(name = "Tabla_Puntuacion_Scs", nullable = false)
    private Long tablaPuntuacionScs;
    
    @OneToOne
    @JoinColumn(name = "id_Usuario", nullable = false)
    private USUARIO usuario;
    
    public Integer getIdPerfilUsuario() {
        return idPerfilUsuario;
    }
    
    public void setIdPerfilUsuario(Integer idPerfilUsuario) {
        this.idPerfilUsuario = idPerfilUsuario;
    }
    
    public Long getTablaPuntuacionMtms() {
        return tablaPuntuacionMtms;
    }
    
    public void setTablaPuntuacionMtms(Long tablaPuntuacionMtms) {
        this.tablaPuntuacionMtms = tablaPuntuacionMtms;
    }
    
    public Long getTablaPuntuacionLct() {
        return tablaPuntuacionLct;
    }
    
    public void setTablaPuntuacionLct(Long tablaPuntuacionLct) {
        this.tablaPuntuacionLct = tablaPuntuacionLct;
    }
    
    public Long getTablaPuntuacionScs() {
        return tablaPuntuacionScs;
    }
    
    public void setTablaPuntuacionScs(Long tablaPuntuacionScs) {
        this.tablaPuntuacionScs = tablaPuntuacionScs;
    }
    
    public USUARIO getUsuario() {
        return usuario;
    }
    
    public void setUsuario(USUARIO usuario) {
        this.usuario = usuario;
    }

    
}