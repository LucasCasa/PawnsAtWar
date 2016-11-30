package ar.edu.itba.paw.webapp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by root on 26/10/16.
 */
public class MessageTag extends SimpleTagSupport {

    private String userFrom;
    private String message;

    public void doTag() throws JspException, IOException{

    }

    public void setUserFrom(String u){
        this.userFrom = u;
    }


    public void setMessage(String message){
        this.message = message;
    }

    public String getUserFrom(){
        return userFrom;
    }

    public String getMessage(){
        return message;
    }

}
