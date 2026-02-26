package training.afpa.cda24060.squartrnbn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import training.afpa.cda24060.squartrnbn.config.CustomProperty;
import training.afpa.cda24060.squartrnbn.model.ApiError;
import training.afpa.cda24060.squartrnbn.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final WebClient     webClient;
    private final CustomProperty property;

    /* ─────────────────────────────────────────
       GET tous les utilisateurs
       ───────────────────────────────────────── */
    public List<User> getAllUsers() {
        List<User> users = webClient.get()
                .uri(property.getApiURL() + "/api/users")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();
        log.debug("getAllUsers OK — {} résultats", users != null ? users.size() : 0);
        return users;
    }

    /* ─────────────────────────────────────────
       GET un utilisateur par ID
       ───────────────────────────────────────── */
    public User getUser(Integer id) {
        return webClient.get()
                .uri(property.getApiURL() + "/api/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    /* ─────────────────────────────────────────
       DELETE
       ───────────────────────────────────────── */
    public void deleteUser(Integer id) {
        webClient.delete()
                .uri(property.getApiURL() + "/api/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        log.debug("deleteUser id={} OK", id);
    }

    /* ─────────────────────────────────────────
       SAVE (création ou mise à jour) + photo
       Retourne null si succès, ApiError si erreur API.
       ───────────────────────────────────────── */
    public ApiError saveUserWithError(User user, MultipartFile photo) {
        try {
            boolean isNew = (user.getId() == null);

            // ── Mot de passe vide = null (optionnel en update, évite @Size(min=8) sur "") ──
            if (user.getPassword() != null && user.getPassword().isBlank()) {
                user.setPassword(null);
            }

            // ── Si une photo est fournie, on l'upload d'abord ──
            if (photo != null && !photo.isEmpty()) {
                String uploadedPath = uploadPhoto(photo);
                if (uploadedPath != null) {
                    user.setPhotoPath(uploadedPath);
                }
            }

            // ── Appel API ──
            if (isNew) {
                webClient.post()
                        .uri(property.getApiURL() + "/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user)
                        .retrieve()
                        .bodyToMono(User.class)
                        .block();
            } else {
                webClient.put()
                        .uri(property.getApiURL() + "/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user)
                        .retrieve()
                        .bodyToMono(User.class)
                        .block();
            }

            return null; // Succès

        } catch (WebClientResponseException ex) {
            // L'API a renvoyé une réponse d'erreur (4xx / 5xx)
            log.warn("Erreur API {} lors de la sauvegarde : {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            try {
                return ex.getResponseBodyAs(ApiError.class);
            } catch (Exception parseEx) {
                // Impossible de parser, on crée une erreur générique
                ApiError fallback = new ApiError();
                fallback.setHttpStatusCode(ex.getStatusCode().value());
                fallback.setMessage(ex.getResponseBodyAsString());
                return fallback;
            }

        } catch (Exception ex) {
            log.error("Erreur inattendue lors de la sauvegarde : {}", ex.getMessage(), ex);
            ApiError fallback = new ApiError();
            fallback.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            fallback.setMessage("Erreur de communication avec le serveur. Veuillez réessayer.");
            return fallback;
        }
    }

    /* ─────────────────────────────────────────
       Upload de photo vers l'API
       ───────────────────────────────────────── */
    private String uploadPhoto(MultipartFile photo) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            byte[] bytes = photo.getBytes();
            builder.part("file", new ByteArrayResource(bytes) {
                @Override public String getFilename() { return photo.getOriginalFilename(); }
            }).contentType(MediaType.parseMediaType(
                    photo.getContentType() != null ? photo.getContentType() : "image/jpeg"
            ));

            // Appel endpoint upload de l'API (à adapter selon votre API)
            String path = webClient.post()
                    .uri(property.getApiURL() + "/api/users/photo")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Photo uploadée : {}", path);
            return path;

        } catch (Exception e) {
            log.warn("Upload photo échoué (non bloquant) : {}", e.getMessage());
            return null; // Non bloquant : si l'upload échoue, on continue sans photo
        }
    }
}
