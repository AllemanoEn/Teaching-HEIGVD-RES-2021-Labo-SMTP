package smtp;

import model.Mail;

import java.io.IOException;

public interface ISmtpClient {
    void sendMail(Mail mail) throws IOException;
}
