package com.app.controller;

import com.app.entity.User;
import com.app.exception.custom.TaskException;
import com.app.payload.response.UserResponseDto;
import com.app.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

     // Get user by ID (including avatar URL)
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User Not found");
        }
        User user = optionalUser.get();

        // Creating response DTO (to avoid exposing internal DB fields)
        UserResponseDto responseDto = mapToDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        List<User> users=userRepository.findAll();
        if(users ==null){
            throw new TaskException("No user found");
        }
        return ResponseEntity.ok(mapToUserResponse(users));
    }

    public UserResponseDto mapToDto(User user) {
        UserResponseDto responseDto = new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getDob(),
                user.getAvatar()
        );
        return responseDto;
    }
    public List<UserResponseDto> mapToUserResponse(List<User> users) {
        List<UserResponseDto> userDtoList = users.stream().map(user -> new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getGender(), // Assuming avatar is a URL, else convert byte[] to Base64
                user.getDob(),
                user.getAvatar()
        )).collect(Collectors.toList());
        return (userDtoList);
    }
}