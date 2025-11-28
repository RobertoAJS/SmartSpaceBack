package pe.edu.smartspace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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

    // === NUEVO: relación con Role (para hacer user.getRoles()) ===
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    // Nuevo1: mappedBy = "usuario" indica que en la clase Mueble hay un atributo llamado 'usuario'
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // IMPORTANTE: Evita que al pedir un usuario se traiga toda la base de datos de muebles
    private List<Mueble> muebles = new ArrayList<>();

    // Nuevo2: Un Usuario tiene muchos Diseños ===
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Lo mismo pero con disenos
    private List<Diseno> disenos = new ArrayList<>();


    // Métodos auxiliares para Spring Security
    public boolean isEnabled() {
        return this.statusUsuario;
    }

    public void setEnabled(boolean enabled) {
        this.statusUsuario = enabled;
    }

}



