package ch.heigvd.res.smtp;

import ch.heigvd.res.model.Mail;

import java.io.IOException;

public interface ISmtpClient {
    /**
     * Method sendMail, to send an email
     *
     * @param mail we want to send
     */
    void sendMail(Mail mail) throws IOException;
}
