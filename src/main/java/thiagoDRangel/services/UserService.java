package thiagoDRangel.services;

import org.springframework.stereotype.Service;
import thiagoDRangel.interfaces.IUser;
import thiagoDRangel.models.User;
import thiagoDRangel.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserService implements IUser {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User newUser) {
        if (userRepository.existsByAccountNumber(newUser.getAccount().getNumber())) {
            throw new IllegalArgumentException("This account number already exists.");
        }
        return userRepository.save(newUser);
    }
}
