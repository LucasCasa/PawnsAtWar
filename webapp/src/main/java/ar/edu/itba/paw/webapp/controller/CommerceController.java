package ar.edu.itba.paw.webapp.controller;

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

@Controller
public class CommerceController {
	
	@Autowired
	private EmpireService es;
	
	@Autowired
	private CommerceService cs;
	
	@RequestMapping(value="/commerce")
    public ModelAndView commerce(@RequestParam(value="insuficientAmount", required = false)boolean insuficientAmount
    		,@ModelAttribute("user") final User user){
        final ModelAndView mav = new ModelAndView("commerce");
        
        mav.addObject("myTrades",cs.getAllOffers(user.getId()));
        mav.addObject("tradeList",cs.showOffers(user.getId()));
        mav.addObject("insuficientAmount",insuficientAmount);
        mav.addObject("ratesList",es.getRates(user.getId()));
        mav.addObject("resList",es.getResources(user.getId()));
        
        return mav;
	}
	
	@RequestMapping(value="/commerce/acceptTrade")
	public ModelAndView acceptTrade(@RequestParam final int id, @ModelAttribute final User user){
		TradeOffer to = cs.getOffer(id);
		if(to == null || to.getOwner().getId() == user.getId())
			return new ModelAndView("redirect:/error");
		
		if(es.getResource(user.getId(),to.getReceives().getType()).getQuantity() < to.getReceives().getQuantity()){
			ModelAndView mav = new ModelAndView("redirect:/commerce");
			mav.addObject("insuficientAmount",true);
			return mav;
		}
		cs.acceptOffer(to, user.getId());
		return new ModelAndView("redirect:/commerce");
	}
	
	@RequestMapping(value="/commerce/delete")
	public ModelAndView deleteTrade(@RequestParam final int id, @ModelAttribute final User user){
		TradeOffer to = cs.getOffer(id);
		if(to == null || to.getOwner().getId() != user.getId())
			return new ModelAndView("redirect:/error");

		cs.removeOffer(to);
		return new ModelAndView("redirect:/commerce");
	}
	
	@RequestMapping(value="/commerce/create")
	public ModelAndView createOffer(@RequestParam(value="insuficientAmount", required = false)boolean insuficientAmount,
			@ModelAttribute final User user){
		ModelAndView mav = new ModelAndView("createOffer");
		
		mav.addObject("ratesList",es.getRates(user.getId()));
		mav.addObject("resList",es.getResources(user.getId()));
		
		return mav;
	}
	
	@RequestMapping(value="/commerce/create/submit", method = RequestMethod.POST)
    public ModelAndView sumbitCreate(@RequestParam String giveType, @RequestParam String getType,@RequestParam String giveQty,@RequestParam String getQty,@ModelAttribute("user") final User user ){
		if(!(Validator.isInteger(giveQty)&&Validator.isInteger(getQty)&&Integer.parseInt(giveQty)>0&&Integer.parseInt(getQty)>0)){
			return new ModelAndView("redirect:/error");
		}
		if(!(Validator.isInteger(giveType)&&Validator.isInteger(getType))){
			return new ModelAndView("redirect:/error");
		}
		System.out.println("giveT: " + giveType);
		System.out.println("getT: " + getType);
		int giveTyp = Integer.parseInt(giveType);
		int getTyp = Integer.parseInt(getType);
		int giveAmount = Integer.parseInt(giveQty);
		int receiveAmount = Integer.parseInt(getQty);
		boolean res = cs.createOffer(user.getId(),giveTyp,giveAmount,getTyp,receiveAmount);
		if(!res){
			ModelAndView mav = new ModelAndView("redirect:/commerce/create");
			mav.addObject("insuficientAmount",true);
			return mav;
		}
		return new ModelAndView("redirect:/commerce");
	}
	
    @ModelAttribute("user")
    public User setRandomUser() {
        User bean = new User(69,"lucas","42069","l@l.com");
        return bean;
    }

}
