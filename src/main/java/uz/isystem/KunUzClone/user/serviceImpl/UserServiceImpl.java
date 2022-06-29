package uz.isystem.KunUzClone.user.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.isystem.KunUzClone.user.User;
import uz.isystem.KunUzClone.user.UserDto;
import uz.isystem.KunUzClone.user.UserRepository;
import uz.isystem.KunUzClone.user.UserService;
import uz.isystem.KunUzClone.www.exception.ApiRequestException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getEntityByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserTypes().stream().map(userType -> new SimpleGrantedAuthority(userType.getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getStatus(), true, true, true, authorities);
    }

    @Override
    public void create(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(true);
        userRepository.save(user);
    }

    @Override
    public UserDto getOne(Integer id) {
        return mapper.map(getEntity(id), UserDto.class);
    }

    @Override
    public List<UserDto> getAll() {
        return getAllEntity()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Integer id, UserDto userDto) {
        getEntity(id);
        User old = mapper.map(userDto, User.class);
        userRepository.save(old);
    }

    @Override
    public void delete(Integer id) {
        User user = getEntity(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    // |- SECONDARY FUNCTIONS -|
    public User getEntity(Integer id){
        return userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new ApiRequestException("User not found!"));
    }

    public List<User> getAllEntity(){
        List<User> users = userRepository.findAllByDeletedAtIsNull();
        if (users.isEmpty()){
            throw new ApiRequestException("There is no user");
        }
        return users;
    }

    public User getEntityByUsername(String username){
        Optional<User> optionalUser = userRepository.findByUsernameAndDeletedAtIsNull(username);
        if (optionalUser.isEmpty()){
            throw new ApiRequestException("User not found!");
        }
        return optionalUser.get();
    }
}
