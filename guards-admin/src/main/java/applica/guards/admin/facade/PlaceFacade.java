package applica.guards.admin.facade;

import applica.framework.LoadRequest;
import applica.guards.domain.data.PlacesRepository;
import applica.guards.domain.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Iaco on 18/10/15.
 */
@Component
public class PlaceFacade {
    @Autowired
    PlacesRepository placesRepository;

    public List<Place> placeList(){

         return placesRepository.find(LoadRequest.build()).getRows();
    }

}
