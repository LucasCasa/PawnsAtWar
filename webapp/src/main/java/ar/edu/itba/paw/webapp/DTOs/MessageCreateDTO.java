package ar.edu.itba.paw.webapp.DTOs;

public class MessageCreateDTO {

    private String to;
    private String subject;
    private String message;

  public MessageCreateDTO(){

  }
    public MessageCreateDTO(String to,String message, String subject){
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public String getTo(){
    return to;
  }

    public String getMessage(){
        return message;
    }

    public String getSubject(){
        return subject;
    }

  public void setTo(String to) {
    this.to = to;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
