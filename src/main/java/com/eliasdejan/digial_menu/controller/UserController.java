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
    public String showUpdateForm(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping("/add")
    public String showSignUpForm(User user) {
        return "users/add";
    }

    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/add";
        }

        userRepository.save(user);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            user.setId((int) id);
            return "users/edit";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }
}
