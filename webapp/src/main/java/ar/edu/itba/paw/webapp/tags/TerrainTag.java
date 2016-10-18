package ar.edu.itba.paw.webapp.tags;

import ar.edu.itba.paw.webapp.dataClasses.Info;

import java.io.IOException;

import javax.servlet.jsp.tagext.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.*;

public class TerrainTag extends SimpleTagSupport{
	@Autowired
	HttpServletRequest request;
    private int id;
    private String clas= "";
    private int ownerid;
    private int userid;
    private String path;

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        String status = "";
        if(ownerid == userid){
        	status = "friendly";
        }else if(ownerid>0){
        	status = "hostile";
        }
       
        switch (id){
            case Info.EMPTY:
                out.print("<img class=\" " + clas + " " + status + " \" src=\""+path+"/resources/images/terrain.jpg\">");
                break;
            case Info.CASTLE:
                out.print("<img class=\" " + clas + " " + status + " \" src=\""+path+"/resources/images/castle.jpg\">");
                break;
            case Info.ARCHERY:
                out.print("<img class=\" " + clas + " " + status+ " \" src=\""+path+"/resources/images/archery.jpg\">");
                break;
            case Info.BARRACKS:
                out.print("<img class=\" " + clas + " " + status+ " \" src=\""+path+"/resources/images/barracks.jpg\">");
                break;
            case Info.GOLD:
                out.print("<img class=\" " + clas + " " + status + " \" src=\""+path+"/resources/images/gold.jpg\">");
                break;
            case Info.TERR_GOLD:
                out.print("<img class=\" " + clas + " " + status + " \" src=\""+path+"/resources/images/terrgold.jpg\">");
                break;
            case Info.MILL:
                out.print("<img class=\" " + clas + " " + status + " \"  src=\""+path+"/resources/images/mill.jpg\">");
                break;
            case Info.BLACKSMITH:
                out.print("<img class=\" " + clas + " " + status + " \"  src=\""+path+"/resources/images/blacksmith.jpg\">");
                break;
            case Info.STABLE:
                out.print("<img class=\" " + clas + " " + status + " \"  src=\""+path+"/resources/images/stable.jpg\">");
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
    public void setPath(String path){
    	this.path = path;
    }
    public String getPath(){
    	return path;
    }
}
