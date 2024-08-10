package travel.travel_community.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.GeneralException;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.converter.UserConverter;
import travel.travel_community.service.system.AuthenticationService;
import travel.travel_community.service.system.MailSendService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final MailSendService mailSendService;
    private final UserService userService;

    @GetMapping("/check/userid")
    public ApiResponse<UserResponseDTO.DuplicateCheckResultDTO> useridUniqueCheck(@RequestParam String userid){
        if(userid == null || userid.isEmpty())throw new GeneralException(ErrorStatus._NO_PARAMETER);
        return ApiResponse.onSuccess(UserResponseDTO.DuplicateCheckResultDTO.builder()
                        .isUnique(!userService.isExistByUserid(userid))
                .build());
    }
    @GetMapping("/check/email")
    public ApiResponse<UserResponseDTO.DuplicateCheckResultDTO> emailUniqueCheck(@RequestParam String email){
        if(email == null || email.isEmpty())throw new GeneralException(ErrorStatus._NO_PARAMETER);
        return ApiResponse.onSuccess(UserResponseDTO.DuplicateCheckResultDTO.builder()
                .isUnique(!userService.isExistByEmail(email))
                .build());
    }
    @GetMapping("/check/nickname")
    public ApiResponse<UserResponseDTO.DuplicateCheckResultDTO> nicknameUniqueCheck(@RequestParam String nickname){
        if(nickname == null || nickname.isEmpty())throw new GeneralException(ErrorStatus._NO_PARAMETER);
        return ApiResponse.onSuccess(UserResponseDTO.DuplicateCheckResultDTO.builder()
                .isUnique(!userService.isExistByNickname(nickname))
                .build());
    }

    /**
     * 회원가입
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public ApiResponse<UserResponseDTO.SignupResultDTO> register(@RequestBody @Valid UserRequestDTO.SignupDTO request) {
        return ApiResponse.onSuccess(UserConverter.toSignupResultDTO(authenticationService.signup(request)));
    }

    /**
     * 로그인
     * @param request
     * @return
     */
    @PostMapping("/signIn")
    public ApiResponse<UserResponseDTO.SignInResultDTO> authenticate(@RequestBody @Valid UserRequestDTO.SignInDTO request) {
        return ApiResponse.onSuccess(UserConverter.toSignInResultDTO(authenticationService.signIn(request), request.getUserid()));
    }

    /**
     * 인증 코드 메일 전송
     * @param request
     * @return
     */
    @PostMapping("/mailSend")
    public ApiResponse<UserResponseDTO.EmailAuthenticationResultDTO> emailAuthentication(@RequestBody @Valid UserRequestDTO.EmailAuthenticationDTO request) {
        String authNum = mailSendService.joinEmail(request.getEmail());
        return ApiResponse.onSuccess(UserConverter.toEmailAuthenticationResultDTO(authNum));
    }

    /**
     * 인증 코드 확인
     * @param request
     * @return
     */
    @PostMapping("/mailCheck")
    public ApiResponse<UserResponseDTO.EmailValidationResultDTO> emailValidation(@RequestBody @Valid UserRequestDTO.EmailValidationDTO request) {
        boolean checkResult = mailSendService.checkAuthNum(request.getEmail(), request.getAuthNum());
        if (!checkResult) {
            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_ERROR);
        }
        return ApiResponse.onSuccess(UserConverter.toEmailValidationResultDTO(request));
    }
}
