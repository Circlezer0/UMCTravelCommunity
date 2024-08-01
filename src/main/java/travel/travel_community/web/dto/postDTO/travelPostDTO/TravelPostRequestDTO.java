package travel.travel_community.web.dto.postDTO.travelPostDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import travel.travel_community.validation.annotation.ExistContinent;
import travel.travel_community.validation.annotation.ExistCountry;
import travel.travel_community.validation.annotation.ExistUser;

import java.util.List;

public class TravelPostRequestDTO {

    @Getter
    @Setter
    public static class CreatePostDTO {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @ExistUser
        private String userid;
        @ExistContinent
        private Long continent;
        @ExistCountry
        private Long country;
    }
}
