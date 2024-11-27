package ma.zyn.app.service.facade.admin.passenger;

import java.util.List;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;


public interface PassengerAdminService {


    Passenger findByUsername(String username);
    boolean changePassword(String username, String newPassword);

    List<Passenger> findByCarteBancaireId(Long id);
    int deleteByCarteBancaireId(Long id);
    long countByCarteBancaireId(Long id);




	Passenger create(Passenger t);

    Passenger update(Passenger t);

    List<Passenger> update(List<Passenger> ts,boolean createIfNotExist);

    Passenger findById(Long id);

    Passenger findOrSave(Passenger t);

    Passenger findByReferenceEntity(Passenger t);

    Passenger findWithAssociatedLists(Long id);

    List<Passenger> findAllOptimized();

    List<Passenger> findAll();

    List<Passenger> findByCriteria(PassengerCriteria criteria);

    List<Passenger> findPaginatedByCriteria(PassengerCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(PassengerCriteria criteria);

    List<Passenger> delete(List<Passenger> ts);

    boolean deleteById(Long id);

    List<List<Passenger>> getToBeSavedAndToBeDeleted(List<Passenger> oldList, List<Passenger> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
