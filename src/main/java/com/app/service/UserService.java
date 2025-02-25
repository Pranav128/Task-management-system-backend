package com.app.service;

import com.app.entity.User;
import com.app.exception.custom.TaskException;
import com.app.payload.request.UpdateProfileRequest;
import com.app.payload.response.UserResponseDto;
import com.app.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject BCrypt Encoder

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> users=userRepository.findAll();
        if(users == null){
            throw new TaskException("No users found");
        }
        return ResponseEntity.ok(mapToUserResponse(users));
    }

    public UserResponseDto updateProfile(String username, UpdateProfileRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Validate current password using BCrypt
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new TaskException("⚠ Current password is incorrect");
        }

        // Update avatar if provided
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }

        // Update password if new password is provided
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            // ✅ Hash the new password before saving
            user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        }
        User user1 = userRepository.save(user);
        return mapToDto(user1);
    }

    public UserResponseDto getUserById( String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User Not found");
        }
        User user = optionalUser.get();

        // Creating response DTO (to avoid exposing internal DB fields)
        UserResponseDto responseDto = mapToDto(user);
        return responseDto;
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
