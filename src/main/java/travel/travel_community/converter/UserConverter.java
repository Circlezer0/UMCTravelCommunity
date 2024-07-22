package travel.travel_community.converter;

import travel.travel_community.entity.User;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

public class UserConverter {

    public static UserResponseDTO.SignupResultDTO toSignupResultDTO(String jwtToken){
        return UserResponseDTO.SignupResultDTO.builder()
                .token(jwtToken)
                .build();
    }

    public static UserResponseDTO.SignInResultDTO toSignInResultDTO(String jwtToken){
        return UserResponseDTO.SignInResultDTO.builder()
                .token(jwtToken)
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
}
