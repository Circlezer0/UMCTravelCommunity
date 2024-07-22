package travel.travel_community.web.dto.postDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class PostRequestDTO {

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
    public static class TravelPostDto {

    }
}
