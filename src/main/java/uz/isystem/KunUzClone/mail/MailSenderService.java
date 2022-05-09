package uz.isystem.KunUzClone.mail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final MailSender mailSender;

    public void sendMail(String content, String email){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setText(content);
        mailMessage.setSubject("Kun Uz Verification!");
        mailSender.send(mailMessage);
    }
}
