package br.com.cadastrocarros.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Nome da tabela no banco
public class User implements UserDetails { // Implementa UserDetails do Spring Security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // Carrega as roles junto com o usuário
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    private boolean enabled = true; // Campo para habilitar/desabilitar usuário

    // --- Construtores, Getters e Setters ---
    // (Omitidos por brevidade - gere-os pela sua IDE)

    public User() { } // Construtor padrão necessário pelo JPA

    public User(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // --- Implementação dos métodos de UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte as strings de roles (ex: "ROLE_USER", "ROLE_ADMIN")
        // para objetos GrantedAuthority que o Spring Security entende.
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Lógica customizada se necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Lógica customizada se necessário
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Lógica customizada se necessário
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}