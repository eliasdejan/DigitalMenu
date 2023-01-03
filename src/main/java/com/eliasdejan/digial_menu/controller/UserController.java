package com.eliasdejan.digial_menu.controller;

import javax.validation.Valid;

import com.eliasdejan.digial_menu.model.User;
import com.eliasdejan.digial_menu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String showIndex(Model model) {
        // aceasta este functia care raspunde la /users/ si afiseaza o lista cu userii.
        model.addAttribute("users", userRepository.findAll()); //transmite pe frontend o lista cu toti userii prin variabila users
        model.addAttribute("User", new User()); //transmite pe frontend un user nou in caz ca se doreste crearea unui nou user
        return "users/index"; // se returneaza pagina users/index
    }

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
        user.setIsAdmin(false);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        // aceasta este functia care raspunde la /users/login si afiseaza formularul de login.
        model.addAttribute("User", new User()); //transmite pe frontend un user nou in caz ca se doreste crearea unui nou user
        return "users/login"; // se returneaza pagina users/login
    }

    @PostMapping("/login")
    public String loginUser(@Valid User user, BindingResult bindingResult, Model model) {
        // aceasta este functia care raspunde la /users/login si verifica daca userul exista in baza de date.
        if (bindingResult.hasErrors()) {
            return "users/login";
        }
        User userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB == null) {
            return "users/login";
        }
        if (userFromDB.getPassword().equals(user.getPassword())) {
            return "redirect:/users";
        }
        return "users/login";
    }

    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/index";
        }

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "/users/edit";
    }

    @PostMapping("edit/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            user.setId((int) id);
            return "/users/edit/"+id;
        }

        User oldUser = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        user.setPassword(oldUser.getPassword());

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/users";
    }
}
