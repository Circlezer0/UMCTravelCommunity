package travel.travel_community.web.dto.postDTO.travelItemPostDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import travel.travel_community.validation.annotation.ExistContinent;
import travel.travel_community.validation.annotation.ExistCountry;
import travel.travel_community.validation.annotation.ExistUser;

import java.util.ArrayList;
import java.util.List;

public class TravelItemPostRequestDTO {

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
        private String categories = "전체";
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePostDTO {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @ExistUser
        private String userid;
        @NotEmpty(message = "카테고리 리스트는 비어있을 수 없습니다.")
        private List<@NotBlank(message = "각 카테고리는 비어있을 수 없습니다.") String> categories;
    }
}
