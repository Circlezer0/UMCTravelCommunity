package travel.travel_community.web.dto.userDTO;

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
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }
}
