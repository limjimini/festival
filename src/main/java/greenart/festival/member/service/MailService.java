package greenart.festival.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private static final String senderEmail = "mpi9904@gmail.com";
    private static int number;

    public static void createNumber() {
        number = (int)(Math.random()*(900000)) + 100000;
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = mailSender.createMimeMessage();

        try{
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");

            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";

            message.setText(body, "utf-8", "html");

        }catch(MessagingException e){
            e.printStackTrace();
        }


        return message;
    }

    public int sendMail(String mail){
        MimeMessage message = CreateMail(mail);
        mailSender.send(message);

        return number;
    }
}
