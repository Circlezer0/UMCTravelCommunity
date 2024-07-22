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
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/userInfo")
    public ApiResponse<UserResponseDTO.UserInfoResultDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        // "Bearer " 접두사 제거
        //System.out.println("authHeader = " + authHeader);
        if (authHeader == null || authHeader.length() <= 7) {
            throw new UserHandler(ErrorStatus.USER_TOKEN_IS_NOT_VALID);
        }

        String token = authHeader.substring(7);
        //System.out.println("token = " + token);
        User user = authenticationService.getUserFromToken(token);
        return ApiResponse.onSuccess(UserConverter.toUserInfoResultDTO(user));
    }

    @PostMapping("/findUserId")
    public ApiResponse<UserResponseDTO.FindUserIdResultDTO> findUserId(@RequestBody @Valid UserRequestDTO.FindUserIdDTO request) {
        String email = request.getEmail();
        System.out.println("email = " + email);
        return ApiResponse.onSuccess(UserConverter.toFindUserIdResultDTO(userService.findUserId(email)));
    }

    @PostMapping("/resetPassword")
    public ApiResponse<UserResponseDTO.ResetPasswordResultDTO> resetPassword(@RequestBody @Valid UserRequestDTO.ResetPasswordDTO request) {
        String userid = request.getUserid();
        String password = request.getPassword();
        return ApiResponse.onSuccess(UserConverter.toResetPasswordResultDTO(userService.resetPassword(userid, password)));
    }
}
