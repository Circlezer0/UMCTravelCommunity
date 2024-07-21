package travel.travel_community.web.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserRequestDTO {

    @Getter
    public static class SignInDTO {
        @NotBlank
        private String userid;
        @NotBlank
        private String password;
    }

    @Getter
    public static class SignupDTO {
        @NotBlank
        private String userid;
        @NotBlank
        private String nickname;
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @Getter
    public static class EmailAuthenticationDTO {
        @Email
        @NotBlank
        private String email;
    }

    @Getter
    public static class EmailValidationDTO {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String authNum;
    }

    @Getter
    public static class FindUserIdDTO {
        @Email
        @NotBlank
        private String email;
    }

    @Getter
    public static class ResetPasswordDTO {
        @NotBlank
        private String userid;
        @NotBlank
        private String password;
    }
}
