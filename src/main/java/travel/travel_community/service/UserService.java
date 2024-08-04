package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.entity.User;
import travel.travel_community.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserId(String email){
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if(userOpt.isEmpty()) throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        return userOpt.get();
    }

    public User resetPassword(String userId, String password) {
        Optional<User> userOpt = userRepository.findUserByUserid(userId);
        if (userOpt.isEmpty()) throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save((user));
    }
}
