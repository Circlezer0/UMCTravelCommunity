package travel.travel_community.converter;

import travel.travel_community.entity.User;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

import java.util.List;

public class UserConverter {

    public static UserResponseDTO.UserDTO toUserDTO(User user) {
        return UserResponseDTO.UserDTO.builder()
                .id(user.getId())
                .userid(user.getUserid())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public static UserResponseDTO.SignupResultDTO toSignupResultDTO(String jwtToken){
        return UserResponseDTO.SignupResultDTO.builder()
                .token(jwtToken)
                .build();
    }

    public static UserResponseDTO.SignInResultDTO toSignInResultDTO(String jwtToken, String userid){
        return UserResponseDTO.SignInResultDTO.builder()
                .token(jwtToken)
                .userid(userid)
                .build();
    }

    public static UserResponseDTO.UserInfoResultDTO toUserInfoResultDTO(User user){
        return UserResponseDTO.UserInfoResultDTO.builder()
                .userid(user.getUserid())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public static UserResponseDTO.EmailAuthenticationResultDTO toEmailAuthenticationResultDTO(String authNum){
        return UserResponseDTO.EmailAuthenticationResultDTO.builder()
                .authNum(authNum)
                .build();
    }

    public static UserResponseDTO.EmailValidationResultDTO toEmailValidationResultDTO(UserRequestDTO.EmailValidationDTO request){
        return UserResponseDTO.EmailValidationResultDTO.builder()
                .email(request.getEmail())
                .build();
    }

    public static UserResponseDTO.FindUserIdResultDTO toFindUserIdResultDTO(User user) {
        return UserResponseDTO.FindUserIdResultDTO.builder()
                .userid(user.getUserid())
                .email(user.getEmail())
                .build();
    }

    public static UserResponseDTO.ResetPasswordResultDTO toResetPasswordResultDTO(User user){
        return UserResponseDTO.ResetPasswordResultDTO.builder()
                .userid(user.getUserid())
                .build();
    }

    public static UserResponseDTO.TopUsersResultDTO toTopUserResultDTO(List<User> users) {
        return UserResponseDTO.TopUsersResultDTO.builder()
                .topUsers(users)
                .build();
    }
}
