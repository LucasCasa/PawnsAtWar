package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO {

    private User from;
    private User to;
    private String subject;
    private String message;

    public MessageDTO(User from, User to, String subject, String message){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public User getFrom(){
        return from;
    }

    public void setFrom(User from){
        this.from = from;
    }

    public User getTo(){
        return from;
    }

    public void setTo(User to){
        this.to = to;
    }

    public String getSubject(){
        return subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
