package ma.zyn.app.service.facade.driver.reservation;

import java.util.List;
import ma.zyn.app.bean.core.reservation.Reservation;
import ma.zyn.app.dao.criteria.core.reservation.ReservationCriteria;


public interface ReservationDriverService {



    List<Reservation> findByTrajetId(Long id);
    int deleteByTrajetId(Long id);
    long countByTrajetId(Long id);
    List<Reservation> findByPassengerId(Long id);
    int deleteByPassengerId(Long id);
    long countByPassengerEmail(String email);
    List<Reservation> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);
    List<Reservation> findByCarteBancaireId(Long id);
    int deleteByCarteBancaireId(Long id);
    long countByCarteBancaireId(Long id);
    List<Reservation> findByConversationId(Long id);
    int deleteByConversationId(Long id);
    long countByConversationCode(String code);




	Reservation create(Reservation t);

    Reservation update(Reservation t);

    List<Reservation> update(List<Reservation> ts,boolean createIfNotExist);

    Reservation findById(Long id);

    Reservation findOrSave(Reservation t);

    Reservation findByReferenceEntity(Reservation t);

    Reservation findWithAssociatedLists(Long id);

    List<Reservation> findAllOptimized();

    List<Reservation> findAll();

    List<Reservation> findByCriteria(ReservationCriteria criteria);

    List<Reservation> findPaginatedByCriteria(ReservationCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ReservationCriteria criteria);

    List<Reservation> delete(List<Reservation> ts);

    boolean deleteById(Long id);

    List<List<Reservation>> getToBeSavedAndToBeDeleted(List<Reservation> oldList, List<Reservation> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
