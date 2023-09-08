package thiagoDRangel.interfaces;

import thiagoDRangel.models.User;

public interface IUser {
    User findById(Long id);
    User create(User newUser);

}
