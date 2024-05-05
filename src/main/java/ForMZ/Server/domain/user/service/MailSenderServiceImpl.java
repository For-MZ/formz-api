package ForMZ.Server.domain.user.service;

import ForMZ.Server.domain.user.dto.MailRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailSenderServiceImpl implements MailSenderService{
    @Autowired
    private JavaMailSender mailSender;
    private int authNumber;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * 임의의 6자리 양수 생성
     */
    public void makeRandomNumber(){
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++){
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }

    /**
     * 인증을 위해 보내는 Mail 형식, 정보
     */
    public MailRes joinEmail(String email){
        makeRandomNumber();
        String setFrom = username;
        String toMail = email;
        String title = "회원가입 인증 이메일입니다.";
        String content =
                "안녕하세요. ForMZ 입니다." +
                        "<br><br>" +
                        "인증 번호는 " + authNumber + " 입니다." +
                        "<br>"+
                        "인증 번호를 입력해주세요.";
        mailSend(setFrom, toMail, title, content);
        return new MailRes(Integer.toString(authNumber));
    }

    /**
     * 이메일 전송
     */
    public void mailSend(String setFrom, String toMail, String title, String content){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
