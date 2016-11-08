package ar.edu.itba.interfaces;

import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;

import java.util.List;

public interface MessageService {
	public Message createMessage(User from, User to, String subject,String message);
	public Message getById(Long id);
	public List<Message> getAllMessages(User u);
	public void deleteMessage(Long id);
	public void deleteMessage(Message mssg);
}

