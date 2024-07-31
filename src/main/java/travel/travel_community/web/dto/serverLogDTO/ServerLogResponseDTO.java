package travel.travel_community.web.dto.serverLogDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ServerLogResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServerLogResultDTO {
        int allUsers;
        int recentSignupUsers;
        int allPosts;
        int runningDays;
    }
}
