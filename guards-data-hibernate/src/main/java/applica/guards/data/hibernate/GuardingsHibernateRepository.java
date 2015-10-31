package applica.guards.data.hibernate;

import applica.framework.data.hibernate.HibernateRepository;
import applica.guards.domain.data.GuardingsRepository;
import applica.guards.domain.data.PlacesRepository;
import applica.guards.domain.model.Guarding;
import applica.guards.domain.model.Place;
import org.springframework.stereotype.Repository;

/**
 * Created by Iaco on 18/10/15.
 */
@Repository
public class GuardingsHibernateRepository extends HibernateRepository<Guarding> implements GuardingsRepository {
    @Override
    public Class<Guarding> getEntityType() {
        return Guarding.class;
    }
}
