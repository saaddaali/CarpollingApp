package ma.zyn.app.utils.security.service.facade;

import ma.zyn.app.utils.security.bean.Role;
import ma.zyn.app.utils.security.dao.criteria.core.RoleCriteria;
import ma.zyn.app.utils.service.IService;



public interface RoleService extends  IService<Role,RoleCriteria>  {
    Role findByAuthority(String authority);
    int deleteByAuthority(String authority);


    



}
