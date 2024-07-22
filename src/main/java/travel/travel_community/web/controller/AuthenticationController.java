package travel.travel_community.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.converter.UserConverter;
import travel.travel_community.entity.User;
import travel.travel_community.service.AuthenticationService;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    // 회원 가입
    @PostMapping("/signup")
    public ApiResponse<UserResponseDTO.SignupResultDTO> register(@RequestBody @Valid UserRequestDTO.SignupDTO request) {
        System.out.println("AuthenticationController.register");
        return ApiResponse.onSuccess(UserConverter.toSignupResultDTO(authenticationService.signup(request)));
    }

    // 로그인
    @PostMapping("/signIn")
    public ApiResponse<UserResponseDTO.SignInResultDTO> authenticate(@RequestBody @Valid UserRequestDTO.SignInDTO request) {
        return ApiResponse.onSuccess(UserConverter.toSignInResultDTO(
                authenticationService.signIn(request)
        ));
    }

    @GetMapping("/userInfo")
    public ApiResponse<UserResponseDTO.UserInfoResultDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        // "Bearer " 접두사 제거
        //System.out.println("authHeader = " + authHeader);
        if(authHeader == null || authHeader.length() <= 7){
            throw new UserHandler(ErrorStatus.USER_TOKEN_IS_NOT_VALID);
        }

        String token = authHeader.substring(7);
        //System.out.println("token = " + token);
        User user = authenticationService.getUserFromToken(token);
        return ApiResponse.onSuccess(UserConverter.toUserInfoResultDTO(user));
    }
}
