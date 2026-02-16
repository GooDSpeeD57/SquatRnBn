package training.afpa.cda24060.squartrnbn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import training.afpa.cda24060.squartrnbn.model.User;
import training.afpa.cda24060.squartrnbn.repository.UserRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Integer id) {
             return userRepository.getUserById(id);
    }

    public List<User> getAllUsers() {
        Iterable<User> iterable = userRepository.getUsers();
        return StreamSupport.stream(iterable.spliterator(), false)
                .toList();
    }

    public void deleteUser(final Integer id) {
        userRepository.deleteUser(id);
    }

    public User saveUser(User user) {
        User saved;

        if(user.getNom() != null) {
            user.setNom(user.getNom().toLowerCase());
        }

        if(user.getId() == null) {
            saved = userRepository.createUser(user);
        } else {
            saved = userRepository.updateUser(user);
        }

        log.debug("User saved: {}", saved);
        return saved;
    }
}
