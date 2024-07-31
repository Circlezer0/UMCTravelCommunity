package travel.travel_community.service.system;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travel_community.entity.ServerLog;
import travel.travel_community.repository.ServerLogRepository;
import travel.travel_community.service.TravelItemPostService;
import travel.travel_community.service.TravelPostService;
import travel.travel_community.service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServerLogService {

    private final ServerLogRepository serverLogRepository;
    private final UserService userService;
    private final TravelPostService travelPostService;
    private final TravelItemPostService travelItemPostService;

    /**
     * 메인페이지에서 가입한 유저의 수를 확인하는 메소드
     * 매번 새로 계산하면 서버 부하가 매우 커지기 때문에
     * db에 미리 값을 저장해두고 일정 기간마다 업데이트 하는 방식으로 처리
     * @return 전체 유저의 수
     */
    public int getAllUsers(){
        updateAllUsers(); // 실사용 시에는 GitAction 등으로 특정 시간마다 실행하도록 변경해야 함.

        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("allUsers");
        if(serverLogOpt.isEmpty()){
            //throw new ~~ 예외처리
            return 0;
        }
        ServerLog serverLog = serverLogOpt.get();
        String value = serverLog.getValue();
        return Integer.parseInt(value);
    }

    /**
     *  DB에 저장된 유저의 수를 업데이트 하는 메소드
     * @return 전체 유저의 수
     */
    public int updateAllUsers(){
        int userCount = userService.allUserCount();

        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("allUsers");
        if(serverLogOpt.isEmpty()){
            // allUsers 키워드가 없는 경우 새로 생성해서 저장
            ServerLog serverLog = ServerLog.builder()
                    .keyValue("allUsers")
                    .value(Integer.toString(userCount))
                    .build();
            serverLogRepository.save(serverLog);
        }
        else {
            // allUsers 키워드가 있는 경우 업데이트 해서 저장
            ServerLog serverLog = serverLogOpt.get();
            serverLog.setValue(Integer.toString(userCount));
            serverLogRepository.save(serverLog);
        }
        return userCount;
    }


    /**
     * 메인페이지에서 최근 가입한 유저의 수를 확인하는 메소드
     * 매번 새로 계산하면 서버 부하가 매우 커지기 때문에
     * db에 미리 값을 저장해두고 일정 기간마다 업데이트 하는 방식으로 처리
     * @return 최근 가입한 유저의 수
     */
    public int getRecentSignupUsers(){
        updateRecentSignupUsers(); // 실사용 시에는 GitAction 등으로 특정 시간마다 실행하도록 변경해야 함.

        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("recentSignupUsers");
        if(serverLogOpt.isEmpty()){
            //throw new ~~ 예외처리
            return 0;
        }
        ServerLog serverLog = serverLogOpt.get();
        String value = serverLog.getValue();
        return Integer.parseInt(value);
    }

    /**
     *  DB에 저장된 최근 가입한 유저의 수를 업데이트 하는 메소드
     * @return 최근 가입한 유저의 수
     */
    public int updateRecentSignupUsers(){
        int userCount = userService.recentSignupUserCount();

        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("recentSignupUsers");
        if(serverLogOpt.isEmpty()){
            // allUsers 키워드가 없는 경우 새로 생성해서 저장
            ServerLog serverLog = ServerLog.builder()
                    .keyValue("recentSignupUsers")
                    .value(Integer.toString(userCount))
                    .build();
            serverLogRepository.save(serverLog);
        }
        else {
            // allUsers 키워드가 있는 경우 업데이트 해서 저장
            ServerLog serverLog = serverLogOpt.get();
            serverLog.setValue(Integer.toString(userCount));
            serverLogRepository.save(serverLog);
        }
        return userCount;
    }

    /**
     * 메인페이지에서 게시글의 수를 확인하는 메소드
     * 매번 새로 계산하면 서버 부하가 매우 커지기 때문에
     * db에 미리 값을 저장해두고 일정 기간마다 업데이트 하는 방식으로 처리
     * @return 전체 게시글의 수
     */
    public int getAllPosts(){
        updateAllPosts(); // 실사용 시에는 GitAction 등으로 특정 시간마다 실행하도록 변경해야 함.

        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("allPosts");
        if(serverLogOpt.isEmpty()){
            //throw new ~~ 예외처리
            return 0;
        }
        ServerLog serverLog = serverLogOpt.get();
        String value = serverLog.getValue();
        return Integer.parseInt(value);
    }

    /**
     *  DB에 저장된 게시글의 수를 업데이트 하는 메소드
     * @return 전체 게시글의 수
     */
    public int updateAllPosts(){
        int postCount = 0;
        postCount += travelPostService.getAllPostCount();
        postCount += travelItemPostService.getAllPostCount();

        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("allPosts");
        if(serverLogOpt.isEmpty()){
            // allUsers 키워드가 없는 경우 새로 생성해서 저장
            ServerLog serverLog = ServerLog.builder()
                    .keyValue("allPosts")
                    .value(Integer.toString(postCount))
                    .build();
            serverLogRepository.save(serverLog);
        }
        else {
            // allUsers 키워드가 있는 경우 업데이트 해서 저장
            ServerLog serverLog = serverLogOpt.get();
            serverLog.setValue(Integer.toString(postCount));
            serverLogRepository.save(serverLog);
        }
        return postCount;
    }


    /**
     * 서버 시작 날짜에서 현재 날짜 차이를 통해 서버를 시작한 날의 수를 계산
     * @return 서버 실행 기간
     */
    public int getRunningDays() {
        Optional<ServerLog> serverLogOpt = serverLogRepository.findByKeyValue("startDate");
        if(serverLogOpt.isEmpty()){
            String dateNow = LocalDate.now().toString();
            ServerLog serverLog = ServerLog.builder()
                    .keyValue("startDate")
                    .value(dateNow)
                    .build();
            serverLogOpt = Optional.of(serverLogRepository.save(serverLog));
        }

        ServerLog serverLog = serverLogOpt.get();
        String str = serverLog.getValue();
        LocalDate startDate = LocalDate.parse(str);
        return (int) ChronoUnit.DAYS.between(startDate, LocalDate.now());
    }
}
