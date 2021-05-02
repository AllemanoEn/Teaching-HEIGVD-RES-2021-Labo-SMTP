package ch.heigvd.res.model;

import java.util.*;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private Person victimRcpt;
    private List<Person> witnessList;
    private String message;
    private String subject;

    /**
     * Constructor of the class Prank
     *
     * @param group group of people to play the prank on
     * @param message  text message to send
     */
    public Prank(Group group, String message){
        victimSender = group.getMembers().elementAt(0);
        victimRcpt = group.getMembers().elementAt(1);
        witnessList = group.getMembers().subList(2, group.getMembers().size());

        int index = message.indexOf("!!");
        this.subject = message.substring(10,index);
        this.message = message.substring(index+4); //Pour enelver les !! et le retour à la ligne
    }

    /**
     * Method to generate mail struct message and return it
     *
     * @return  the mail generated
     */
    public Mail generateMailMessage(){
        Mail mail = new Mail();

        mail.setBody(message);

        mail.setBody(message + "\r\n" + victimSender.getFirstName());

        mail.setTo(victimRcpt.getAddress());

        String[] cc = witnessList.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{});
        mail.setCc(cc);

        mail.setFrom(victimSender.getAddress());

        mail.setSubject(subject);

        return mail;
    }
}
