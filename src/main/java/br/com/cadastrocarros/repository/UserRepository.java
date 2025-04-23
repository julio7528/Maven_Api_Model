package br.com.cadastrocarros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cadastrocarros.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método para buscar um usuário pelo nome de usuário (username)
    Optional<User> findByUsername(String username);
}