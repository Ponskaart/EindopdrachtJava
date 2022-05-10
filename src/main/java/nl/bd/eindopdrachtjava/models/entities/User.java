package nl.bd.eindopdrachtjava.models.entities;

import lombok.*;
import nl.bd.eindopdrachtjava.models.enums.UserRole;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long userId;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private String username;

    @Column
    private String password;

    public User(String username, String password,  UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
