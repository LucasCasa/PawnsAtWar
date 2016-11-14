package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ResourceTag extends SimpleTagSupport{
	
	private int type;
	private double amount;
	private double rate;
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
		if(Math.floor(amount) == amount){
			out.print("<img class=\"resource\" src=\""+path+"/resources/images/" + imgName + "\"/><span class=\"quantity\" id=\""+ rate +"\">   " + ((int)amount) + "</span>");
		}else{
			DecimalFormat df = new DecimalFormat("#.#");
			out.print("<img class=\"resource\" src=\""+path+"/resources/images/" + imgName + "\"/><span class=\"quantity\" id=\""+ rate +"\">   " + df.format(amount) + "</span>");
		}

	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
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
