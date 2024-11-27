package ma.zyn.app.service.facade.admin.vehicule;

import java.util.List;
import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.dao.criteria.core.vehicule.VehiculeCriteria;


public interface VehiculeAdminService {



    List<Vehicule> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);




	Vehicule create(Vehicule t);

    Vehicule update(Vehicule t);

    List<Vehicule> update(List<Vehicule> ts,boolean createIfNotExist);

    Vehicule findById(Long id);

    Vehicule findOrSave(Vehicule t);

    Vehicule findByReferenceEntity(Vehicule t);

    Vehicule findWithAssociatedLists(Long id);

    List<Vehicule> findAllOptimized();

    List<Vehicule> findAll();

    List<Vehicule> findByCriteria(VehiculeCriteria criteria);

    List<Vehicule> findPaginatedByCriteria(VehiculeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(VehiculeCriteria criteria);

    List<Vehicule> delete(List<Vehicule> ts);

    boolean deleteById(Long id);

    List<List<Vehicule>> getToBeSavedAndToBeDeleted(List<Vehicule> oldList, List<Vehicule> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
