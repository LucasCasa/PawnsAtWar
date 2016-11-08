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
	MessageDao md;

	@Override
	public Message createMessage(User from, User to, String subject, String message) {
		if(from == null || to == null || message == null){
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


}
