package kea.dat3.entities;

import kea.dat3.security.UserWithPassword;
import kea.dat3.dto.PersonRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    @Column(nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private char active;

    @Column(unique = true)
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    // 72 == Max length of a bcrypt encoded password
    // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
    @Column(nullable = false, length = 72)
    private String password;

    /*@OneToMany
    private reservations;*/

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

    public Person(String email, String firstName, String lastName, String phoneNumber, String username, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public Person(PersonRequest body) {
        this.email = body.getEmail();
        this.username = body.getUsername();
        this.firstName = body.getFirstName();
        this.lastName = body.getLastName();
        this.phoneNumber = body.getPhoneNumber();
        this.password = pwEncoder.encode(body.getPassword());
        this.enabled = true;
    }

    public boolean isEnabled() { return this.enabled; }
}
