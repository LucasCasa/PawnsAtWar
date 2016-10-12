package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ar.edu.itba.paw.webapp.dataClasses.BuildingInformationMap;

public class BuildingIconTag extends SimpleTagSupport{
	private int id;

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

       /* switch (id){
            case BuildingInformationMap.CASTLE:
            	out.print("<img src=\"/webapp/resources/images/castle.png\">");
                break;
            case BuildingInformationMap.ARCHERY:
            	out.print("<img src=\"/webapp/resources/images/archery.png\">");
                break;
            case BuildingInformationMap.BARRACKS:
            	out.print("<img src=\"/webapp/resources/images/barracks.png\">");
                break;
            case BuildingInformationMap.GOLD:
            	out.print("<img src=\"/webapp/resources/images/gold.png\">");
                break;
            case BuildingInformationMap.MILL:
            	out.print("<img src=\"/webapp/resources/images/mill.png\">");
                break;
            case BuildingInformationMap.BLACKSMITH:
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
