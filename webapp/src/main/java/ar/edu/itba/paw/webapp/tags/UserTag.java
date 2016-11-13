package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ar.edu.itba.model.User;

public class UserTag extends SimpleTagSupport{
	
	private User user;
	private String path;
	

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.write("<form name=\"myForm"+user.getId()+"\" method=\"get\" action=\""+path+"/locate\"/>");
		out.write("<input type=\"hidden\" name=\"user\" value=\""+ user.getId() +"\">");
		out.write("<a href=\"#\" onclick=\"submitform("+user.getId()+");\">"+user.getName()+"</a>");
		out.write("<input type=\"submit\" value=\"\" style=\"display:none\" />");
        out.write("</form>");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
}
