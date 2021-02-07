package web.game.avalon.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendMail(String code,String email){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("아발론 레지스탕스 인증 메일입니다");
        message.setText(String.format("인증 코드는 %s 입니다",code));

        javaMailSender.send(message);
    }
}
