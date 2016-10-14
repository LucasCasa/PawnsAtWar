package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class BuildingIconTag extends SimpleTagSupport{
	private int id;

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

       /* switch (id){
            case Info.CASTLE:
            	out.print("<img src=\"/webapp/resources/images/castle.png\">");
                break;
            case Info.ARCHERY:
            	out.print("<img src=\"/webapp/resources/images/archery.png\">");
                break;
            case Info.BARRACKS:
            	out.print("<img src=\"/webapp/resources/images/barracks.png\">");
                break;
            case Info.GOLD:
            	out.print("<img src=\"/webapp/resources/images/gold.png\">");
                break;
            case Info.MILL:
            	out.print("<img src=\"/webapp/resources/images/mill.png\">");
                break;
            case Info.BLACKSMITH:
            	out.print("<img src=\"/webapp/resources/images/blacksmith.png\">");
                break;
        }*/
    }
    
    public int getId() {
		return id;
	}
    
    public void setId(int id) {
		this.id = id;
	}
}
