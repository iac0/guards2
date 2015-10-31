package applica.guards.data.hibernate;

import applica.framework.data.hibernate.HibernateRepository;
import applica.guards.domain.data.GuardRepository;
import applica.guards.domain.model.Guard;
import org.springframework.stereotype.Repository;

/**
 * Created by Iaco on 18/10/15.
 */
@Repository
public class GuardsHibernateRepository extends HibernateRepository<Guard> implements GuardRepository {
    @Override
    public Class<Guard> getEntityType() {
        return Guard.class;
    }
}
