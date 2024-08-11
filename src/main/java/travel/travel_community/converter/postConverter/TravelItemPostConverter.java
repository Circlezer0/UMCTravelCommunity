package travel.travel_community.converter.postConverter;

import org.springframework.data.domain.Page;
import travel.travel_community.entity.posts.TravelItemPost;
import travel.travel_community.web.dto.postDTO.PostResponseDTO;
import travel.travel_community.web.dto.postDTO.travelItemPostDTO.TravelItemPostResponseDTO;
import travel.travel_community.web.dto.postDTO.travelPostDTO.TravelPostResponseDTO;

import java.util.List;

public class TravelItemPostConverter {
    public static TravelItemPostResponseDTO.ViewAllResultDTO toViewAllResultDTO(
            Page<TravelItemPost> posts, int page, String orderBy, int minPageIdx, int maxPageIdx) {

        List<PostResponseDTO.TravelItemPostDTO> postDTOList = posts.getContent().stream().map(
                        PostConverter::toTravelItemPostResultDTO)
                .toList();

        return TravelItemPostResponseDTO.ViewAllResultDTO.builder()
                .posts(postDTOList)
                .orderBy(orderBy)
                .page(page)
                .minPageIdx(minPageIdx)
                .maxPageIdx(maxPageIdx)
                .build();
    }
}
