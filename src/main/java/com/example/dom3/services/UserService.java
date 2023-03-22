package com.example.dom3.services;

import com.example.dom3.models.Permission;
import com.example.dom3.models.User;
import com.example.dom3.models.UserUpdateReq;
import com.example.dom3.repository.PermissionRepository;
import com.example.dom3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PermissionRepository permissionRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User myUser = this.userRepository.findByUsername(username);
//        if(myUser == null) {
//            throw new UsernameNotFoundException("User name "+username+" not found");
//        }
//
//        return new org.springframework.security.core.userdetails.User(myUser.getName(), myUser.getPassword(), new ArrayList<>());
        User myUser = this.userRepository.findByEmail(username);

        if(myUser == null) throw new UsernameNotFoundException("User not found in the database");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        myUser.getPermissions().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName()) ));
        return  new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), authorities);
    }

    public User newUser(User user) {

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Collection<Permission> permissions = new ArrayList<>();
        for (Permission permission: user.getPermissions()) {
            permissions.add(permission);
        }
        user.setPermissions(permissions);
        return userRepository.save(user);

    }

    public void savePermission(Permission permission) {
        permissionRepository.save(permission);
    }

    public void addPermissionToUser(String mail, String roleName) {
        Permission permission = permissionRepository.findByName(roleName);
        User user = userRepository.findByEmail(mail);
        user.getPermissions().add(permission);
        userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
            userRepository.deleteById(id.longValue());

    }

    public User updateUser(UserUpdateReq user){
        User newUser = userRepository.getById(user.getId().longValue());
        newUser.setName(user.getName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        Collection<Permission> permissions = new ArrayList<>();
        for (Permission permission: user.getPermissions()) {
            permissions.add(permission);
        }
        newUser.setPermissions(permissions);
        return userRepository.save(newUser);

    }

    public List<User> getAllUsers(){
//        ArrayList<User> users = new ArrayList<>();
//        userRepository.findAll().forEach(user -> users.add(user));
        return userRepository.findAll();

    }

    public List<Permission> getPermissionsForUser(String mail) {
        System.out.println(mail);
        return new ArrayList<>(userRepository.findByEmail(mail).getPermissions());
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Permission> getAllPermissions(){
        return permissionRepository.findAll();
    }


}
