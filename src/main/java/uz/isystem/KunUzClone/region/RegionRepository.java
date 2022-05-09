package uz.isystem.KunUzClone.region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Query("select r from regions r where r.id = ?1 and r.deletedAt is null")
    Optional<Region> findByIdAndDeletedAtIsNull(Integer id);

    @Query("select r from regions r where r.deletedAt is null")
    List<Region> findAllByDeletedAtIsNull();
}
