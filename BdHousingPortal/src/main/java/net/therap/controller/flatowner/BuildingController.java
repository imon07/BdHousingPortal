package net.therap.controller.flatowner;

import net.therap.domain.Building;
import net.therap.domain.FlatOwner;
import net.therap.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ashraf
 * Date: 6/6/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/building")



public class BuildingController {

    @Autowired
    BuildingService buildingService;

    public BuildingService getBuildingService() {
        return buildingService;
    }

    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @RequestMapping(value = "/create.htm",method = RequestMethod.GET)
    String createBuildingGetAction(Map<String, Object> model){
        model.put("building",new Building());
        model.put("title","Building details :");
        return "flatowner/createbuilding";
    }

    @RequestMapping(value = "/create.htm",method = RequestMethod.POST)
    String createBuildingPostAction(@Valid Building building, BindingResult bindingResult,HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "flatowner/createbuilding";
        }
        else {

            building.setFlatOwner((FlatOwner)request.getSession().getAttribute("flatowner"));
            building.setFlatTypeCount(0);
            buildingService.saveBuilding(building);
            return  "redirect:/own/home.htm";

        }

    }

    @RequestMapping(value = "/buildinglist.htm",method = RequestMethod.GET)
    String showBuildingListAction(Map<String, Object> model,HttpServletRequest request){
        List<Building> buildingList = buildingService.getBuildingList((FlatOwner)request.getSession().getAttribute("flatowner"));
        model.put("buildingList",buildingList);
        model.put("title","Building List :");
        return "flatowner/buildinglist";
    }

    @RequestMapping(value = "/buildingdetails.htm",method = RequestMethod.GET)
    String showBuildingDetailsAction(@RequestParam long buildingId, Map<String, Object> model){

        Building building = buildingService.getBuildingById(buildingId);
        model.put("building",building);
        model.put("title","Building Details :");
        return "flatowner/buildingdetails";
    }
}
