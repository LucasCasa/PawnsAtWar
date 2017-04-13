package ar.edu.itba.paw.webapp.tags;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.MessageSource;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.SectorType;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.InformationBuilding;

/**
 * Created by Muffin on 9/18/16.
 */
public class BuildingTag extends SimpleTagSupport {

	private Alert alert;
	private MessageSource messageSource;
    private Locale locale;
    private InformationBuilding info;
    private String clas= "";
    private String path;
    private Point point;
    private Integer level;
    private Integer price;


    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        switch (info.getId()) {
            case Info.EMPTY:
            case Info.TERR_GOLD:
                if(alert == null) {
                    printNew(out, info.getId() == Info.TERR_GOLD);
                }else{
                    out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
                }
                break;
            case Info.CASTLE:
                printCastle();
                break;
            case Info.ARCHERY:
                printArchery();
                break;
            case Info.BARRACKS:
                printBarrack();
                break;
            case Info.GOLD:
                printGoldMine();
                break;
            case Info.MILL:
                printMill();
                break;
            case Info.STABLE:
                printStable();
                break;
        }
    }

    private void printButtons() throws JspException,IOException{
        JspWriter out = getJspContext().getOut();
        out.println("<div class=\"row\">");
        out.println("<div class=\"col-md-2\">");
        out.println("<form action=\"demolish\" method=\"post\"  onsubmit=\"return confirm('" + messageSource.getMessage("warningDemolish", null,locale) +"');\">");
        out.println("<input type=\"hidden\" name=\"x\" value=\""+point.getX()+"\"/>");
        out.println("<input type=\"hidden\" name=\"y\" value=\""+point.getY()+"\"/>");
        out.println("<input type=\"submit\" value=\""+messageSource.getMessage("demolish",null,locale)+"\"class=\"myButton\"/>");
        out.println("</form>");
        out.println("</div>");
        out.println("<div class=\"col-md-1\">");
        out.println("<form action=\"levelup\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"x\" value=\""+point.getX()+"\"/>");
        out.println("<input type=\"hidden\" name=\"y\" value=\""+point.getY()+"\"/>");
        out.println("<input type=\"submit\" class=\"myButton\" value=\""+ messageSource.getMessage("levelup",null,locale)+"\"/>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");

    }
    private void printArchery() throws JspException,IOException{
    	JspWriter out = getJspContext().getOut();
    	if(alert==null){
	        printButtons();
	        printCreateTroop(Info.ARCHER);
    	}else{
            out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
    	}
        printTable(messageSource.getMessage("troopCost",null,locale), Info.ARCHERY,level);
    }
    private void printBarrack() throws JspException,IOException{
    	JspWriter out = getJspContext().getOut();
    	if(alert==null){
	        printButtons();
	        printCreateTroop(Info.WARRIOR);
    	}else{
    		out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
    	}
        printTable(messageSource.getMessage("troopCost",null,locale), Info.BARRACKS,level);

    }
    private void printStable() throws JspException,IOException{
    	JspWriter out = getJspContext().getOut();
    	if(alert==null){
	        printButtons();
	        printCreateTroop(Info.HORSEMAN);
    	}else{
    		out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
    	}
        printTable(messageSource.getMessage("troopCost",null,locale),Info.STABLE,level);
    }
    private void printCreateTroop(int type) throws JspException,IOException{
        JspWriter out = getJspContext().getOut();
        out.println("<br><br><br>");
        out.println("<div class=\"row\" style=\"margin-left: 0px;\"><form class=\"form-inline\" method=\"post\" action=\""+ path + "/train\" >");
        out.println("<input placeholder=\""+messageSource.getMessage("amount",null,locale)+"\" type=\"number\" style=\"font-size: 18px;\" name=\"amount\" min=\"1\">");
        out.println("<input  type=\"hidden\" name=\"type\" value=\""+type+"\">");
        out.println("<input  type=\"hidden\" name=\"px\" value=\""+ point.getX() +"\">");
        out.println("<input  type=\"hidden\" name=\"py\" value=\""+ point.getY() +"\">");
        out.println("<input value=\""+messageSource.getMessage("train",null,locale)+"\" class=\"myButton\" type=\"submit\">");
        out.println("</form></div>");

    }
    private void printNew(JspWriter out, boolean gold) throws JspException, IOException  {
        out.println("<table style=\"table-layout: fixed;\"id=\"buildListTable\">");
        out.println("<thead>");
        out.println("<td >"+messageSource.getMessage("building",null,locale)+"</td>");
        out.println("<td >"+messageSource.getMessage("description",null,locale)+"</td>");
        out.println("<td>"+messageSource.getMessage("cost",null,locale)+"</td>");
        out.println("<td>"+messageSource.getMessage("build",null,locale)+"</td>");
        out.println("</thead>");
        out.println("<tbody>");
        if(!gold) {
            for (Integer i : Info.getConstructable(info.getId())) {
                out.println("<tr>");
                out.println("<td>");
                printImage(out,i);
                out.println("</td>");
                out.println("<td style=\"text-align: center;\">");
                out.println(messageSource.getMessage("description." + SectorType.get(i).toString(),null,locale));
                out.println("</td>");
                out.println("<td style=\"text-align: center;\">");
                ResourceTag re = new ResourceTag();
                re.setJspContext(getJspContext());
                re.setAmount(price);
                re.setRate(0);
                re.setType(1);
                re.setPath(path);
                re.doTag();
                out.println("</td>");
                out.println("<td>");
                out.println("<form method=\"post\" action=\""+ path +"/build\">");
                out.println("<input type=\"hidden\" name=\"x\" value=\"" + point.getX() + "\"/>");
                out.println("<input type=\"hidden\" name=\"y\" value=\"" + point.getY() + "\"/>");
                out.println("<input type=\"hidden\" name=\"type\" value=\"" + i + "\"/>");
                out.println("<input type=\"submit\" class=\"myButton\" value=\""+messageSource.getMessage("build",null,locale)+"\"/>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");

            }
        }
        if(info.getId() == Info.TERR_GOLD) {
            out.println("<tr>");
            out.println("<td>");
            printImage(out, Info.GOLD);
            out.println("</td>");
            out.println("<td>");
            out.println(messageSource.getMessage("description.GOLD",null,locale));
            out.println("</td>");
            out.println("<td style=\" text-align: center;\">");
            ResourceTag re = new ResourceTag();
            re.setJspContext(getJspContext());
            re.setAmount(price);
            re.setRate(0);
            re.setType(1);
            re.setPath(path);
            re.doTag();
            out.println("</td>");
            out.println("<td>");
            out.println("<form method=\"post\" action=\""+ path +"/build\">");
            out.println("<input type=\"hidden\" name=\"x\" value=\"" + point.getX() + "\"/>");
            out.println("<input type=\"hidden\" name=\"y\" value=\"" + point.getY() + "\"/>");
            out.println("<input type=\"hidden\" name=\"type\" value=\"" + Info.GOLD + "\"/>");
            out.println("<input type=\"submit\" class=\"myButton\" value=\"OK\"/>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");

        }
        out.println("</tbody>");
        out.println("</table>");
    }
    private void printMill() throws JspException,IOException{
    	JspWriter out = getJspContext().getOut();
    	if(alert==null){
	        printButtons();
    	}else{
    		out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
    	}
        printTable(messageSource.getMessage("foodGen",null,locale), Info.MILL,level);
    }

    private void printCastle() throws JspException,IOException{
    	JspWriter out = getJspContext().getOut();
    	if(alert==null){
	        printButtons();
    	}else{
    		out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
    	}
        printTable(messageSource.getMessage("storeAmount",null,locale), Info.CASTLE,level);
    }
    private void printGoldMine() throws JspException,IOException{
    	JspWriter out = getJspContext().getOut();
    	if(alert==null){
	        printButtons();
    	}else{
    		out.println("<h1>"+messageSource.getMessage(alert.getType().toString(), null,locale)+"</h1>");
    	}
        printTable(messageSource.getMessage("goldGen",null,locale), Info.GOLD,level);
    }
    private void printImage(JspWriter out,int id) throws JspException, IOException{
        out.print("<img src=\""+ path +"/resources/images/"+SectorType.get(id).toString()+".png\">");
    }
    public void printTable(String text,int type,int level) throws JspException, IOException{
        JspWriter out = getJspContext().getOut();
        
        int costAm = Info.getCostBuilding(type,level);
        
        ResourceTag cost = new ResourceTag();
        cost.setJspContext(getJspContext());
        cost.setAmount(costAm);
        cost.setRate(0);
        cost.setType(1);
        cost.setPath(path);
        
        out.println("<div class=\"row\" style=\"margin-left: 0px;\">");
        out.println("<br><br><br>");
        out.println("<table class=\"table table-striped\" id=\"Level\">");
        out.println("<thead>");
        out.println("<td>"+messageSource.getMessage("level",null,locale)+"</td>");
        out.println("<td>"+text+"</td>");
        out.println("<td>"+messageSource.getMessage("cost",null,locale)+"</td>");
        //out.println("<td>"+messageSource.getMessage("time",null,locale)+"</td>");
        out.println("</thead>");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td><b>"+level +"</b></td><td><b>");
        if(type != Info.CASTLE){
	        ResourceTag bonus = new ResourceTag();
	        bonus.setJspContext(getJspContext());
	        bonus.setAmount(Info.getBonus(type, level));
	        bonus.setRate(0);
	        bonus.setType(Info.getBonusType(type));
	        bonus.setPath(path);
	        bonus.doTag();
        }else{
        	out.println("<img class=\"resource\" src=\""+path+"/resources/images/silo.png\"/><span>"+costAm+"</span>");
        }
        out.println("</b></td><td><b>");
        cost.doTag();
        out.println("</b></td>");//<td><b>00:"+level+":00</b></td>");
        out.println("</tr>");
        for(int i = level+1 ; i<=20;i++){
        	int amount = Info.getCostBuilding(type,i);
        	ResourceTag c = new ResourceTag();
            c.setJspContext(getJspContext());
            c.setAmount(amount);
            c.setRate(0);
            c.setType(1);
            c.setPath(path);
            
            out.println("<tr>");
            out.println("<td>"+i +"</td><td>");
            if(type != Info.CASTLE){
	            ResourceTag re = new ResourceTag();
	            re.setJspContext(getJspContext());
	            re.setAmount(Info.getBonus(type, i));
	            re.setRate(0);
	            re.setType(Info.getBonusType(type));
	            re.setPath(path);
	            re.doTag();
            }else{
            	out.println("<img class=\"resource\" src=\""+path+"/resources/images/silo.png\"/><span>"+amount+"</span>");
            }
            out.println("</td><td>");
            c.doTag();
            out.println("</td>");//<td>00:"+i+":00</td>");
            out.println("</tr>");

        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
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
    public void setPath(String path){
        this.path = path;
    }
    public String getPath() {
        return path;
    }
    public void setClas(String clas){
        this.clas = clas;
    }
    public void setLocale(Locale l){
        this.locale = l;
    }
    public Locale getLocale(){
        return locale;
    }
    public void setMessageSource(MessageSource ms){
        messageSource = ms;
    }
    public MessageSource getMessageSource(){
        return messageSource;
    }
    public Integer getLevel(){
        return level;
    }
    public void setLevel(Integer level){
        this.level = level;
    }
    public Integer getPrice(){
    	return price;
    }
    public void setPrice(Integer price){
    	this.price=price;
    }
    public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}
}
