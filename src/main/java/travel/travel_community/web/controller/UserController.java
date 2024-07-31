package travel.travel_community.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.converter.UserConverter;
import travel.travel_community.entity.User;
import travel.travel_community.service.system.AuthenticationService;
import travel.travel_community.service.UserService;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    /**
     * 유저 정보 조회
     * 발생하는 예외 종류
     * - ErrorStatus.USER_TOKEN_IS_NOT_VALID : 유효하지 않은 JWT 토큰
     * - ErrorStatus.USER_NOT_FOUND : 유저가 DB에 없음
     *
     * @param authHeader JWT 토큰 (프론트 세션에서 관리)
     * @return 유저 정보
     */
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

    /**
     * 유저 아이디 조회
     * 발생하는 예외 종류
     * - ErrorStatus.USER_NOT_FOUND : 유저가 DB에 없음
     *
     * @param request 이메일 정보
     * @return 유저 아이디
     */
    @PostMapping("/findUserId")
    public ApiResponse<UserResponseDTO.FindUserIdResultDTO> findUserId(@RequestBody @Valid UserRequestDTO.FindUserIdDTO request) {
        String email = request.getEmail();
        System.out.println("email = " + email);
        return ApiResponse.onSuccess(UserConverter.toFindUserIdResultDTO(userService.findUserId(email)));
    }

    /**
     * 비밀번호 변경
     * 발생하는 예외 종류
     * - ErrorStatus.USER_NOT_FOUND : 유저가 DB에 없음
     *
     * @param request 유저 아이디, 바꿀 비밀번호
     * @return 바뀐 결과
     */
    @PostMapping("/resetPassword")
    public ApiResponse<UserResponseDTO.ResetPasswordResultDTO> resetPassword(@RequestBody @Valid UserRequestDTO.ResetPasswordDTO request) {
        String userid = request.getUserid();
        String password = request.getPassword();
        return ApiResponse.onSuccess(UserConverter.toResetPasswordResultDTO(userService.resetPassword(userid, password)));
    }


    //------------------------------------ 메인페이지 기능 --------------------------------------------
    /**
     * 메인페이지에서 보여줄 유저 30명 랜덤으로 가져옴
     * @return 유저 리스트
     */
    @GetMapping("/topUsers")
    public ApiResponse<UserResponseDTO.TopUsersResultDTO> getTopUsers() {
        // 랜덤으로 유저 30명 가져오는걸 나중에 좋아요를 받은 순서대로 정렬해서 유저를 가져와야 함
        List<User> users = userService.getRandomUsers();
        return ApiResponse.onSuccess(UserConverter.toTopUserResultDTO(users));
    }
    //----------------------------------------------------------------------------------------------
}
