package applica.guards.admin.controllers;

import applica.guards.admin.facade.GuardFacade;
import applica.guards.admin.facade.PlaceFacade;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 2/22/13
 * Time: 3:18 PM
 */
@Controller
public class HomeController {

    @Autowired
    PlaceFacade placeFacade;
    @Autowired
    GuardFacade guardFacade;

    @RequestMapping("/")

    public String index(Model model,HttpServletRequest request, HttpServletResponse response) {
         model.addAttribute("places",placeFacade.placeList());
         model.addAttribute("guards",guardFacade.guardList());
        return "index";
    }

}
