package uz.isystem.KunUzClone.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDto {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String checkPassword;
    private String contact;
    private String email;
}
