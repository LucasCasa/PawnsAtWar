package ar.edu.itba.paw.webapp.tags;

import ar.edu.itba.paw.webapp.dataClasses.InformationBuilding;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by root on 9/18/16.
 */
public class BuildingTag extends SimpleTagSupport {
    private InformationBuilding info;

    private String clas= "";

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        /*
        out.print("<img class=\" "+ clas + " \" src=\"/webapp/resources/images/" + info.getName() + ".png\"/>");
        out.println();
        out.print("<p>" + info.getDescription() + "</p>");
    */

    }

    public void setInfo(InformationBuilding info){
        this.info = info;
    }

    public InformationBuilding getInfo() {
        return info;
    }

    public String getClas() {
        return clas;
    }
    public void setClas(String clas){
        this.clas = clas;
    }
}
