package com.example.dom3.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    public Permission(Integer id, String name){
        this.id = id;
        this.name = name;
    }

//    @ManyToMany
//    @JoinTable(
//            name = "users_permissions",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
//    )
//    private List<User> users;


}
