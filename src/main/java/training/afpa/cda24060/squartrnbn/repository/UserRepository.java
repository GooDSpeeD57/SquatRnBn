package training.afpa.cda24060.squartrnbn.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import training.afpa.cda24060.squartrnbn.config.CustomProperty;
import training.afpa.cda24060.squartrnbn.model.User;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository {

    private final CustomProperty property;
    private final WebClient webClient;

    public List<User> getUsers() {
        List<User> users = webClient.get()
                .uri(property.getApiURL() + "/user")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();

        log.debug("Get Users call OK");
        return users;
    }

    public User getUserById(Integer id) {
        return webClient.get()
                .uri(property.getApiURL() + "/user/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User createUser(User user) {
        User createdUser = webClient.post()
                .uri(property.getApiURL() + "/user")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .block();

        log.debug("Create User call OK");
        return createdUser;
    }

    public User updateUser(User user) {
        User updatedUser = webClient.put()
                .uri(property.getApiURL() + "/user/{id}", user.getId())
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .block();

        log.debug("Update User call OK");
        return updatedUser;
    }

    public void deleteUser(int id) {
        webClient.delete()
                .uri(property.getApiURL() + "/user/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.debug("Delete User call OK");
    }
}
