package ch.heigvd.res.model;

import java.util.*;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private Person victimRcpt;
    private List<Person> witnessList = new ArrayList<>();
    private String message;
    private String subject;

    public Prank(Group group, String message){
        victimSender = group.getMembers().elementAt(0);
        victimRcpt = group.getMembers().elementAt(1);
        witnessList = group.getMembers().subList(2, group.getMembers().size());

        int index = message.indexOf("!!");
        this.subject = message.substring(10,index);
        this.message = message.substring(index+4); //Pour enelver les !! et le retour à la ligne
    }

    public Mail generateMailMessage(){
        Mail mail = new Mail();

        mail.setBody(message);

        mail.setBody(message + "\r\n" + victimSender.getFirstName());

        mail.setTo(victimRcpt.getAddress());

        String[] cc = witnessList.stream().map(person -> person.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        mail.setCc(cc);

        mail.setFrom(victimSender.getAddress());

        mail.setSubject(subject);

        return mail;
    }
}
