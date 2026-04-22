package nicolagraziani.U5_W3_D1_Spring_security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import nicolagraziani.U5_W3_D1_Spring_security.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "emplyees")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired", "enabled"})
@ToString
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID employeeId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(name = "avatar_img_url")
    private String avatarImg;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatarImg = "https://ui-avatars.com/api/?" + name + "=" + surname;
        this.password = password;
        this.role = Role.USER;
    }

    //    MANAGE AUTHORIZATION
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
