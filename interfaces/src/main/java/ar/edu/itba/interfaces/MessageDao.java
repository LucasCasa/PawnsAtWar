package ar.edu.itba.interfaces;

import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;

import java.util.List;

public interface MessageDao {
	public Message createMessage(User from, User to, String message);
	public Message getById(int id);
	public List<Message> getAllMessages(User u);
	
}
