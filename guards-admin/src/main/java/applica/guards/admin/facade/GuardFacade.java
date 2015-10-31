package applica.guards.admin.facade;

import applica.framework.LoadRequest;
import applica.guards.admin.viewmodel.UICalendarEvent;
import applica.guards.domain.data.GuardRepository;
import applica.guards.domain.data.GuardingsRepository;
import applica.guards.domain.data.PlacesRepository;
import applica.guards.domain.exception.DatabaseException;
import applica.guards.domain.model.Guard;
import applica.guards.domain.model.Guarding;
import applica.guards.domain.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Iaco on 18/10/15.
 */
@Component
public class GuardFacade {

    @Autowired
    GuardRepository guardRepository;
    @Autowired
    GuardingsRepository guardingsRepository;
    @Autowired
    PlacesRepository placesRepository;

    public List<Guard> guardList(){
        return guardRepository.find(LoadRequest.build()).getRows();
    }

    public List<UICalendarEvent> fetch(Long guardId, Date start, Date end){
        LoadRequest request = LoadRequest.build().gte("watching",start).lte("watching",end).eq("guard.id",guardId)
                .sort("guard.id",false);

        List<UICalendarEvent> results = new ArrayList<>();
        for (Guarding guarding : guardingsRepository.find(request).getRows()){
            UICalendarEvent event = new UICalendarEvent(guarding);
            results.add(event);
        }
        return results;
    }

    public List<Guarding> fetch(Date start, Date end){
        LoadRequest request = LoadRequest.build()
                .gte("watching",start)
                .lte("watching",end)
                .sort("guard.id",false);

        return guardingsRepository.find(request).getRows();
    }
    public Guarding create(long guardId, long placeId, Date start) throws DatabaseException {
        Guarding guarding = new Guarding();
        Optional<Guard> guardOptional = guardRepository.find(LoadRequest.build().eq("id",guardId)).findFirst();
        if ( !guardOptional.isPresent() ){
            throw new DatabaseException("Errore nel caricamento della guardia con id:"+guardId);
        }
        Optional<Place> placeOptional = placesRepository.find(LoadRequest.build().eq("id", placeId)).findFirst();
        if ( ! placeOptional.isPresent() ){
            throw new DatabaseException("Errore nel caricamento della posto di guardia con id:"+guardId);
        }
        Place place = placeOptional.get();
        Guard guard = guardOptional.get();
        guarding.setGuard(guard);
        guarding.setPlace(place);
        Date placeStartDate = place.getDateOfGuarding();
        Calendar calStart = Calendar.getInstance(); // locale-specific
        Calendar calPlace = Calendar.getInstance();
        calPlace.setTime(placeStartDate);
        calStart.setTime(start);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        calStart.set(Calendar.HOUR_OF_DAY,calPlace.get(Calendar.HOUR_OF_DAY));
        calStart.set(Calendar.MINUTE,calPlace.get(Calendar.MINUTE));

        guarding.setWatching(calStart.getTime());

        if ( place.getFinishOfGuarding() != null ) {
            long differnce = place.getFinishOfGuarding().getTime() - place.getDateOfGuarding().getTime();
            Date end = new Date();
            end.setTime(guarding.getWatching().getTime() + differnce);

            guarding.setWatchingEnd(end);
        } else {
            Date end = new Date();
            end.setTime( guarding.getWatching().getTime() + 30*60*1000);
            guarding.setWatchingEnd(end);
        }
        try {
            guardingsRepository.save(guarding);

        }catch (Exception e){
            throw new DatabaseException("Errore nel salvataggio del turno");
        }
        return  guarding;
    }

    public Guarding update(long eventId, Date start) throws DatabaseException {
        Guarding guarding = guardingsRepository.find(LoadRequest.build().eq("id",eventId)).findFirst().get();
        Place place = guarding.getPlace();
        Date placeStartDate = place.getDateOfGuarding();
        Calendar calStart = Calendar.getInstance(); // locale-specific
        Calendar calPlace = Calendar.getInstance();
        calPlace.setTime(placeStartDate);
        calStart.setTime(start);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        calStart.set(Calendar.HOUR_OF_DAY,calPlace.get(Calendar.HOUR_OF_DAY));
        calStart.set(Calendar.MINUTE,calPlace.get(Calendar.MINUTE));

        guarding.setWatching(calStart.getTime());

        if ( place.getFinishOfGuarding() != null ) {
            long differnce = place.getFinishOfGuarding().getTime() - place.getDateOfGuarding().getTime();
            Date end = new Date();
            end.setTime(guarding.getWatching().getTime() + differnce);

            guarding.setWatchingEnd(end);
        } else {
            Date end = new Date();
            end.setTime( guarding.getWatching().getTime() + 30*60*1000);
            guarding.setWatchingEnd(end);
        }
        try {
            guardingsRepository.save(guarding);

        }catch (Exception e){
            throw new DatabaseException("Errore nel salvataggio del turno");
        }
        return  guarding;
    }
    public void removeGuard(long id){
        guardingsRepository.delete(id);
    }
}
