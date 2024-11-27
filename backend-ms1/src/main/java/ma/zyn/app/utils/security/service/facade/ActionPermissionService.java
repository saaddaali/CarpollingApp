package ma.zyn.app.utils.security.service.facade;

import ma.zyn.app.utils.security.bean.ActionPermission;
import ma.zyn.app.utils.security.dao.criteria.core.ActionPermissionCriteria;
import ma.zyn.app.utils.service.IService;
import java.util.List;


public interface ActionPermissionService extends  IService<ActionPermission,ActionPermissionCriteria>  {

    List<ActionPermission> findAllOptimized();

}
