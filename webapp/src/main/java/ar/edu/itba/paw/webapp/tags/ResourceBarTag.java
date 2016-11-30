package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ar.edu.itba.model.Resource;
import ar.edu.itba.paw.webapp.beans.ResourceBarBean;

public class ResourceBarTag extends SimpleTagSupport{
	
	private ResourceBarBean rbean;
	private String path;


	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		out.write("<table>");
		out.write("<tbody>");
		out.write("<tr>");
		int index = 0;
		System.err.println("**********************************");
		System.err.println(rbean.getResources());
		for(Resource r: rbean.getResources()){
			 ResourceTag re = new ResourceTag();
			 re.setJspContext(getJspContext());
			 re.setAmount(r.getQuantity());
			 re.setRate(rbean.getRates().get(index++));
			 re.setType(r.getType());
			 re.setPath(path);
			 out.write("<td>");
			 re.doTag();
			 out.write("</td>");
		}
		out.write("<td>");
		out.write("<img class=\"resource\" src=\""+path+"/resources/images/silo.png\"/><span id=\"limit\">"+rbean.getLimit()+"</span>");
		out.write("</td>");
		out.write("</tr>");
		out.write("</tbody>");
		out.write("</table>");
	}

	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public ResourceBarBean getRbean() {
		return rbean;
	}

	public void setRbean(ResourceBarBean rbean) {
		this.rbean = rbean;
	}
}
