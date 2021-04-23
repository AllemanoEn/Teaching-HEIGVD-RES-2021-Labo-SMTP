package smtp;

import model.Mail;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SmtpClient implements ISmtpClient{

    private String smtpServerAddress;
    private int smtpServerPort;

    private BufferedReader br;
    private PrintWriter pw;
    private Socket clientSocket;

    public SmtpClient(String smtpServerAddress,int smtpServerPort){
        this.smtpServerAddress=smtpServerAddress;
        this.smtpServerPort=smtpServerPort;
    }

    @Override
    public void sendMail(Mail mail) throws IOException {
        clientSocket = new Socket(smtpServerAddress, smtpServerPort);

        System.out.println("-- STARTING SMPT CONNECTION --");

        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);



        String msg = br.readLine();

        System.out.println(msg);

        pw.println("coucou");
        pw.flush();

        msg = br.readLine();

        System.out.println(msg);

        pw.close();
        br.close();
        clientSocket.close();
    }
}
