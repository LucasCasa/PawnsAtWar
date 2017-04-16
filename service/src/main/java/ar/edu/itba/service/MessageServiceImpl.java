package ar.edu.itba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDao md;

	@Override
	public Message createMessage(User from, User to, String subject, String message) {
		if(from == null || to == null || message == null || from.equals(to)){
			return null;
		}
		return md.createMessage(from, to, subject, message);
	}

	@Override
	public Message getById(Long id) {
		if(id < 0){
			return null;
		}
		return md.getById(id);
	}

	@Override
	public List<Message> getAllMessages(User u) {
		return md.getAllMessages(u);
	}

	@Override
	public void deleteMessage(Long id) {
		md.removeMessage(id);
	}

	@Override
	public void deleteMessage(Message mssg) {
		deleteMessage(mssg.getId());
	}

	@Override
	public void deleteMessages(User user) {
		List<Message> received = user.getReceivedMessages();
		List<Message> sent = user.getSentMessages();
		md.removeMessages(sent);
		md.removeMessages(received);
		
	}


	@Override
	public int countUnreadMessages(User u){
		return md.countUnreadMessages(u);
	}

	@Override
	public void markAsRead(Long id) {
		if(!md.getById(id).isRead())
			md.markAsRead(id);
	}

	@Override
	public List<Message> getReadMessages(User u) {
		return md.getReadMesssages(u);
	}

	@Override
	public List<Message> getUnreadMessages(User u) {
		return md.getUnReadMessages(u);
	}

	@Override
	public List<Message> getSentMessages(User u) {
		return md.getSentMessages(u);
	}

}
