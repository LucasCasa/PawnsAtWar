package ar.edu.itba.paw.webapp.tags;

import ar.edu.itba.model.SectorType;

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
        out.print("<img class=\" " + clas + " " + status + " \" src=\""+path+"/resources/images/"+ SectorType.get(id).toString()+".jpg\">");

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
