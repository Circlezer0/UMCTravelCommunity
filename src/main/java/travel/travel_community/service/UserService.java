package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.entity.User;
import travel.travel_community.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isValidUser(String userid){
        Optional<User> userByUserid = userRepository.findUserByUserid(userid);
        if(userByUserid.isEmpty())return false;
        User user = userByUserid.get();
        // 이후에 비활성화된 유저나 탈퇴한 유저의 확인을 진행할 수 있음.
        return true;
    }
    public User findUserByUserId(String userid){
        Optional<User> userOpt = userRepository.findUserByUserid(userid);
        if (userOpt.isEmpty()) throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        return userOpt.get();
    }

    public User findUserId(String email) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if (userOpt.isEmpty()) throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        return userOpt.get();
    }


    public User resetPassword(String userId, String password) {
        Optional<User> userOpt = userRepository.findUserByUserid(userId);
        if (userOpt.isEmpty()) throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save((user));
    }

    public List<User> getRandomUsers() {
        return userRepository.findRandomUsers(PageRequest.of(0, 18));
    }

    public int allUserCount(){
        return userRepository.findAll().size();
    }

    public int recentSignupUserCount(){
        return userRepository.findRecentSignupUsers(LocalDateTime.now().minusDays(7)).size();
    }
}
