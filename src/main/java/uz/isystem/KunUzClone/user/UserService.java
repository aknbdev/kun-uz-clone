package uz.isystem.KunUzClone.user;

import java.util.List;

public interface UserService {

    // create
    void create(UserDto userDto);
    // read
    UserDto getOne(Integer id);
    List<UserDto> getAll();
    // update
    void update(Integer id, UserDto userDto);
    //delete
    void delete(Integer id);
}
