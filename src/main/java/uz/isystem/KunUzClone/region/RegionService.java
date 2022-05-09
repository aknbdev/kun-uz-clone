package uz.isystem.KunUzClone.region;
import java.util.List;

public interface RegionService {

    // create
    void create(RegionDto regionDto);
    // read
    RegionDto getOne(Integer id);
    List<RegionDto> getAll();
    // update
    void update(Integer id, RegionDto regionDto);
    // delete
    void delete(Integer id);
}
