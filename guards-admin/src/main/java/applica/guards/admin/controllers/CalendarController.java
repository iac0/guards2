package applica.guards.admin.controllers;

import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.RestResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.guards.admin.facade.GuardFacade;
import applica.guards.domain.exception.DatabaseException;
import applica.guards.domain.model.Guard;
import applica.guards.domain.model.Guarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Iaco on 18/10/15.
 */
@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {

    @Autowired
    GuardFacade guardFacade;

    @RequestMapping(value = "/fetch",method = RequestMethod.POST)
    public @ResponseBody
    SimpleResponse fetch(Long guard_id, String start, String end){
        // date from js are in other format required multiply with 10000
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RestResponse restResponse = new RestResponse(HttpStatus.OK);
        restResponse.setValue(guardFacade.fetch(guard_id,startDate,endDate));
        return restResponse;
    }
    @RequestMapping(value ="/create",method = RequestMethod.POST)
    public @ResponseBody
    RestResponse create(String startDate, long placeId, long guardId) {
        RestResponse response = new RestResponse(HttpStatus.OK);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH:mm");
        Date start = null;
        try {
            start = format.parse(startDate);
        } catch (ParseException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Data non corretta");
            response.setError(true);
            return response;
        } catch (NullPointerException e){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Data nulla");
            response.setError(true);
            return response;
        }
        try {
            Guarding created = guardFacade.create(guardId,placeId,start);
            response.setValue(created);
            response.setError(false);
            response.setMessage("ok");
        } catch (DatabaseException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setError(true);
            response.setMessage(e.getMessage());

        }
        return response;

    }

    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public @ResponseBody
    RestResponse update(String startDate, long eventId) {
        RestResponse response = new RestResponse(HttpStatus.OK);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH:mm");
        Date start = null;
        try {
            start = format.parse(startDate);
        } catch (ParseException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Data non corretta");
            response.setError(true);
            return response;
        } catch (NullPointerException e){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Data nulla");
            response.setError(true);
            return response;
        }
        try {
            Guarding updated = guardFacade.update(eventId, start);
            response.setValue(updated);
            response.setError(false);
            response.setMessage("ok");
        } catch (DatabaseException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setError(true);
            response.setMessage(e.getMessage());

        }
        return response;

    }

    @RequestMapping(value ="/remove",method = RequestMethod.POST)
    @ResponseBody
    SimpleResponse deleteEvent(long eventId){
        try {
            guardFacade.removeGuard(eventId);
        }catch (Exception e){
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new RestResponse(HttpStatus.OK);
    }

    @RequestMapping(value ="/chooseWeek",method = RequestMethod.GET)
    String choose(){
        return "/guarding/choose";
    }

    @RequestMapping(value ="/print",method = RequestMethod.GET)
    String print(String start, String end, Model model){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("guardings",guardFacade.fetch(startDate,endDate));
        return "/guarding/print";
    }
}
