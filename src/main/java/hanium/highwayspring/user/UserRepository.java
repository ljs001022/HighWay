package hanium.highwayspring.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long user_no);
    List<User> findAll();

}
