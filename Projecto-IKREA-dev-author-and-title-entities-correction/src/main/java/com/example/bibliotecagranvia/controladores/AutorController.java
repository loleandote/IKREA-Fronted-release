package com.example.bibliotecagranvia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.bibliotecagranvia.entidades.Autor;
import com.example.bibliotecagranvia.persistencia.AutorRepositorio;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
public class AutorController {
    @Autowired
    private AutorRepositorio authorRepository;
    @GetMapping("/authors")
    public String showHomePage(Model model){
        model.addAttribute("autor", authorRepository.findAll());
        return "authors";
    }
    @GetMapping("/addAuthor")
    public String addAuthor(Autor autor){
        return "addAuthor";
    }
    @PostMapping("/addAuthor")
    public String addAuthorPost(@Valid Autor autor, BeanPropertyBindingResult result, Model model){
        if (result.hasErrors()) {
            return "addAuthor";
        }
        
        authorRepository.save(autor);
        return "redirect:/authors";
    }
    @GetMapping("/edit/{id}")
public String showUpdateForm(@PathVariable("id") long id, Model model) {
    Autor autor = authorRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Id de autor no valido:" + id));
    
    model.addAttribute("autor", autor);
    return "updateAuthor";
}
@PostMapping("/update/{id}")
public String updateUser(@PathVariable("id") long id, @Valid Autor autor, 
  BindingResult result, Model model) {
    if (result.hasErrors()) {
        autor.setId(id);
        return "updateAuthor";
    }
        
    authorRepository.save(autor);
    return "redirect:/authors";
}
    
@GetMapping("/delete/{id}")
public String deleteUser(@PathVariable("id") long id, Model model) {
    Autor autor = authorRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Id de autor no valido:" + id));
    authorRepository.delete(autor);
    return "redirect:/authors";
}

}
