package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

public class TerrainTag extends SimpleTagSupport{
    private int id;
    private String clas= "";

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        switch (id){
            case 0:
                out.print("<img class=\" "+clas + " \" src=\"resources/images/terrain.jpg\">");
                break;
            case 1:
                out.print("<img class=\" " + clas + " \" src=\"resources/images/cas.jpg\">");
                break;
            case 2:
                out.print("<img class=\" " + clas + " \" src=\"resources/images/arch.jpg\">");
                break;
            case 3:
                out.print("<img class=\" " + clas + " \" src=\"resources/images/ba.jpg\">");
                break;
            case 4:
                out.print("<img class=\" " + clas + " \" src=\"resources/images/go.jpg\">");
                break;
            case 5:
                out.print("<img class=\" " + clas + " \" src=\"resources/images/terrgold.jpg\">");
                break;
            case 6:
                out.print("<img class=\" " + clas + " \"  src=\"resources/images/mi.jpg\">");
                break;

        }
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
