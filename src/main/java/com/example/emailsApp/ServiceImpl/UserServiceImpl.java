package com.example.emailsApp.ServiceImpl;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.emailsApp.dto.UserDto;
import com.example.emailsApp.entity.Roles;
import com.example.emailsApp.entity.User;
import com.example.emailsApp.repository.RoleRepository;
import com.example.emailsApp.repository.UserRepository;
import com.example.emailsApp.services.UserService;




@Service
public class UserServiceImpl  implements UserService{
    

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    @Override
    public void saveUser(UserDto userDto) {
        

        if (userDto.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

       Roles role = roleRepository.findByName("ROLE_USER");
       
       if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
       
        userRepository.save(user);
       
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        List<String> roles = user.getRoles().stream()
                                .map(role -> role.getName())
                                .collect(Collectors.toList());
                                userDto.setRoles(roles);
        return userDto;
    }

    private Roles checkRoleExist(){
        Roles role = new Roles();
        role.setName("ROLE_USER");
        return roleRepository.save(role);

    }



    
}

