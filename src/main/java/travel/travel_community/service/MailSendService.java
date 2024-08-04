package travel.travel_community.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.apiPayload.exception.handler.UserHandler;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    //private final RedisUtil redisUtil;
    private final HttpSession httpSession;
    private final static Random random = new Random();

    @Value("${spring.email.authentication.email}")
    private String emailAddress;
    @Value("${spring.email.authentication.password}")
    private String password;

    //임의의 6자리 양수를 반환합니다.
    public int makeRandomNumber() {
        int randomNumber = 0;
        do {
            randomNumber = 0;
            for (int i = 0; i < 6; i++) {
                randomNumber = randomNumber * 10 + random.nextInt(10);
            }
        } while (httpSession.getAttribute(Integer.toString(randomNumber)) != null);
        // 세션 방식을 나중에 Redis 방식으로 변경
        // while (redisUtil.getData(Integer.toString(randomNumber)) != null);

        return randomNumber;
    }

    public boolean checkAuthNum(String email, String authNum) {
        if(httpSession.getAttribute(authNum) == null){
            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_TOKEN_NOT_FOUND);
        }
        String cmpEmail = (String) httpSession.getAttribute(authNum);
        LocalDateTime startTime = (LocalDateTime) httpSession.getAttribute(cmpEmail);
        if(startTime.isBefore(LocalDateTime.now())){
            httpSession.removeAttribute(authNum);
            httpSession.removeAttribute(cmpEmail);
            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_TOKEN_EXPIRED);
        }
        if(!httpSession.getAttribute(authNum).equals(email)){
            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_TOKEN_ERROR);
        }
        httpSession.removeAttribute(authNum);
        httpSession.removeAttribute(cmpEmail);
        return true;
//        세션 방식을 나중에 Redis 방식으로 변경
//        if (redisUtil.getData(authNum) == null) {
//            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_TOKEN_NOT_FOUND);
//        }
//        if(!redisUtil.getData(authNum).equals(email)){
//            throw new UserHandler(ErrorStatus.MAIL_AUTHENTICATION_TOKEN_ERROR);
//        }
//        return redisUtil.getData(authNum).equals(email);
    }

    //mail을 어디서 보내는지, 어디로 보내는지 , 인증 번호를 html 형식으로 어떻게 보내는지 작성합니다.
    public String joinEmail(String targetEmail) {
        int authNumber = makeRandomNumber();
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        //이메일 내용, html 형식으로 작성 !
        String content =
                "Memoir 이메일 인증입니다." +
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다.";

        mailSend(emailAddress, targetEmail, title, content);
        // 5분동안 인증 토큰 유지
        httpSession.setAttribute(Integer.toString(authNumber), targetEmail);
        httpSession.setAttribute(targetEmail, LocalDateTime.now().plusMinutes(5));
//        세션 방식을 나중에 Redis로 변경
//        redisUtil.setDataExpire(Integer.toString(authNumber), targetEmail, 60*1L);
        return Integer.toString(authNumber);
    }

    //이메일을 전송합니다.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            throw new UserHandler(ErrorStatus.MAIL_SEND_ERROR);
            //e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
    }

}