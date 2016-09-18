package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.paw.webapp.dataClasses.BuildingInformationMap;
import ar.edu.itba.paw.webapp.dataClasses.InformationBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by root on 9/18/16.
 */
@Controller
public class BuildingController {

    @Autowired
    private SectorService ss;

    @RequestMapping(value="/building", method = RequestMethod.GET)
    public ModelAndView terrainParams(@RequestParam(value="x",required = false) final int x, @RequestParam(value="y", required = false) final int y ) {

        final ModelAndView mav = new ModelAndView("building");

        Sector sector = ss.getSector(new Point(x,y));
        InformationBuilding ib  = BuildingInformationMap.getInstance().getBuildingInformation(sector.getType());

        mav.addObject("building",ib);

        return mav;

    }
}
