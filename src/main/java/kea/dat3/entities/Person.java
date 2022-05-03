package kea.dat3.entities;

import kea.dat3.security.UserWithPassword;
import kea.dat3.dto.PersonRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person implements UserWithPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    private char active;

    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime create_date;

    @UpdateTimestamp
    private LocalDateTime last_updated;

    @Column(nullable = false, length = 50)
    private String username;

    // 72 == Max length of a bcrypt encoded password
    // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
    @Column(nullable = false, length = 72)
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CUSTUMER','STAFF','ADMIN')")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="security_role")
    List<Role> roles = new ArrayList<>();

    @Override
    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public void setPassword(String password) {
        this.password = pwEncoder.encode(password);
    }


    public Person(String email,String first_name, String last_name, String phoneNumber, String username, String password) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public Person(PersonRequest body) {
        this.email = body.getEmail();
        this.username = body.getUsername();
        this.password = pwEncoder.encode(body.getPassword());
        this.enabled = true;
    }

    public boolean isEnabled() { return this.enabled; }
}
