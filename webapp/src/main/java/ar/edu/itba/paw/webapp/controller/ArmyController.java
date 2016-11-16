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
	private UserService us;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PAWMailService mailService;

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
		mav.addObject("armies",armies);
		mav.addObject("size",armies.size());
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
		List<Troop> troops = army.getTroops();
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
		if(s.getUser()  == user ){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackSelfBuilding",null,locale));
		}else if(s.getType() == 0 || s.getType() == 5){
			return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackTerrain",null,locale));
		}else {
			if(s.getType() == Info.CASTLE){
				if(!ss.isCastleAlone(new Point(xprime,yprime),3)){
					return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackCastle",null,locale));
				}
			}
			Army d = as.getStrongest(s.getUser());
			Army a = as.getArmyById(id);
			Map<String,Integer> values = new HashMap<>();
			int res = 0;
			values.put("a0b",0);
			values.put("a1b",0);
			values.put("a2b",0);
			values.put("d0b",0);
			values.put("d1b",0); // QUE WASADA POR DIOS
			values.put("d2b",0);
			values.put("a0l",0);
			values.put("a1l",0);
			values.put("a2l",0);
			values.put("d0l",0);
			values.put("d1l",0);
			values.put("d2l",0);
			if(d != null){
				int defenderP = (int) ts.getValue(d.getIdArmy());
				int attackerP = (int) ts.getValue(id);
				int loserP;
				Army adef;
				Army awin;
				String prefixW;
				String prefixD;
				if(defenderP > attackerP){
					mav.addObject("result",messageSource.getMessage("defenderWin",null,locale));
					adef = a;
					awin = d;
					loserP = attackerP;
					prefixD ="a";
					prefixW ="d";
					res = 1;
				}else if(attackerP > defenderP){
					mav.addObject("result",messageSource.getMessage("attackerWin",null,locale));
					awin = a;
					adef = d;
					loserP = defenderP;
					prefixD ="d";
					prefixW ="a";
					ss.deleteBuilding(new Point(xprime,yprime));
					res = 2;
				}else{
					mav.addObject("result",messageSource.getMessage("draw",null,locale));
					for(Troop t : a.getTroops()){
						values.put("a"+t.getType()+"b",t.getQuantity());
						values.put("a"+t.getType()+"l",t.getQuantity());
					}
					for(Troop t : d.getTroops()){
						values.put("d"+t.getType()+"b",t.getQuantity());
						values.put("d"+t.getType()+"l",t.getQuantity());
					}
					as.deleteArmy(id);
					as.deleteArmy(d.getIdArmy());
					mav.addAllObjects(values);
					sendMail(values,user,s.getUser(),0);
					return mav;
				}
				List<Troop> defeated = adef.getTroops();
				as.deleteArmy(adef.getIdArmy());
				List<Troop> winner = awin.getTroops();
				for(Troop t : winner){
					if(t.getQuantity()*(t.getType()+1) > loserP){
						values.put(prefixW+t.getType()+"b",t.getQuantity());
						ts.subtractTroop(awin.getIdArmy(),t.getType(),loserP/(t.getType()+1));
						values.put(prefixW+t.getType()+"l",loserP/(t.getType()+1));
						break;
					}else{
						values.put(prefixW+t.getType()+"b",t.getQuantity());
						values.put(prefixW+t.getType()+"l",t.getQuantity());
						ts.deleteTroop(awin.getIdArmy(),t.getType());
						loserP-= (t.getType()+1)*t.getQuantity();

					}
				}
				for(Troop t: defeated){
					values.put(prefixD+t.getType()+"b",t.getQuantity());
					values.put(prefixD+t.getType()+"l",t.getQuantity());
				}
				mav.addAllObjects(values);
				sendMail(values,user,s.getUser(),res);
				return mav;
			}
			mav.addObject("result",messageSource.getMessage("noDefenderArmy",null,locale));
			for(Troop t : ts.getTroopById(id)){
				values.put("a"+t.getType()+"b",t.getQuantity());
				values.put("a"+t.getType()+"l",0);
			}
			ss.deleteBuilding(new Point(xprime,yprime));
			mav.addAllObjects(values);
			sendMail(values,user,s.getUser(),2);
		}
		return mav;
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
		Sector s =ss.getSector(new Point(x,y));
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
		boolean resp = as.trainTroops(user,Info.RES_FOOD,cost,new Point(x,y),a,Integer.valueOf(type));
		if(resp){
			return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&s="+ messageSource.getMessage("troopSuccess",null,locale));

		}else{
			return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&e="+ messageSource.getMessage("error.noFood",null,locale));
		}

	}

	@RequestMapping(value="/merge")
	public ModelAndView train(@RequestParam String f,
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

		List<Troop> troops = ts.getTroopById(from);
		for(Troop troop : troops){
			ts.addTroop(to,troop.getType(),troop.getQuantity());
		}
		as.deleteArmy(from);
		return new ModelAndView("redirect:/armies");
	}

	@RequestMapping(value="/armies/{armyId}/split")
	public ModelAndView train(@PathVariable String armyId,
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
		mav.addObject("user",user);
		mav.addObject("army",a);
		mav.addObject("possiblePoints",points);
		return mav;
	}
	@RequestMapping(value="/split",method = RequestMethod.POST)
	public ModelAndView train(@RequestParam(value= "0",required = false,defaultValue = "0") String t1,
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

		Army newArmy = as.getOrCreateArmy(p,user);
		if(warriors != 0){
			ts.addTroop(newArmy.getIdArmy(),Info.WARRIOR,warriors);
			ts.subtractTroop(id,Info.WARRIOR,warriors);
		}
		if(archers != 0){
			ts.addTroop(newArmy.getIdArmy(),Info.ARCHER,archers);
			ts.subtractTroop(id,Info.ARCHER,archers);
		}
		if(horsemen != 0){
			ts.addTroop(newArmy.getIdArmy(),Info.HORSEMAN,horsemen);
			ts.subtractTroop(id,Info.HORSEMAN,horsemen);
		}
		return new ModelAndView("redirect:/armies");
	}

	@ModelAttribute("userId")
	public User loggedUser (final HttpSession session){
		if(session.getAttribute("userId") != null)
			return  us.findById((Integer)session.getAttribute("userId"));
		return null;
	}

}