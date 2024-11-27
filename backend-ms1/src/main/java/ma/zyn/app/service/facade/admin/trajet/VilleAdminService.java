package ma.zyn.app.service.facade.admin.trajet;

import java.util.List;
import ma.zyn.app.bean.core.trajet.Ville;
import ma.zyn.app.dao.criteria.core.trajet.VilleCriteria;


public interface VilleAdminService {







	Ville create(Ville t);

    Ville update(Ville t);

    List<Ville> update(List<Ville> ts,boolean createIfNotExist);

    Ville findById(Long id);

    Ville findOrSave(Ville t);

    Ville findByReferenceEntity(Ville t);

    Ville findWithAssociatedLists(Long id);

    List<Ville> findAllOptimized();

    List<Ville> findAll();

    List<Ville> findByCriteria(VilleCriteria criteria);

    List<Ville> findPaginatedByCriteria(VilleCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(VilleCriteria criteria);

    List<Ville> delete(List<Ville> ts);

    boolean deleteById(Long id);

    List<List<Ville>> getToBeSavedAndToBeDeleted(List<Ville> oldList, List<Ville> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
