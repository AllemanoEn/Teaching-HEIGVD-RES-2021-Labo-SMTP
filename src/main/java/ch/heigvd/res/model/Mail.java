package ch.heigvd.res.model;

import lombok.Getter;
import lombok.Setter;

public class Mail {
    @Getter @Setter private String from;
    @Getter @Setter private String to;
    @Getter @Setter private String[] cc = new String[0];    //Carbon Copy
    @Getter @Setter private String subject;
    @Getter @Setter private String body;
}
