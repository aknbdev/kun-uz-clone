package uz.isystem.KunUzClone.category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from categories c where c.id = ?1 and c.deletedAt is null")
    Optional<Category> findByIdAndDeletedAtIsNull(Integer id);

    @Query("select c from categories c where c.deletedAt is null")
    List<Category> findAllByDeletedAtIsNull();
}
