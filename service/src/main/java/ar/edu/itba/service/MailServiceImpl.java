package ar.edu.itba.service;

import ar.edu.itba.interfaces.PAWMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
/**
 * Created by Team Muffin on 26/10/16.
 */
@Service
public class MailServiceImpl implements PAWMailService{

    @Autowired
    private MailSender mailSender; // MailSender interface defines a strategy
    // for sending simple mails

    public void sendEmail(String toAddress, String subject, String msgBody) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAddress);
        msg.setSubject(subject);
        msg.setText(msgBody);
        msg.setReplyTo("PawnsAtWar");
        mailSender.send(msg);
    }
}
