package uz.isystem.KunUzClone.userType;
import java.util.List;

public interface UserTypeService {

    // create
    void create(UserTypeDto userTypeDto);
    // read
    UserTypeDto getOne(Integer id);
    List<UserTypeDto> getAll();
    // update
    void update(Integer id, UserTypeDto userTypeDto);
    // delete
    void delete(Integer id);
}