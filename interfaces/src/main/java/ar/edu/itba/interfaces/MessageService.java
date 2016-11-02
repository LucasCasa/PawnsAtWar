package ar.edu.itba.interfaces;

import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;

public interface MessageService {
	public Message createMessage(User from, User to, String message);
	public Message getById(int id);
}