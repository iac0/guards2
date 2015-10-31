package applica.guards.data.hibernate;

import applica.framework.data.hibernate.HibernateRepository;
import applica.guards.domain.data.GuardRepository;
import applica.guards.domain.data.PlacesRepository;
import applica.guards.domain.model.Guard;
import applica.guards.domain.model.Place;
import org.springframework.stereotype.Repository;

/**
 * Created by Iaco on 18/10/15.
 */
@Repository
public class PlacesHibernateRepository extends HibernateRepository<Place> implements PlacesRepository {
    @Override
    public Class<Place> getEntityType() {
        return Place.class;
    }
}
