package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessagesDTO {

    private User user;
    private List<MessageDTO> readMessages = new ArrayList<>();
    private List<MessageDTO> unreadMessages = new ArrayList<>();

    public UserMessagesDTO(User user, List<MessageDTO> readMessages, List<MessageDTO> unreadMessages){
        this.user = user;
        this.readMessages = readMessages;
        this.unreadMessages = unreadMessages;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public List<MessageDTO> getReadMessages() { return readMessages; }

    public List<MessageDTO> getUnreadMessages() { return unreadMessages; }
}
