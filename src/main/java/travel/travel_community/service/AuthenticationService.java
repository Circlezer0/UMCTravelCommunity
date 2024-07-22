package travel.travel_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.entity.User;
import travel.travel_community.entity.enums.Role;
import travel.travel_community.repository.UserRepository;
import travel.travel_community.web.dto.userDTO.UserRequestDTO;


@Service
@RequiredArgsConstructor
public class AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public String signup(UserRequestDTO.SignupDTO request) {
        User prevUser = userRepository.findUserByUserid(request.getUserid()).orElseGet(() -> null);
        System.out.println("AuthenticationService.register");
        System.out.println("prevUser = " + prevUser);
        if (prevUser != null) {
            throw new UserHandler(ErrorStatus.USER_ALREADY_EXIST);
        }
        // 회원가입을 위해 유저를 db에 등록
        User user = User.builder()
                .userid(request.getUserid())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 인코딩
                .role(Role.USER)
                .build();

        user = userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String signIn(UserRequestDTO.SignInDTO request) {
//        System.out.println("AuthenticationService.authenticate");
//        System.out.println("request.getUserid() = " + request.getUserid());
//        System.out.println("request.getPassword() = " + request.getPassword());

        // 사용자가 DB에 존재하는지 확인
        User user = userRepository.findUserByUserid(request.getUserid())
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        // 인증 시도. 인증에 실패하면 AuthenticationError 반환됨
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserid(),
                            request.getPassword()
                    )
            );
        }catch (BadCredentialsException e) {
            throw new UserHandler(ErrorStatus.WRONG_LOGIN_INFO);
        } catch (LockedException e) {
            throw new UserHandler(ErrorStatus.USER_IS_LOCKED);
        } catch (DisabledException e) {
            throw new UserHandler(ErrorStatus.USER_IS_DISABLED);
        } catch (AccountExpiredException e) {
            throw new UserHandler(ErrorStatus.USER_IS_EXPIRED);
        } catch (CredentialsExpiredException e) {
            throw new UserHandler(ErrorStatus.CREDENTIAL_IS_EXPIRED);
        } catch (AuthenticationException e) {
            throw new UserHandler(ErrorStatus.AUTHENTICATION_FAILED);
        } catch (Exception e){
            throw new UserHandler(ErrorStatus._BAD_REQUEST);
        }

        // 인증 성공 시
        return jwtService.generateToken(user);
    }


    public User getLoggedInUser() {
        // 백엔드 서버에서 로그인 세션 관리를 하는 경우
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserHandler(ErrorStatus.USER_NOT_FOUND);
        }

        String userid = authentication.getName();
//        System.out.println("AuthenticationService.getLoggedInUser");
//        System.out.println("userid = " + userid);
        return userRepository.findUserByUserid(userid)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }

    public User getUserFromToken(String token) {
        // 프론트 서버에서 로그인 세션 관리를 하는 경우
        // 프론트로부터 jwt 토큰을 받아서 해당 토큰으로 로그인 유저 정보 가져옴
        String userid = jwtService.extractUsername(token);
//        System.out.println("AuthenticationService.getUserFromToken");
//        System.out.println("userid = " + userid);
        return userRepository.findUserByUserid(userid)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
    }
}