package training.afpa.cda24060.squartrnbn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import training.afpa.cda24060.squartrnbn.model.User;
import training.afpa.cda24060.squartrnbn.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "home";
    }

    @GetMapping(value = {"/createuser"})
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "createuser";
    }

    @GetMapping(value = {"/updateuser/{id}"})
    public String updateUser(@PathVariable final Integer id, Model model) {
        User user = userService.getUser(id);
        if (user.getDateNaissance() != null) {
            String dateNaissanceFormatted = user.getDateNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE);
            model.addAttribute("dateNaissanceFormatted", dateNaissanceFormatted);
        }

        model.addAttribute("user", user);
        return "updateuser";
    }

    @GetMapping(value = {"/deleteuser/{id}"})
    public ModelAndView deleteUser(@PathVariable final Integer id) {
        userService.deleteUser(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping(value = {"/saveuser"})
    public ModelAndView saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/");
    }
}
