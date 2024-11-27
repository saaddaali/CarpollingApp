package ma.zyn.app.service.facade.passenger.trajet;

import java.util.List;
import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.dao.criteria.core.trajet.TrajetCriteria;


public interface TrajetPassengerService {



    List<Trajet> findByVilleDepartId(Long id);
    int deleteByVilleDepartId(Long id);
    long countByVilleDepartCode(String code);
    List<Trajet> findByVilleDestinationId(Long id);
    int deleteByVilleDestinationId(Long id);
    long countByVilleDestinationCode(String code);
    List<Trajet> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);
    List<Trajet> findByLocalisationSourceId(Long id);
    int deleteByLocalisationSourceId(Long id);
    long countByLocalisationSourceCode(String code);
    List<Trajet> findByLocalisationDestinationId(Long id);
    int deleteByLocalisationDestinationId(Long id);
    long countByLocalisationDestinationCode(String code);




	Trajet create(Trajet t);

    Trajet update(Trajet t);

    List<Trajet> update(List<Trajet> ts,boolean createIfNotExist);

    Trajet findById(Long id);

    Trajet findOrSave(Trajet t);

    Trajet findByReferenceEntity(Trajet t);

    Trajet findWithAssociatedLists(Long id);

    List<Trajet> findAllOptimized();

    List<Trajet> findAll();

    List<Trajet> findByCriteria(TrajetCriteria criteria);

    List<Trajet> findPaginatedByCriteria(TrajetCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(TrajetCriteria criteria);

    List<Trajet> delete(List<Trajet> ts);

    boolean deleteById(Long id);

    List<List<Trajet>> getToBeSavedAndToBeDeleted(List<Trajet> oldList, List<Trajet> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
