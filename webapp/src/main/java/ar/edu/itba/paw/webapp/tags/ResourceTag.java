package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ResourceTag extends SimpleTagSupport{
	
	private int type;
	private int amount;
	private int rate;
	private String path;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String imgName;
		switch(type){
			case 0:		imgName = "iconResFood.png";
						break;
			case 1:		imgName = "iconResGold.png";
						break;
			default:	imgName = "";
						break;
		}
		out.print("<img class=\"resource\" src=\""+path+"/resources/images/" + imgName + "\"/><span class=\"quantity\" id=\""+ rate +"\">   " + amount + "</span>");
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
	
	public int getRate() {
		return rate;
	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public int getType() {
		return type;
	}
	public void setPath(String path){
		this.path = path;
	}
	public String getPath(){
		return path;
	}
}
