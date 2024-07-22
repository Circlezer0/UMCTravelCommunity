package travel.travel_community.web.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel_community.web.dto.userDTO.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelPostDTO {
        // 여행지 게시물 DTO
        Long id;
        String title;
        String content;
        int likeCount;
        LocalDateTime createDate;
        UserResponseDTO.UserDTO user;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelItemPostDTO {
        // 여행가방 게시물 DTO
        Long id;
        String title;
        String content;
        int likeCount;
        LocalDateTime createDate;
        UserResponseDTO.UserDTO user;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTopTravelPostsResultDTO {
        // 메인화면에서 7일간 여행가방 게시글 목록 DTO
        private List<TravelPostDTO> topTravelPosts;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTopTravelItemResultPosts {
        // 메인화면에서 7일간 여행가방 게시글 목록 DTO
        private List<TravelItemPostDTO> topTravelItemPosts;
    }
}
