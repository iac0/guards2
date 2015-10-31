package applica.guards.data.hibernate;

import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import applica.guards.domain.data.UsersRepository;
import applica.guards.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Applica (www.applicadoit.com)
 * User: bimbobruno
 * Date: 28/10/13
 * Time: 17:22
 */
@Repository
public class UsersHibernateRepository extends HibernateRepository<User> implements UsersRepository {

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("mail", false));
    }

}
