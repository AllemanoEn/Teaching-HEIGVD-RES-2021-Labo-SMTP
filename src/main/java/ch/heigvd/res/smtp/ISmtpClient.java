package ch.heigvd.res.smtp;

import ch.heigvd.res.model.Mail;

import java.io.IOException;

public interface ISmtpClient {
    void sendMail(Mail mail) throws IOException;
}
