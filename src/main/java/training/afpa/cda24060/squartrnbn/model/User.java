package training.afpa.cda24060.squartrnbn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer   id;
    private String    username;
    private String    nom;
    private String    prenom;
    private String    email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    private String    photoPath;
    private String    password;
    private String    rememberToken;
    private Role      role;
}
