package training.afpa.cda24060.squartrnbn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import training.afpa.cda24060.squartrnbn.model.ApiError;
import training.afpa.cda24060.squartrnbn.model.User;
import training.afpa.cda24060.squartrnbn.service.UserService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* ══════════════════════════════════════
       ACCUEIL — liste des utilisateurs
       ══════════════════════════════════════ */
    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        try {
            List<User> listUsers = userService.getAllUsers();
            model.addAttribute("listUsers", listUsers);
            log.info("Utilisateurs récupérés : {}", listUsers.size());
        } catch (Exception e) {
            log.error("Impossible de charger la liste des utilisateurs : {}", e.getMessage());
            model.addAttribute("listUsers", List.of());
            model.addAttribute("errorMsg", "Impossible de charger la liste : " + e.getMessage());
        }
        return "home";
    }

    /* ══════════════════════════════════════
       FORMULAIRE CRÉATION
       ══════════════════════════════════════ */
    @GetMapping("/createuser")
    public String createUser(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "createuser";
    }

    /* ══════════════════════════════════════
       FORMULAIRE MODIFICATION
       ══════════════════════════════════════ */
    @GetMapping("/updateuser/{id}")
    public String updateUser(@PathVariable Integer id, Model model) {
        try {
            User user = userService.getUser(id);
            model.addAttribute("user", user);
        } catch (Exception e) {
            log.error("Utilisateur id={} introuvable : {}", id, e.getMessage());
            return "redirect:/?errorMsg=Utilisateur introuvable";
        }
        return "updateuser";
    }

    /* ══════════════════════════════════════
       SUPPRESSION
       ══════════════════════════════════════ */
    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            userService.deleteUser(id);
            ra.addFlashAttribute("successMsg", "Utilisateur supprimé avec succès.");
            log.info("Utilisateur id={} supprimé", id);
        } catch (Exception e) {
            log.error("Erreur suppression id={} : {}", id, e.getMessage());
            ra.addFlashAttribute("errorMsg", "Impossible de supprimer : " + e.getMessage());
        }
        return "redirect:/";
    }

    /* ══════════════════════════════════════
       SAUVEGARDE (création OU mise à jour)
       ══════════════════════════════════════ */
    @PostMapping("/saveuser")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam(value = "photo", required = false) MultipartFile photo,
                           @RequestParam(value = "rememberMe", required = false) String rememberMe,
                           Model model,
                           RedirectAttributes ra) {

        boolean isNew = (user.getId() == null);
        log.info("{} utilisateur : {}", isNew ? "Création" : "Mise à jour", user.getUsername());

        /* ── Remember Me token ── */
        if ("on".equals(rememberMe)) {
            String token = java.util.UUID.randomUUID().toString();
            user.setRememberToken(token);
            log.info("Remember-Me token généré pour {}", user.getUsername());
        }

        /* ── Appel service (création ou mise à jour) ── */
        ApiError apiError = userService.saveUserWithError(user, photo);

        if (apiError != null) {
            // On réaffiche le formulaire avec les erreurs
            String viewName = isNew ? "createuser" : "updateuser";

            // Erreurs de validation champ par champ
            if (apiError.getValidationErrors() != null && !apiError.getValidationErrors().isEmpty()) {
                model.addAttribute("fieldErrors", apiError.getValidationErrors());
                model.addAttribute("apiError", "Veuillez corriger les champs indiqués ci-dessous.");
            } else {
                // Erreur globale (email déjà pris, etc.)
                model.addAttribute("apiError", apiError.getMessage());
            }

            model.addAttribute("user", user);
            log.warn("Erreur API lors de la sauvegarde : {}", apiError.getMessage());
            return viewName;
        }

        ra.addFlashAttribute("successMsg",
                isNew ? "Utilisateur créé avec succès !" : "Modifications enregistrées !");
        return "redirect:/";
    }
}
