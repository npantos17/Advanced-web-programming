package com.example.dom3.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "users_permissions",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
//    )
    private Collection<Permission> permissions = new ArrayList<>();

    public User(Long id, String name, String lastName, String email, String password){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
