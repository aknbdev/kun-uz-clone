package uz.isystem.KunUzClone.user;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.isystem.KunUzClone.userType.UserTypeDto;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Integer id;
    @NotBlank(message = ("User Types is mandatory!"))
    private Collection<UserTypeDto> userTypes = new ArrayList<>();
    @NotBlank(message = ("User Firstname is mandatory!"))
    private String firstname;
    @NotBlank(message = ("User Lastname is mandatory!"))
    private String lastname;
    @NotBlank(message = ("User Username is mandatory!"))
    private String username;
    @NotBlank(message = ("User Password is mandatory!"))
    private String password;
    @NotBlank(message = ("User Contact is mandatory!"))
    private String contact;
    @NotBlank(message = ("User Email is mandatory!"))
    private String email;
}
