package ar.edu.itba.paw.webapp.tags;

import ar.edu.itba.model.Point;
import ar.edu.itba.paw.webapp.dataClasses.BuildingInformationMap;
import ar.edu.itba.paw.webapp.dataClasses.InformationBuilding;
import org.springframework.web.servlet.tags.UrlTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by Muffin on 9/18/16.
 */
public class BuildingTag extends SimpleTagSupport {
    private InformationBuilding info;
    private String clas= "";
    private String build;
    private Point point;


    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        switch (info.getId()) {
            case BuildingInformationMap.EMPTY:
            case BuildingInformationMap.TERR_GOLD:
                printNew(out, info.getId() == BuildingInformationMap.TERR_GOLD);
                break;
            case BuildingInformationMap.CASTLE:
                printCastle();
                break;
            case BuildingInformationMap.ARCHERY:
                printArchery();
                break;
            case BuildingInformationMap.BARRACKS:
                printBarrack();
                break;
            case BuildingInformationMap.GOLD:
                printGoldMine();
                break;
            case BuildingInformationMap.MILL:
                printMill();
                break;
            case BuildingInformationMap.BLACKSMITH:
                printBlacksmith();
                break;
        }
    }

    private void printButtons() throws JspException,IOException{
        JspWriter out = getJspContext().getOut();
        out.println("<button class=\"myButton\">Demoler</button>");
        out.println("<button class=\"myButton\" >Aumentar de nivel</button>");
    }
    private void printArchery() throws JspException,IOException{
        JspWriter out = getJspContext().getOut();
        printButtons();
        printCreateTroop(BuildingInformationMap.ARCHER);
        printTable("Tiempo de creacion",BuildingInformationMap.ARCHERY,1);
    }
    private void printBarrack() throws JspException,IOException{
        printButtons();
        printCreateTroop(BuildingInformationMap.WARRIOR);
        printTable("Tiempo de creacion",BuildingInformationMap.BARRACKS,1);

    }
    private void printStable() throws JspException,IOException{
        printButtons();
        printCreateTroop(BuildingInformationMap.HORSEMAN);
        //printTable("Tiempo de creacion",BuildingInformationMap.STABLE,1);
    }
    private void printCreateTroop(int type) throws JspException,IOException{
        JspWriter out = getJspContext().getOut();
        out.println("<br><br><br>");
        out.println("<div class=\"row\"><form class=\"form-inline\">");
        out.println("<input placeholder=\"Cantidad\" type=\"number\" style=\"font-size: 18px;\" name=\"amount\">");
        out.println("<input  type=\"hidden\" name=\"type\" value=\""+type+"\">");
        out.println("<input value=\"Entrenar\" class=\"myButton\" type=\"submit\">");
        out.println("</form></div>");

    }
    private void printNew(JspWriter out, boolean gold) throws JspException, IOException  {
        out.println("<div class=\"col-md-2\"><button class=\"myButton\" id=\"buildBtn\">Construir</button></div>");
        out.println("<div class=\"container\" id=\"buildList\" style=\"display: none;\">");
        out.println("<table id=\"buildListTable\">");
        out.println("<thead>");
        out.println("<td>Edificio</td>");
        out.println("<td>Costo</td>");
        out.println("<td>Construir</td>");
        out.println("</thead>");
        out.println("<tbody>");
        if(!gold) {
            for (InformationBuilding i : BuildingInformationMap.getInstance().getConstructable(info.getId())) {
                out.println("<tr>");
                out.println("<td>");
                printImage(out,i.getId());
                out.println("</td>");
                out.println("<td>");
                ResourceTag re = new ResourceTag();
                re.setJspContext(getJspContext());
                re.setAmount(1000);
                re.setRate(0);
                re.setType(1);
                re.doTag();
                ResourceTag re2 = new ResourceTag();
                re2.setJspContext(getJspContext());
                re2.setAmount(1000);
                re2.setRate(0);
                re2.setType(0);
                re2.doTag();
                out.println("</td>");
                out.println("<td>");
                out.println("<form method=\"post\" action=\""+ build +"\">");
                out.println("<input type=\"hidden\" name=\"x\" value=\"" + point.getX() + "\"/>");
                out.println("<input type=\"hidden\" name=\"y\" value=\"" + point.getY() + "\"/>");
                out.println("<input type=\"hidden\" name=\"type\" value=\"" + i.getId() + "\"/>");
                out.println("<input type=\"submit\" class=\"myButton\" value=\"OK\"/>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");

            }
        }
        if(info.getId() == BuildingInformationMap.TERR_GOLD) {
            out.println("<tr>");
            out.println("<td>");
            printImage(out, BuildingInformationMap.GOLD);
            out.println("</td>");
            out.println("<td>");
            ResourceTag re = new ResourceTag();
            re.setJspContext(getJspContext());
            re.setAmount(1000);
            re.setRate(0);
            re.setType(1);
            re.doTag();
            out.println("</td>");
            out.println("<td>");
            out.println("<form method=\"post\" action=\""+ build +"\">");
            out.println("<input type=\"hidden\" name=\"x\" value=\"" + point.getX() + "\"/>");
            out.println("<input type=\"hidden\" name=\"y\" value=\"" + point.getY() + "\"/>");
            out.println("<input type=\"hidden\" name=\"type\" value=\"" + BuildingInformationMap.GOLD + "\"/>");
            out.println("<input type=\"submit\" class=\"myButton\" value=\"OK\"/>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");

        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
    }
    private void printMill() throws JspException,IOException{
        printButtons();
    }
    private void printBlacksmith() throws JspException,IOException{
        printButtons();
    }
    private void printCastle() throws JspException,IOException{
        printButtons();
    }
    private void printGoldMine() throws JspException,IOException{
        printButtons();
    }
    private void printImage(JspWriter out,int id) throws JspException, IOException{
            switch (id){
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
            }
    }
    public void printTable(String text,int type,int level) throws JspException, IOException{
        JspWriter out = getJspContext().getOut();
        out.println("<br><br><br>");
        out.println("<table class=\"table table-striped\" id=\"Level\">");
        out.println("<thead>");
        out.println("<td>Nivel</td>");
        out.println("<td>"+text+"</td>");
        out.println("<td>Costo</td>");
        out.println("<td>Tiempo</td>");
        out.println("</thead>");
        out.println("<tbody>");
        for(int i = 1 ; i<=40;i++){
            out.println("<tr>");
            out.println("<td>"+i +"</td><td>PlaceHolder</td><td>"+ (1000 + i*i*i*i) +"</td><td>00:"+i+":00</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");

    }
    public void setInfo(InformationBuilding info){
        this.info = info;
    }
    public void setPoint(Point p){
            point = p;
        }
    public InformationBuilding getInfo() {
        return info;
    }
    public Point getPoint(){
            return point;
        }
    public String getClas() {
        return clas;
    }
    public void setBuild(String build){
        this.build = build;
    }
    public String getBuild() {
        return build;
    }

    public void setClas(String clas){
        this.clas = clas;
    }
}
