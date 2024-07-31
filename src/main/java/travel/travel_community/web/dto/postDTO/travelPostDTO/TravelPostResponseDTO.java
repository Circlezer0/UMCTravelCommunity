package travel.travel_community.web.dto.postDTO.travelPostDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;

import java.util.List;

public class TravelPostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewAllResultDTO {
        // 전체 게시글 조회
        private int page;
        private String orderBy;
        private int minPageIdx; // 현재 페이지 기준 선택 가능 페이지 중 최소 숫자
        private int maxPageIdx; // 현재 페이지 기준 선택 가능 페이지 중 최대 숫자
        private List<PostResponseDTO.TravelPostDTO> posts;
    }
}
