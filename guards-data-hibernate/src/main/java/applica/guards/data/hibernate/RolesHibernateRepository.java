package applica.guards.data.hibernate;

import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import applica.guards.domain.data.RolesRepository;
import applica.guards.domain.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 3/3/13
 * Time: 10:53 PM
 */
@Repository
public class RolesHibernateRepository extends HibernateRepository<Role> implements RolesRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("role", false));
    }

    @Override
    public Class<Role> getEntityType() {
        return Role.class;
    }

}
