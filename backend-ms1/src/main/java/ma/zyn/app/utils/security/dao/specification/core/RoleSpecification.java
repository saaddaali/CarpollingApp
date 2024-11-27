package ma.zyn.app.utils.security.dao.specification.core;

import ma.zyn.app.utils.security.bean.Role;
import ma.zyn.app.utils.security.dao.criteria.core.RoleCriteria;
import ma.zyn.app.utils.specification.AbstractSpecification;


public class RoleSpecification extends  AbstractSpecification<RoleCriteria, Role>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("authority", criteria.getAuthority(),criteria.getAuthorityLike());
    }

    public RoleSpecification(RoleCriteria criteria) {
        super(criteria);
    }

    public RoleSpecification(RoleCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
