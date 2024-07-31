package travel.travel_community.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import travel.travel_community.apiPayload.ApiResponse;
import travel.travel_community.apiPayload.code.ErrorReasonDTO;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;
import travel.travel_community.service.system.JwtService;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper; // JSON 변환을 위해 추가

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 헤더에서 Authroization 값을 찾음
        final String JWT_PREFIX = "Bearer ";
        final String JWT_HEADER_KEY = "Authorization";
        final String authHeader = request.getHeader(JWT_HEADER_KEY);
        final String jwt;
        final String userid;

        // jwt token 형식이 아니면 요청을 차단함
        if (authHeader == null || !authHeader.startsWith(JWT_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(JWT_PREFIX.length());
        try{
            userid = jwtService.extractUsername(jwt); // JWT 토큰으로 부터 유저 이메일 추출
            // jwt 토큰에 유저 이메일이 없고, 아직 인증되지 않은 유저라면
            if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // db에서 유저 정보를 가져옴
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userid);
                // token이 유효하다면
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // SecurityContext를 갱신한고 controller로 요청을 전달한다.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }catch (ExpiredJwtException e){
            sendErrorResponse(response, new UserHandler(ErrorStatus.USER_TOKEN_IS_EXPIRED));
            return;
        }catch (JwtException e){
            sendErrorResponse(response, new UserHandler(ErrorStatus.USER_TOKEN_IS_NOT_VALID));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, UserHandler exception) throws IOException {
        response.setContentType("application/json");
        ErrorReasonDTO errorReason = exception.getErrorReason();
        HttpStatus httpStatus = errorReason.getHttpStatus();

        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;  // 기본값 설정
        }
        response.setStatus(httpStatus.value());

        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                errorReason.getCode(),
                errorReason.getMessage(),
                null  // 추가 데이터가 필요한 경우 여기에 추가할 수 있습니다.
        );

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }
}
