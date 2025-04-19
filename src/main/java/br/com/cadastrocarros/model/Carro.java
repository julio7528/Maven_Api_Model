package br.com.cadastrocarros.model;

import java.util.Objects;

// Import the necessary annotations
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Define the entity
@Entity
// Define the table name
@Table(name = "carros")

// Define the class
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define a estratégia de geração de chave primária
    private Long id;
    private String marca;
    private String modelo;
    private int ano;

    public Carro() {
        // Default constructor
    }

    public Carro(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    // Override equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carro carro = (Carro) o;
        return Objects.equals(id, carro.id); // Compare by ID para a entidade Carro
    }
    // Override para gerar apenas o hash code do ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    // Formar o return do Carro com os dados
    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                '}';
    }
}
