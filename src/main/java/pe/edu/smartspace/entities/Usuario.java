package pe.edu.smartspace.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "usuarios")
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    // Campo real de login
    @Column(name = "username", length = 35,  nullable = false)
    private String username;

    @Column(name = "nombre", length = 100,  nullable = false)
    private String nombre;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "password",length = 300, nullable = false)
    private String password;

    @Column(name = "statusUsuario",nullable = false)
    private boolean statusUsuario;

    // === NUEVO: relación con Role (para que puedas hacer user.getRoles()) ===
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Long idUsuario, String username, String nombre, String email, String password, boolean statusUsuario) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.statusUsuario = statusUsuario;
    }

    // Métodos auxiliares para Spring Security
    public boolean isEnabled() {
        return this.statusUsuario;
    }

    public void setEnabled(boolean enabled) {
        this.statusUsuario = enabled;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(boolean statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}



