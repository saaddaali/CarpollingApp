package ma.zyn.app.utils.security.service.facade;

import ma.zyn.app.utils.security.bean.ModelPermission;
import ma.zyn.app.utils.security.dao.criteria.core.ModelPermissionCriteria;
import ma.zyn.app.utils.service.IService;
import java.util.List;



public interface ModelPermissionService extends  IService<ModelPermission,ModelPermissionCriteria>  {
    List<ModelPermission> findAllOptimized();

}
