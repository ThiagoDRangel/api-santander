package thiagoDRangel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thiagoDRangel.models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
