package travel.travel_community.web.dto.postDTO.travelPostDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
        private String continent;
        @ExistCountry
        private String country;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewAllDTO{ // 게시글, 여행가방 게시글 둘 다 요청 양식은 같기 때문에 공통으로 설정
        @Builder.Default
        private String orderBy = "latest";
        @Builder.Default
        private int page = 1;
        @Builder.Default
        private String continent = "전체";
        @Builder.Default
        private String country = "전체";
    }
}
