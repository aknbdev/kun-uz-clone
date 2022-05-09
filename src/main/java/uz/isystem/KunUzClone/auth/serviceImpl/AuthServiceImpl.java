package uz.isystem.KunUzClone.auth.serviceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.isystem.KunUzClone.auth.AuthService;
import uz.isystem.KunUzClone.auth.RegistrationDto;
import uz.isystem.KunUzClone.mail.MailSenderService;
import uz.isystem.KunUzClone.security.CustomAuthenticationFilter;
import uz.isystem.KunUzClone.user.User;
import uz.isystem.KunUzClone.user.UserRepository;
import uz.isystem.KunUzClone.userType.UserType;
import uz.isystem.KunUzClone.userType.UserTypeRepository;
import uz.isystem.KunUzClone.www.exception.ApiRequestException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final UserTypeRepository userTypeRepository;
    private final CustomAuthenticationFilter authenticationFilter;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    @Value("${link.address}")
    private String address;
    @Override
    public void register(RegistrationDto dto) {

        if (!dto.getPassword().equals(dto.getCheckPassword())){
            throw new ApiRequestException("Password should be the same!");
        }
        checkEntity(dto.getUsername());
        User user = mapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Collection<UserType> userTypes = new ArrayList<>();
        userTypes.add(isAdmin(dto.getEmail(), dto.getPassword()));
        user.setUserTypes(userTypes);
        user.setStatus(false);
        userRepository.save(user);
        String link = address + authenticationFilter.generateToken(user.getUsername());
        String content = String.format("Please verify your data! To verify click to link: %s", link);

        mailSenderService.sendMail(content, user.getEmail());
    }

    @Override
    public void verification(String token) {
        User user = checkVerification(authenticationFilter.getUsername(token));
        user.setStatus(true);
        userRepository.save(user);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = getByUsername(username);
                String access_token = JWT.create ()
                        .withSubject (user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer (request.getRequestURL ().toString())
                        .withClaim("roles", user.getUserTypes().stream().map(UserType::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage ());
                response.setStatus (FORBIDDEN.value ());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage ());
                response.setContentType (APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                } catch (IOException e) {
                    throw new ApiRequestException("error" + e.getMessage());
                }
            }
        }else{
            throw new RuntimeException("Refresh token missing !");
        }
    }


    // |- SECONDARY FUNCTIONS -|

    public void checkEntity(String username){
        Optional<User> optionalUser = userRepository.findByUsernameAndDeletedAtIsNull(username);

        if (optionalUser.isPresent()){
            throw new ApiRequestException("User Already exist!");
        }
    }
    private UserType isAdmin(String email, String password) {
        String type;
        if (email.equals("a.k.18072003n.b@gmail.com") && password.equals("18072003")){
            type = "ROLE_ADMIN";
        } else {
            type = "ROLE_USER";
        }
        Optional<UserType> userType = userTypeRepository.findByNameAndDeletedAtIsNull(type);
        if (userType.isEmpty()){
            throw new ApiRequestException("User Type not found!");
        }
        return userType.get();
    }

    public User getByUsername(String username){
        return userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(()->new ApiRequestException("User not found!"));
    }

    private User checkVerification(String username) {
        return userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(()-> new ApiRequestException("Registration failed!"));
    }
}
