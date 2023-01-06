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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("message", model.asMap().get("message"));
        // aceasta este functia care raspunde la /users/ si afiseaza o lista cu userii.
        model.addAttribute("users", userRepository.findAll()); //transmite pe frontend o lista cu toti userii prin variabila users
        model.addAttribute("User", new User()); //transmite pe frontend un user nou in caz ca se doreste crearea unui nou user
        return "users/index"; // se returneaza pagina users/index
    }

    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult result, Model model,  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "users/index";
        }

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Item was added!");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        model.addAttribute("message", model.asMap().get("message"));
        return "/users/edit";
    }

    @PostMapping("edit/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            user.setId((int) id);
            return "/users/edit/"+id;
        }

        User oldUser = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        user.setPassword(oldUser.getPassword());

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "Item was edited!");

        return "redirect:/users";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        redirectAttributes.addFlashAttribute("message", "Item was deleted!");
        return "redirect:/users";
    }
}
