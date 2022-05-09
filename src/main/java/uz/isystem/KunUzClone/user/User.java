package uz.isystem.KunUzClone.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.isystem.KunUzClone.userType.UserType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ("users"))
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<UserType> userTypes = new ArrayList<>();

    @Column(name = ("firstname"))
    private String firstname;

    @Column(name = ("lastname"))
    private String lastname;

    @Column(name = ("username"))
    private String username;

    @Column(name = ("password"))
    private String password;

    @Column(name = ("contact"))
    private String contact;

    @Column(name = ("email"))
    private String email;

    @Column(name = ("status"))
    private Boolean status;

    @Column(name = ("created_at"))
    private LocalDateTime createdAt;

    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
}
