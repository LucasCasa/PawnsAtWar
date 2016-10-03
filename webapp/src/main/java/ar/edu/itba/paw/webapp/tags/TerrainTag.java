package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

public class TerrainTag extends SimpleTagSupport{
    private int id;
    private String clas= "";
    private int ownerid;
    private int userid;

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        String status = "";
        if(ownerid == userid){
        	status = "friendly";
        }else if(ownerid>0){
        	status = "hostile";
        }
        switch (id){
            case 0:
                out.print("<img class=\" " + clas + " " + status + " \" src=\"/webapp/resources/images/terrain.jpg\">");
                break;
            case 1:
                out.print("<img class=\" " + clas + " " + status + " \" src=\"/webapp/resources/images/castle.jpg\">");
                break;
            case 2:
                out.print("<img class=\" " + clas + " " + status+ " \" src=\"/webapp/resources/images/archery.jpg\">");
                break;
            case 3:
                out.print("<img class=\" " + clas + " " + status+ " \" src=\"/webapp/resources/images/barracks.jpg\">");
                break;
            case 4:
                out.print("<img class=\" " + clas + " " + status + " \" src=\"/webapp/resources/images/gold.jpg\">");
                break;
            case 5:
                out.print("<img class=\" " + clas + " " + status + " \" src=\"/webapp/resources/images/terrgold.jpg\">");
                break;
            case 6:
                out.print("<img class=\" " + clas + " " + status + " \"  src=\"/webapp/resources/images/mill.jpg\">");
                break;
            case 7:
                out.print("<img class=\" " + clas + " " + status + " \"  src=\"/webapp/resources/images/blacksmith.jpg\">");
                break;

        }
    }
    
    public int getUserid() {
		return userid;
	}
    
    public int getOwnerid() {
		return ownerid;
	}
    
    public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}
    
    public void setUserid(int userid) {
		this.userid = userid;
	}
    
    public void setId(int id){
      this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getClas() {
        return clas;
    }
    public void setClas(String clas){
        this.clas = clas;
    }
}
