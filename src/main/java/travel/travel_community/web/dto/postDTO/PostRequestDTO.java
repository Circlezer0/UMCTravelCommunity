package travel.travel_community.web.dto.postDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class PostRequestDTO {

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
    }
}
