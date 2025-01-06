package ma.zyn.app.utils.transverse.emailling;


import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String email;

    public void sendSimpleMessage(EmailRequest emailRequest) {
        System.out.println("sending email");
        System.out.println(emailRequest.getBody());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setFrom("blackruby.clothes@gmail.com");
        message.setText(emailRequest.getBody());
        message.setSubject(emailRequest.getSubject());

        mailSender.send(message);

        System.out.println("mail sent!");
    }

    public void sendEmailWithAttachment(String to, String subject, String messageText, String fileName, byte[] attachmentData) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("mohamedsaad.daali@gmail.com");
            helper.setFrom("blackruby.clothes@gmail.com");
            helper.setSubject(subject);
            helper.setText(messageText);

            // Convert byte[] to ByteArrayDataSource
            DataSource dataSource = new ByteArrayDataSource(attachmentData, "application/pdf");

            // Attach the PDF
            helper.addAttachment(fileName, dataSource);

            // Send the email
            mailSender.send(message);
            System.out.println("Email with attachment sent to: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
