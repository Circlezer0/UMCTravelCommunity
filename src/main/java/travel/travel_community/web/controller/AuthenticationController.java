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
import travel.travel_community.service.MailSendService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final MailSendService mailSendService;
    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ApiResponse<UserResponseDTO.SignupResultDTO> register(@RequestBody @Valid UserRequestDTO.SignupDTO request) {
        System.out.println("AuthenticationController.register");
        return ApiResponse.onSuccess(UserConverter.toSignupResultDTO(authenticationService.signup(request)));
    }

    // 로그인
    @PostMapping("/signIn")
    public ApiResponse<UserResponseDTO.SignInResultDTO> authenticate(@RequestBody @Valid UserRequestDTO.SignInDTO request) {
        return ApiResponse.onSuccess(UserConverter.toSignInResultDTO(authenticationService.signIn(request)));
    }


    @PostMapping("/mailSend")
    public ApiResponse<UserResponseDTO.EmailAuthenticationResultDTO> emailAuthentication(@RequestBody @Valid UserRequestDTO.EmailAuthenticationDTO request) {
        String authNum = mailSendService.joinEmail(request.getEmail());
        return ApiResponse.onSuccess(UserConverter.toEmailAuthenticationResultDTO(authNum));
    }

    @PostMapping("/mailCheck")
    public ApiResponse<UserResponseDTO.EmailValidationResultDTO> emailValidation(@RequestBody @Valid UserRequestDTO.EmailValidationDTO request) {
        boolean checkResult = mailSendService.checkAuthNum(request.getEmail(), request.getAuthNum());
        if (!checkResult) {
            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_ERROR);
        }
        return ApiResponse.onSuccess(UserConverter.toEmailValidationResultDTO(request));
    }
}
