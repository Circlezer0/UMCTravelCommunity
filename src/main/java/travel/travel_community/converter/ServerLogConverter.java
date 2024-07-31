package travel.travel_community.converter;

import travel.travel_community.web.dto.serverLogDTO.ServerLogResponseDTO;

public class ServerLogConverter {
    public static ServerLogResponseDTO.ServerLogResultDTO toServerLogResultDTO(
            int allUsers, int recentSignupUsers, int allPosts, int runningDays) {
        return ServerLogResponseDTO.ServerLogResultDTO.builder()
                .allUsers(allUsers)
                .recentSignupUsers(recentSignupUsers)
                .allPosts(allPosts)
                .runningDays(runningDays)
                .build();
    }
}
