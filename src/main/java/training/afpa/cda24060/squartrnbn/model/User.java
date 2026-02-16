package training.afpa.cda24060.squartrnbn.model;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    private String username;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String photoPath;
    private String password;
    private Role role;
}
