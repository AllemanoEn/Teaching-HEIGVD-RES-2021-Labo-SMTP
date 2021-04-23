package model;

import java.util.*;

import lombok.Getter;

public class Group {
    @Getter private List<Person> members = new ArrayList<>();

    public void addMember(Person person){
        members.add(person);
    }

}
