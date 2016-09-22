package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.paw.webapp.dataClasses.BuildingInformationMap;
import ar.edu.itba.paw.webapp.dataClasses.InformationBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import static java.lang.System.out;

/**
 * Created by root on 9/18/16.
 */
@Controller
public class BuildingController {

    @Autowired
    private SectorService ss;

    @RequestMapping(value="/building", method = RequestMethod.GET)
    public ModelAndView terrainParams(@RequestParam(value="x",required = false) String x, @RequestParam(value="y", required = false) String y ) {

        String regex = "^\\d+";

        if(!x.matches(regex) || !y.matches(regex)) {
            return new ModelAndView("redirect:/error");
        }else{
            final ModelAndView mav = new ModelAndView("building");

            Sector sector = ss.getSector(new Point(Integer.parseInt(x),Integer.parseInt(y)));
            InformationBuilding ib  = BuildingInformationMap.getInstance().getBuildingInformation(sector.getType());

            mav.addObject("building",ib);

            return mav;

        }

    }

}
