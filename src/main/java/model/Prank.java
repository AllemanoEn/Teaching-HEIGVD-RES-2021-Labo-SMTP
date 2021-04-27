package model;

import java.util.*;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private Person victimRcpt;
    private Vector<Person> witnessList = new Vector<>();
    private String message;

    public Prank(Group group, String message){
        victimSender = group.getMembers().elementAt(0);
        victimRcpt = group.getMembers().elementAt(1);
        Collections.copy(witnessList,group.getMembers().subList(2, group.getMembers().size()-1));
        this.message = message;
    }

    public Mail generateMailMessage(){
        Mail mail = new Mail();

        mail.setBody(message);

        mail.setBody(message + "\r\n" + victimSender.getFirstName());

        mail.setTo(victimRcpt.getAddress());

        String[] cc = witnessList.stream().map(person -> person.getAddress()).collect(Collectors.toList()).toArray(new String[]{});
        mail.setCc(cc);

        mail.setFrom(victimSender.getAddress());

        return mail;
    }
}
