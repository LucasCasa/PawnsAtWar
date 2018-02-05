package ar.edu.itba.paw.webapp.DTOs;

public class MessageCreateDTO {

    private String message;
    private String subject;

    public MessageCreateDTO(String message, String subject){
        this.message = message;
        this.subject = subject;
    }

    public String getMessage(){
        return message;
    }

    public String getSubject(){
        return subject;
    }
}
