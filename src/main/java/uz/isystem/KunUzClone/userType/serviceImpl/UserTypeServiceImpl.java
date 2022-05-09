package uz.isystem.KunUzClone.userType.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.isystem.KunUzClone.userType.UserType;
import uz.isystem.KunUzClone.userType.UserTypeDto;
import uz.isystem.KunUzClone.userType.UserTypeRepository;
import uz.isystem.KunUzClone.userType.UserTypeService;
import uz.isystem.KunUzClone.www.exception.ApiRequestException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    private final ModelMapper mapper;

    @Override
    public void create(UserTypeDto userTypeDto) {
        UserType userType = mapper.map(userTypeDto, UserType.class);
        userType.setCreatedAt(LocalDateTime.now());
        userType.setStatus(true);
        userTypeRepository.save(userType);
    }

    @Override
    public UserTypeDto getOne(Integer id) {
        return mapper.map(getEntity(id), UserTypeDto.class);
    }

    @Override
    public List<UserTypeDto> getAll() {
        return getAllEntity()
                .stream()
                .map(userType -> mapper.map(userType, UserTypeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Integer id, UserTypeDto userTypeDto) {

        UserType old = getEntity(id);
        UserType userType = mapper.map(userTypeDto, UserType.class);
        old.setUpdatedAt(LocalDateTime.now());
        old.setName(userType.getName());
        userTypeRepository.save(old);
    }

    @Override
    public void delete(Integer id) {

        UserType userType = getEntity(id);
        userType.setDeletedAt(LocalDateTime.now());
        userTypeRepository.save(userType);
    }

    // |- SECONDARY FUNCTIONS -|

    public UserType getEntity(Integer id){
        return userTypeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(()-> new ApiRequestException("User Type not found!"));
    }

    public List<UserType> getAllEntity(){

        List<UserType> userTypes = userTypeRepository.findAllByDeletedAtIsNull();

        if (userTypes.isEmpty()){
            throw new ApiRequestException("There is no User Types!");
        }
        return userTypes;
    }
}