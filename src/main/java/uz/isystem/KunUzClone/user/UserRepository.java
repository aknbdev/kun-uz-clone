package uz.isystem.KunUzClone.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from users u where u.id = ?1 and u.deletedAt is null")
    Optional<User> findByIdAndDeletedAtIsNull(Integer id);

    @Query("select u from users u where u.deletedAt is null")
    List<User> findAllByDeletedAtIsNull();

    @Query("select u from users u where u.username = ?1 and u.deletedAt is null")
    Optional<User> findByUsernameAndDeletedAtIsNull(String username);
}
