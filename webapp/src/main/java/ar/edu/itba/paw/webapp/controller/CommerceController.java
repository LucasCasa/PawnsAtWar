package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.dataClasses.Validator;

import javax.servlet.http.HttpSession;

import java.util.List;


@Controller
public class CommerceController {
	
	@Autowired
	private EmpireService es;
	
	@Autowired
	private CommerceService cs;

	@Autowired
	private UserService us;

	@RequestMapping(value="/commerce")
    public ModelAndView commerce(@RequestParam(value="insuficientAmount", required = false)boolean insuficientAmount
    		,@ModelAttribute("user") final User user){

		if(user == null)
			return new ModelAndView("redirect:/login");

		final ModelAndView mav = new ModelAndView("commerce");

        List<TradeOffer> myTrades = cs.getAllOffers(user);
		List<TradeOffer> tradeList = cs.showOffers(user);
        mav.addObject("myTrades",myTrades);
		mav.addObject("myTradesSize",myTrades.size());
        mav.addObject("tradeList",tradeList);
		mav.addObject("tradeListSize",tradeList.size());
        mav.addObject("insuficientAmount",insuficientAmount);
        mav.addObject("ratesList",es.getRates(user));
		mav.addObject("resList",es.getResources(user));
        
        return mav;
	}
	
	@RequestMapping(value="/commerce/acceptTrade")
	public ModelAndView acceptTrade(@RequestParam final int id, @ModelAttribute("user") final User user){
		TradeOffer to = cs.getOffer(id);
		if(to == null || to.getOwner().getId() == user.getId())
			return new ModelAndView("redirect:/error");
		
		if(es.getResource(user,to.getReceiveType()).getQuantity() < to.getReceiveAmount()){
			ModelAndView mav = new ModelAndView("redirect:/commerce");
			mav.addObject("insuficientAmount",true);
			return mav;
		}
		cs.acceptOffer(to, user);
		return new ModelAndView("redirect:/commerce");
	}
	
	@RequestMapping(value="/commerce/delete")
	public ModelAndView deleteTrade(@RequestParam final int id, @ModelAttribute("user") final User user){
		TradeOffer to = cs.getOffer(id);
		if(to == null || to.getOwner().getId() != user.getId())
			return new ModelAndView("redirect:/error");

		cs.removeOffer(to);
		return new ModelAndView("redirect:/commerce");
	}

	@RequestMapping(value="/commerce/create")
	public ModelAndView createOffer(@RequestParam(value="insuficientAmount", required = false)boolean insuficientAmount,
			@ModelAttribute("user") final User user){
		ModelAndView mav = new ModelAndView("createOffer");
		mav.addObject("insuficientAmount",insuficientAmount);
		mav.addObject("ratesList",es.getRates(user));
		mav.addObject("resList",es.getResources(user));
		
		return mav;
	}
	
	@RequestMapping(value="/commerce/create/submit", method = RequestMethod.POST)
    public ModelAndView sumbitCreate(@RequestParam(required = false) String giveType, @RequestParam(required = false) String getType,@RequestParam(required = false) String giveQty,@RequestParam(required = false) String getQty,@ModelAttribute("user") final User user ){
		if(getQty==null || giveQty==null || giveType==null || getType==null)
			return new ModelAndView("redirect:/commerce/create");
		if(!(Validator.isInteger(giveQty)&&Validator.isInteger(getQty)&&Long.parseLong(giveQty)>0&&Long.parseLong(getQty)>0)){
			return new ModelAndView("redirect:/error");
		}
		if(!(Validator.isInteger(giveType)&&Validator.isInteger(getType))){
			return new ModelAndView("redirect:/error");
		}
		int giveTyp = Integer.parseInt(giveType);
		int getTyp = Integer.parseInt(getType);
		
		if(giveTyp == getTyp)
			return new ModelAndView("redirect:/error");
		
		int giveAmount = Integer.parseInt(giveQty);
		int receiveAmount = Integer.parseInt(getQty);
		boolean res = cs.createOffer(user,giveTyp,giveAmount,getTyp,receiveAmount);
		if(!res){
			return createOffer(true,user);
		}
		return new ModelAndView("redirect:/commerce");
	}

	@ModelAttribute("user")
	public User loggedUser (final HttpSession session){
		if(session.getAttribute("userId") != null)
			return  us.findById((Integer)session.getAttribute("userId"));
		return null;
	}

}
