package ForMZ.Server.domain.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;


    @Value("${spring.mail.port}")
    private int port;


    @Value("${spring.mail.username}")
    private String username;


    @Value("${spring.mail.password}")
    private String password;
    @Bean
    public JavaMailSender mailSender(){ // Java MailSender 인터페이스를 구현한 객체를 빈으로 등록하기 위함.

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();   // JavaMailSender의 구현체를 생성하고
        mailSender.setHost(host);   //속성을 넣기 시작한다. 이메일 전송에 사용할 SMTP 서버 호스트를 설정
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties = new Properties();   // JavaMail의 속성을 설정하기 위해 Properties 객체를 생성
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.debug", "true");
        javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}
