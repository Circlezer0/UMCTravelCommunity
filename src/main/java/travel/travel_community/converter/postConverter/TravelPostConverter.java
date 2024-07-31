package travel.travel_community.converter.postConverter;

import org.springframework.data.domain.Page;
import travel.travel_community.entity.posts.TravelPost;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostResponseDTO;

import java.util.List;

public class TravelPostConverter {

    public static TravelPostResponseDTO.ViewAllResultDTO toViewAllResultDTO(
            Page<TravelPost> posts, int page, String orderBy, int minPageIdx, int maxPageIdx) {

        List<PostResponseDTO.TravelPostDTO> postDTOList = posts.getContent().stream().map(
                PostConverter::toTravelPostResultDTO)
                .toList();

        return TravelPostResponseDTO.ViewAllResultDTO.builder()
                .posts(postDTOList)
                .orderBy(orderBy)
                .page(page)
                .minPageIdx(minPageIdx)
                .maxPageIdx(maxPageIdx)
                .build();
    }
}
