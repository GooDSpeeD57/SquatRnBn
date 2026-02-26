package training.afpa.cda24060.squartrnbn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/**
 * Représente le corps JSON d'erreur retourné par l'API Spring Boot.
 *
 * Exemple :
 * {
 *   "httpStatusCode": 400,
 *   "errorCode": "ERR_VALIDATION",
 *   "message": "Les données du formulaire sont invalides.",
 *   "validationErrors": {
 *     "email": "L'email doit être valide",
 *     "password": "Min. 8 caractères"
 *   }
 * }
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {
    private String httpStatus;
    private int    httpStatusCode;
    private String errorCode;
    private String error;
    private String message;
    private String path;

    /** Erreurs de validation champ par champ (clé = nom du champ, valeur = message d'erreur) */
    private Map<String, String> validationErrors;
}
