package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ResourceTag extends SimpleTagSupport{
	
	private int type;
	
	private int amount;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String imgName;
		switch(type){
			case 5:		imgName = "iconResGold.png";
						break;
			default:	imgName = "";
						break;
		}
		out.print("<img class=\"resource\" src=\"/webapp/resources/images/" + imgName + "\"/><span class=\"quantity\">   " + amount + "</span>");
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getType() {
		return type;
	}
}
