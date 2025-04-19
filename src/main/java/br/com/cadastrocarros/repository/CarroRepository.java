package br.com.cadastrocarros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cadastrocarros.model.Carro;

@Repository
// Define a interface que estende JpaRepository para a entidade Carro
public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByMarca(String marca);
    List<Carro> findByAnoGreaterThan(int ano);
}
