package ar.edu.itba.paw.webapp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessagesDTO {

    private List<MessageDTO> read = new ArrayList<>();
    private List<MessageDTO> unread = new ArrayList<>();

    public UserMessagesDTO( List<MessageDTO> read, List<MessageDTO> unread){
        this.read = read;
        this.unread = unread;
    }

    public List<MessageDTO> getread() { return read; }

    public List<MessageDTO> getUnread() { return unread; }
}
