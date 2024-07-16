package travel.travel_community.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import travel.travel_community.apiPayload.code.BaseErrorCode;
import travel.travel_community.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관려 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "User not found."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4001", "User already exist."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    WRONG_LOGIN_INFO(HttpStatus.UNAUTHORIZED, "MEMBER4003", "UserId or Password is not correct."),
    USER_IS_LOCKED(HttpStatus.UNAUTHORIZED, "MEMBER4004", "User account is locked."),
    USER_IS_DISABLED(HttpStatus.UNAUTHORIZED, "MEMBER4005", "User account is disabled."),
    USER_IS_EXPIRED(HttpStatus.UNAUTHORIZED, "MEMBER4006", "User account is expired."),
    CREDENTIAL_IS_EXPIRED(HttpStatus.UNAUTHORIZED, "MEMBER4007", "Credential is expired."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "MEMBER4008", "Authentication failed."),
    USER_TOKEN_IS_EXPIRED(HttpStatus.UNAUTHORIZED, "MEMBER4009", "JWT Token is expired."),
    USER_TOKEN_IS_NOT_VALID(HttpStatus.UNAUTHORIZED, "MEMBER4010", "JWT Token is not valid."),


    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    // Ror test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

    // FoodCategory Error
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FOOD_CATEGORY4001", "음식 카테고리가 없습니다."),

    // Store Error

    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE_4001","가게가 없습니다."),

    // Region Error
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION_4001", "지역이 없습니다"),

    // Review Error
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_4001", "리뷰가 없습니다"),
    REVIEW_SCORE_VALUE_ERROR(HttpStatus.BAD_REQUEST, "REVIEW_4002", "리뷰 점수가 잘못되었습니다"),

    // Mission Error
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION_4001", "미션이 없습니다"),
    MISSION_EXPIRED(HttpStatus.FORBIDDEN, "MISSION_4002", "미션이 만료되었습니다"),
    MISSION_ALREADY_STARTED(HttpStatus.BAD_REQUEST, "MISSION_4003", "이미 시작된 미션입니다");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}