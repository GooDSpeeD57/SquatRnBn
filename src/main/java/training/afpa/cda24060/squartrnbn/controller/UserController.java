package training.afpa.cda24060.squartrnbn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import training.afpa.cda24060.squartrnbn.model.User;
import training.afpa.cda24060.squartrnbn.service.UserService;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        log.info("Nombre d'utilisateurs récupérés = {}", listUsers.size());
        return "home";
    }

    @GetMapping(value = {"/createuser"})
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        log.info("Création d'un nouvel utilisateur (objet vide créé)");
        return "createuser";
    }

    @GetMapping("/updateuser/{id}")
    public String updateUser(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        if (user.getDateNaissance() != null) {
            log.info("DATE WEB pour l'utilisateur {} = {}", user.getUsername(), user.getDateNaissance());
        } else {
            log.warn("L'utilisateur {} n'a pas de date de naissance définie", user.getUsername());
        }
        model.addAttribute("user", user);
        return "updateuser";
    }

    @GetMapping(value = {"/deleteuser/{id}"})
    public ModelAndView deleteUser(@PathVariable final Integer id) {
        userService.deleteUser(id);
        log.info("Utilisateur id={} supprimé", id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping(value = {"/saveuser"})
    public ModelAndView saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        log.info("Utilisateur {} enregistré / mis à jour", user.getUsername());
        return new ModelAndView("redirect:/");
    }
}