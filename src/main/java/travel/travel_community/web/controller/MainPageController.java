package travel.travel_community.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.converter.ServerLogConverter;
import travel.travel_community.service.*;
import travel.travel_community.service.system.ServerLogService;
import travel.travel_community.web.dto.serverLogDTO.ServerLogResponseDTO;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainPageController {
    private final UserService userService;
    private final TravelPostService travelPostService;
    private final TravelPostCategoryService travelPostCategoryService;
    private final TravelItemPostService travelItemPostService;
    private final TravelItemPostCategoryService travelItemPostCategoryService;

    private final ServerLogService serverLogService;


    @GetMapping("/serverLogs")
    public ApiResponse<ServerLogResponseDTO.ServerLogResultDTO> getServerData() {
        int allUsers = serverLogService.getAllUsers();
        int recentSignupUsers = serverLogService.getRecentSignupUsers();
        int allPosts = serverLogService.getAllPosts();
        int runningDays = serverLogService.getRunningDays();

        return ApiResponse.onSuccess(ServerLogConverter.toServerLogResultDTO(
                allUsers, recentSignupUsers, allPosts, runningDays
        ));
    }
}