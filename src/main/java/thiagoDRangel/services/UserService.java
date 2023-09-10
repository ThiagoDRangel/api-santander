package thiagoDRangel.services;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thiagoDRangel.exceptions.BusinessException;
import thiagoDRangel.exceptions.NotFoundException;
import thiagoDRangel.interfaces.IUser;
import thiagoDRangel.models.User;
import thiagoDRangel.repository.UserRepository;

import java.util.List;
import static java.util.Optional.ofNullable;

@Service
public class UserService implements IUser {

    private static final Long FIXED_ID = 1L;
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User create(User newUser) {
        ofNullable(newUser).orElseThrow(() -> new BusinessException("User to create must not be null."));
        ofNullable(newUser.getAccount()).orElseThrow(() -> new BusinessException("User account must not be null."));;
        ofNullable(newUser.getCard()).orElseThrow(() -> new BusinessException("User card must not be null"));

        this.validateChangeableId(newUser.getId(), "created");
        if (userRepository.existsByAccountNumber(newUser.getAccount().getNumber())) {
            throw new BusinessException("This account number already existis.");
        }
        if (userRepository.existsByCardNumber(newUser.getCard().getNumber())) {
            throw new BusinessException("This card number already exists");
        }
        return this.userRepository.save(newUser);
    }

    @Transactional
    public User update(Long id, User userToUpdate) {
        this.validateChangeableId(id, "updated");
        User userDB = this.findById(id);
        if (!userDB.getId().equals(userToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }
        userDB.setName(userToUpdate.getName());
        userDB.setAccount(userToUpdate.getAccount());
        userDB.setCard(userToUpdate.getCard());
        userDB.setFeatures(userToUpdate.getFeatures());
        userDB.setNews(userToUpdate.getNews());
        return this.userRepository.save(userDB);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        User userDB = this.findById(id);
        this.userRepository.delete(userDB);
    }

    private void validateChangeableId(Long id, String operation) {
        if (FIXED_ID.equals(id)) {
            throw new BusinessException("User with ID %d can not be %s.".formatted(FIXED_ID, operation));
        }
    }
}
