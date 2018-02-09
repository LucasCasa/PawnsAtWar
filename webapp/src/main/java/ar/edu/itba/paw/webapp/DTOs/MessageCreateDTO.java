package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.User;

public class MessageCreateDTO {

    private User to;
    private String subject;
    private String message;

    public MessageCreateDTO(User to,String message, String subject){
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public User getTo(){
    return to;
  }

    public String getMessage(){
        return message;
    }

    public String getSubject(){
        return subject;
    }
}
