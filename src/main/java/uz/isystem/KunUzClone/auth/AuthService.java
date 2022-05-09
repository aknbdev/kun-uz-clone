package uz.isystem.KunUzClone.auth;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    void register(RegistrationDto dto);
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
    void verification(String token);
}
