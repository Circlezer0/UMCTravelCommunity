package travel.travel_community.converter.postConverter;

import travel.travel_community.converter.UserConverter;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponseDTO.TravelPostDTO toTravelPostResultDTO(TravelPost post) {
        return PostResponseDTO.TravelPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .scrapCount(post.getScrapCount())
                .viewCount(post.getViewCount())
                .createDate(post.getCreatedDate())
                .user(UserConverter.toUserDTO(post.getAuthor()))
                .build();
    }

    public static PostResponseDTO.TravelItemPostDTO toTravelItemPostResultDTO(TravelItemPost post) {
        return PostResponseDTO.TravelItemPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .scrapCount(post.getScrapCount())
                .viewCount(post.getViewCount())
                .createDate(post.getCreatedDate())
                .user(UserConverter.toUserDTO(post.getAuthor()))
                .build();
    }

    public static PostResponseDTO.TravelPostsResultDTO toTravelPostsResultDTO(List<TravelPost> posts){
        // 무한재귀 방지를 위한 조치
        List<PostResponseDTO.TravelPostDTO> collect = posts.stream()
                .map(PostConverter::toTravelPostResultDTO)
                .collect(Collectors.toList());

        return PostResponseDTO.TravelPostsResultDTO.builder()
                .topTravelPosts(collect)
                .build();
    }

    public static PostResponseDTO.TravelItemPostsResultDTO toTravelItemPostsResultDTO(List<TravelItemPost> posts){
        List<PostResponseDTO.TravelItemPostDTO> collect = posts.stream()
                .map(PostConverter::toTravelItemPostResultDTO)
                .collect(Collectors.toList());
        return PostResponseDTO.TravelItemPostsResultDTO.builder()
                .topTravelItemPosts(collect)
                .build();
    }

}
