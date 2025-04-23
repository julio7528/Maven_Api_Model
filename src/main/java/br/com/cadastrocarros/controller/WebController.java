package br.com.cadastrocarros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.cadastrocarros.model.Carro;
import br.com.cadastrocarros.repository.CarroRepository;

@Controller // Use @Controller para retornar nomes de views
public class WebController {

    private final CarroRepository carroRepository;

    @Autowired
    public WebController(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @GetMapping("/carros/listar")
    public String mostrarListaCarros(Model model) {
        List<Carro> listaDeCarros = carroRepository.findAll();
        model.addAttribute("carros", listaDeCarros);
        return "lista-carros";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/carros/listar";
    }
    @GetMapping("/carros/novo")
    public String mostrarFormularioCadastro(Model model) {
         model.addAttribute("carro", new Carro()); // Adiciona um objeto Carro vazio para o form binding
         return "form-carro"; // Supõe que você criará uma form-carro.jsp
    }
}