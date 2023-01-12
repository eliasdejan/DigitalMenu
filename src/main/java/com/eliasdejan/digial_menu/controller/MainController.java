package com.eliasdejan.digial_menu.controller;

import com.eliasdejan.digial_menu.model.User;
import com.eliasdejan.digial_menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegister(Model model) {
        // aceasta este functia care raspunde la /users/register si afiseaza formularul de inregistrare.
        model.addAttribute("User", new User()); //transmite pe frontend un user nou in caz ca se doreste crearea unui nou user
        return "users/register"; // se returneaza pagina users/register
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        // aceasta este functia care raspunde la /users/register si adauga un user nou in baza de date.
        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        boolean isAdmin = userRepository.count() == 0;

        user.setIsAdmin(isAdmin);
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "users/logout";
    }
}
