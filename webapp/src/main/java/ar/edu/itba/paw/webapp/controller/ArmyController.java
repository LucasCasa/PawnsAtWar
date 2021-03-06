package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * Created by Team Muffin on 18/09/16.
 */
@Controller
public class ArmyController {

	@Autowired
	private ArmyService as;
	@Autowired
	private SectorService ss;
	@Autowired
	private TroopService ts;
	@Autowired
	private EmpireService es;
	@Autowired
	private UserService us;
	@Autowired
	private ScheduleService sh;
	@Autowired
	private AlertService als;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PAWMailService mailService;
	@Autowired
	private MessageService ms;

	@RequestMapping(value="/armies")
	public ModelAndView showArmies(@ModelAttribute("userId") final User user,
			@RequestParam(value= "x",required = false) String x ,
			@RequestParam(value= "y",required = false) String y){
		if(user == null){
			return new ModelAndView("redirect:/");
		}
		final ModelAndView mav = new ModelAndView("armies");
		List<Army> armies =  as.getArmies(user);

		if(Validator.validBoardPosition(x) && Validator.validBoardPosition(y)){
			mav.addObject("x",x);
			mav.addObject("y",y);
		}
		if(armies == null){
			armies = new ArrayList<>();
		}

		int unreadMessages = ms.countUnreadMessages(user);

		mav.addObject("armies",armies);
		mav.addObject("size",armies.size());
		mav.addObject("unreadMessages", unreadMessages);

		return mav;
	}

	@RequestMapping(value="/armies/{armyId}")
	public ModelAndView showArmy(@PathVariable String armyId,
			@RequestParam(value= "x",required = false) String x ,
			@RequestParam(value= "y",required = false) String y,
			@ModelAttribute("userId") final User user,
			Locale locale){
		if(user == null){
			return new ModelAndView("redirect:/");
		}
		final ModelAndView mav = new ModelAndView("army");

		if(Validator.validBoardPosition(x) && Validator.validBoardPosition(y)){
			mav.addObject("x",x);
			mav.addObject("y",y);
		}else{

			mav.addObject("x","");
			mav.addObject("y","");
		}

		if(armyId == null || !Validator.isInteger(armyId)) {
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
		}
		int id = Integer.parseInt(armyId);

		Army army = as.getArmyById(id);
		if(army == null){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notExistArmy",null,locale));
		}
		if(!as.belongs(user ,id)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
		}
		List<Army> armies = as.getArmies(user);
		if(armies == null){
			armies = new ArrayList<>();
		}
		int unreadMessages = ms.countUnreadMessages(user);


		List<Troop> troops = army.getTroops();
		mav.addObject("unreadMessages", unreadMessages);
		mav.addObject("armies",armies);
		mav.addObject("armySize",armies.size());
		mav.addObject("army",army);
		mav.addObject("troops",troops);
		return mav;
	}

	@RequestMapping(value="/attack", method = RequestMethod.POST)
	public ModelAndView attack(@ModelAttribute("userId") final User user,
			@RequestParam(value= "x",required = true) String x,
			@RequestParam(value= "y",required = true) String y,
			@RequestParam(value= "armyId",required = true) String armyId,
			Locale locale){
		if(user == null){
			return new ModelAndView("redirect:/");
		}
		final ModelAndView mav = new ModelAndView("attack");
		if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y) ){
			return new ModelAndView("redirect:/error?"+ messageSource.getMessage("error.invalidPosition",null,locale));
		}
		if(!Validator.isInteger(armyId)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
		}
		int id = Integer.parseInt(armyId);
		if(!as.belongs(user ,id)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
		}
		int xprime = Integer.parseInt(x);
		int yprime = Integer.parseInt(y);
		
		Sector s = ss.getSector(new Point(xprime,yprime));
		if(s == null){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notBuildingInPosition",null,locale));

		}
		if(!as.getArmyById(id).getAvailable()){
			return new ModelAndView("redirect:/error");
		}
		if(s.getUser()  == user ){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackSelfBuilding",null,locale));
		}else if(s.getType() == 0 || s.getType() == 5){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackTerrain",null,locale));
		}else {
			if (s.getType() == Info.CASTLE) {
				if (!ss.isCastleAlone(new Point(xprime, yprime), 3)) {
					return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.attackCastle", null, locale));
				}
			}
			if(s.getUser().getLocale() != null){
				Locale l = new Locale(s.getUser().getLocale());
				mailService.sendEmail(s.getUser().getEmail(),messageSource.getMessage("email.subject",null,l), messageSource.getMessage("email.body",null,l));
			}else{
				mailService.sendEmail(s.getUser().getEmail(),messageSource.getMessage("email.subject",null,locale), messageSource.getMessage("email.body",null,locale));
			}
			
			sh.attackTask(user,new Point(xprime,yprime),id);
			as.setAvailable(id,false);
		}
		return new ModelAndView("redirect:/");
	}
	private void sendMail(Map<String,Integer> res,User a, User d,int result){
		String header;
		String body;
		if(result == 0)
			header = "Fuiste atacado por " +a.getName() +": Fue un empate\n";
		else if(result == 1)
			header = "Fuiste atacado por "+ a.getName() +": Ganaste\n";
		else
			header = "Fuiste atacado por "+ a.getName() +": Perdiste\n";

		body =  header + "Tus tropas:\n\tGerreros:\n\t\tOriginales: "+ res.get("d0b") + "\n\t\tMuertos: " +
				res.get("d0l") + "\n\t\tSobrevivientes: " + (res.get("d0b") - res.get("d0l")) + "\n\n" +
				"\tArqueros:\n\t\tOriginales: "+ res.get("d1b") + "\n\t\tMuertos: " +
				res.get("d1l") + "\n\t\tSobrevivientes: " + (res.get("d1b") - res.get("d1l")) + "\n\n" +
				"\tCaballeros:\n\t\tOriginales: "+ res.get("d2b") + "\n\t\tMuertos: " +
				res.get("d2l") + "\n\t\tSobrevivientes: " + (res.get("d2b") - res.get("d2l")) + "\n\n" +
				"Atacante:\n\tGerreros:\n\t\tOriginales: "+ res.get("a0b") + "\n\t\tMuertos: " +
				res.get("a0l") + "\n\t\tSobrevivientes: " + (res.get("a0b") - res.get("a0l")) + "\n\n" +
				"\tArqueros:\n\t\tOriginales: "+ res.get("a1b") + "\n\t\tMuertos: " +
				res.get("a1l") + "\n\t\tSobrevivientes: " + (res.get("a1b") - res.get("a1l")) + "\n\n" +
				"\tCaballeros:\nOriginales: "+ res.get("a2b") + "\n\t\tMuertos: " +
				res.get("a2l") + "\n\t\tSobrevivientes: " + (res.get("a2b") - res.get("a2l")) + "\n\n";

		mailService.sendEmail(d.getEmail(),"Fuiste atacado",body);
	}
	
	@RequestMapping(value="/train", method = RequestMethod.POST)
	public ModelAndView train(@RequestParam String type,
			@RequestParam String amount,
			@RequestParam String px,
			@RequestParam String py,
			@ModelAttribute("userId") final User user,
			Locale locale){
		if(user == null){
			return new ModelAndView("redirect:/");
		}
		if(!Validator.isInteger(type) || !Validator.isInteger(amount)
				|| !Validator.validBoardPosition(px) || !Validator.validBoardPosition(py)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidParam",null,locale));
		}
		int a = Integer.valueOf(amount);
		int x = Integer.valueOf(px);
		int y = Integer.valueOf(py);
		Point p = new Point(x,y);
		Sector s =ss.getSector(p);
		int troopType = Integer.valueOf(type);
		int cost=0;
		switch (troopType){
		case Info.WARRIOR:
			cost = (Info.COST_WARRIOR - (s.getLevel() - 1))*a;
			break;
		case Info.ARCHER:
			cost = (Info.COST_ARCHER - (s.getLevel() - 1))*a;
			break;
		case Info.HORSEMAN:
			cost = (Info.COST_HORSEMAN - (s.getLevel() - 1))*a;
			break;
		default:
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidTroop",null,locale));
		}
		boolean resp = false;
		if(als.getAlertByPoint(p) != null){
			return new ModelAndView("redirect:/error");
		}
		if(es.subtractResourceAmount(user,Info.RES_FOOD,cost)){
			resp = true;
			sh.TrainTask(user,p,a,troopType);
		}

		if(resp){
			return new ModelAndView("redirect:/");

		}else{
			return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&e="+ messageSource.getMessage("error.noFood",null,locale));
		}

	}

	@RequestMapping(value="/merge")
	public ModelAndView merge(@RequestParam String f,
			@RequestParam String t,
			@ModelAttribute("userId") final User user,
			Locale locale){
		if(user == null){
			return new ModelAndView("redirect:/");
		}
		if(!Validator.isInteger(f) || !Validator.isInteger(t)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidParam",null,locale));
		}
		int from = Integer.valueOf(f);
		int to = Integer.valueOf(t);
		if(!as.belongs(user ,from) || !as.belongs(user ,to)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
		}
        sh.mergeTask(user,from,to,as.getArmyById(to).getPosition());
        as.setAvailable(from,false);
		return new ModelAndView("redirect:/armies");
	}

	@RequestMapping(value="/armies/{armyId}/split")
	public ModelAndView split(@PathVariable String armyId,
			@ModelAttribute("userId") final User user,
			Locale locale){
		if(user == null){
			return new ModelAndView("redirect:/");
		}
		if(!Validator.isInteger(armyId)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
		}
		if(!as.belongs(user ,Integer.valueOf(armyId))){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
		}
		ModelAndView mav = new ModelAndView("split");
		Army a = as.getArmyById(Integer.parseInt(armyId));
		List<Point> points = new ArrayList<>();
		for(Sector b : ss.getAllBuildings(user)){
			if(!b.getPosition().equals(a.getPosition())) {
				points.add(b.getPosition());
			}
		}
		int unreadMessages = ms.countUnreadMessages(user);

		mav.addObject("unreadMessages", unreadMessages);
		mav.addObject("user",user);
		mav.addObject("army",a);
		mav.addObject("possiblePoints",points);
		return mav;
	}
	@RequestMapping(value="/split",method = RequestMethod.POST)
	public ModelAndView split2(@RequestParam(value= "0",required = false,defaultValue = "0") String t1,
			@RequestParam(value= "1",required = false,defaultValue = "0") String t2,
			@RequestParam(value= "2",required = false,defaultValue = "0") String t3,
			@RequestParam(value= "pos") String pos,
			@RequestParam(value= "armyId") String armyId,
			@ModelAttribute("userId") final User user,
			Locale locale){
		String[] po = pos.split(",");
		if(!Validator.validBoardPosition(po[0]) || !Validator.validBoardPosition(po[1])){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidPosition",null,locale));
		}
		Point p = new Point(Integer.parseInt(po[0]),Integer.parseInt(po[1]));
		if(!Validator.isInteger(t1) || !Validator.isInteger(t2) || !Validator.isInteger(t3)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidTroopAmount",null,locale));
		}
		if(!Validator.isInteger(armyId)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
		}
		int id = Integer.parseInt(armyId);
		if(!as.belongs(user ,id)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
		}
		int warriors = Integer.parseInt(t1);
		int archers = Integer.parseInt(t2);
		int horsemen = Integer.parseInt(t3);
		Map<TroopType,Integer> tr = new HashMap<>();
		tr.put(TroopType.warrior,warriors);
		tr.put(TroopType.archer,archers);
		tr.put(TroopType.horseman,horsemen);
		Army a = as.splitArmy(id,tr);
		sh.splitTask(user,a.getIdArmy(),p);
		return new ModelAndView("redirect:/armies");
	}
	@RequestMapping(value="/move", method = RequestMethod.POST)
	public ModelAndView move(@ModelAttribute("userId") final User user,
							   @RequestParam(value= "x",required = true) String x,
							   @RequestParam(value= "y",required = true) String y,
							   @RequestParam(value= "armyId",required = true) String armyId,
							   Locale locale){

		if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidPosition",null,locale));
		}
		Point p = new Point(Integer.parseInt(x),Integer.parseInt(y));
		if(!Validator.isInteger(armyId)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
		}
		int id = Integer.parseInt(armyId);
		if(!as.belongs(user ,id)){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
		}
		as.setAvailable(id,false);
		sh.moveTask(user,id,p);
		return new ModelAndView("redirect:/map");

	}
	@ModelAttribute("userId")
	public User loggedUser (final HttpSession session){
		if(session.getAttribute("userId") != null)
			return  us.findById((Integer)session.getAttribute("userId"));
		return null;
	}

}