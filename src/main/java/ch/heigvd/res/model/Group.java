package ch.heigvd.res.model;

import java.util.*;

import lombok.Getter;

public class Group {
    @Getter private Vector<Person> members = new Vector<>();

    public void addMember(Person person){
        members.add(person);
    }
}
