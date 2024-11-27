package ma.zyn.app.utils.security.service.impl;


import ma.zyn.app.utils.security.bean.Role;
import ma.zyn.app.utils.security.dao.criteria.core.RoleCriteria;
import ma.zyn.app.utils.security.dao.facade.core.RoleDao;
import ma.zyn.app.utils.security.dao.specification.core.RoleSpecification;
import ma.zyn.app.utils.security.service.facade.RoleService;
import ma.zyn.app.utils.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, RoleCriteria, RoleDao> implements RoleService {


    @Override
    public Role findByAuthority(String authority) {
        return dao.findByAuthority(authority);
    }

    @Override
    public int deleteByAuthority(String authority) {
        return deleteByAuthority(authority);
    }



    public Role findByReferenceEntity(Role t){
        return  dao.findByAuthority(t.getAuthority());
    }


    public List<Role> findAllOptimized() {
        return dao.findAllOptimized();
    }





    public void configure() {
        super.configure(Role.class, RoleSpecification.class);
    }



    public RoleServiceImpl(RoleDao dao) {
        super(dao);
    }

}
