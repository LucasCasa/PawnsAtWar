package ar.edu.itba.interfaces;

/**
 * Created by Team Muffin on 26/10/16.
 */
public interface PAWMailService {
    void sendEmail(String toAddress, String subject, String msgBody);
}
