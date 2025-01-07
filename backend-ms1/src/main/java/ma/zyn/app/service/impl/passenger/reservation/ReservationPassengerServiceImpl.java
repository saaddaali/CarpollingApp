package ma.zyn.app.service.impl.passenger.reservation;


import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.utils.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.reservation.Reservation;
import ma.zyn.app.dao.criteria.core.reservation.ReservationCriteria;
import ma.zyn.app.dao.facade.core.reservation.ReservationDao;
import ma.zyn.app.dao.specification.core.reservation.ReservationSpecification;
import ma.zyn.app.service.facade.passenger.reservation.ReservationPassengerService;

import static ma.zyn.app.utils.util.ListUtil.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.utils.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.zyn.app.service.facade.passenger.driver.DriverPassengerService ;
import ma.zyn.app.service.facade.passenger.passenger.PassengerPassengerService ;
import ma.zyn.app.service.facade.passenger.paiement.CarteBancairePassengerService ;
import ma.zyn.app.service.facade.passenger.message.ConversationPassengerService ;
import ma.zyn.app.service.facade.passenger.trajet.TrajetPassengerService ;

@Service
public class ReservationPassengerServiceImpl implements ReservationPassengerService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Reservation update(Reservation t) {
        Reservation loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Reservation.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Reservation findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Reservation findOrSave(Reservation t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Reservation result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Reservation> findAll() {
        return dao.findAll();
    }

    public List<Reservation> findByCriteria(ReservationCriteria criteria) {
        List<Reservation> content = null;
        if (criteria != null) {
            ReservationSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ReservationSpecification constructSpecification(ReservationCriteria criteria) {
        ReservationSpecification mySpecification =  (ReservationSpecification) RefelexivityUtil.constructObjectUsingOneParam(ReservationSpecification.class, criteria);
        return mySpecification;
    }

    public List<Reservation> findPaginatedByCriteria(ReservationCriteria criteria, int page, int pageSize, String order, String sortField) {
        ReservationSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ReservationCriteria criteria) {
        ReservationSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Reservation> findByTrajetId(Long id){
        return dao.findByTrajetId(id);
    }
    public int deleteByTrajetId(Long id){
        return dao.deleteByTrajetId(id);
    }
    public long countByTrajetId(Long id){
        return dao.countByTrajetId(id);
    }
    public List<Reservation> findByPassengerId(Long id){
        return dao.findByPassengerId(id);
    }
    public int deleteByPassengerId(Long id){
        return dao.deleteByPassengerId(id);
    }
    public long countByPassengerEmail(String email){
        return dao.countByPassengerEmail(email);
    }
    public List<Reservation> findByDriverId(Long id){
        return dao.findByDriverId(id);
    }
    public int deleteByDriverId(Long id){
        return dao.deleteByDriverId(id);
    }
    public long countByDriverEmail(String email){
        return dao.countByDriverEmail(email);
    }
    public List<Reservation> findByCarteBancaireId(Long id){
        return dao.findByCarteBancaireId(id);
    }
    public int deleteByCarteBancaireId(Long id){
        return dao.deleteByCarteBancaireId(id);
    }
    public long countByCarteBancaireId(Long id){
        return dao.countByCarteBancaireId(id);
    }
    public List<Reservation> findByConversationId(Long id){
        return dao.findByConversationId(id);
    }
    public int deleteByConversationId(Long id){
        return dao.deleteByConversationId(id);
    }
    public long countByConversationCode(String code){
        return dao.countByConversationCode(code);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Reservation> delete(List<Reservation> list) {
		List<Reservation> result = new ArrayList();
        if (list != null) {
            for (Reservation t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Reservation create(Reservation t) {
        Reservation loaded = findByReferenceEntity(t);
        Reservation saved;
        Trajet trajet = t.getTrajet();
        trajet.setPlacesDisponibles(trajet.getPlacesDisponibles() - 1);
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Reservation findWithAssociatedLists(Long id){
        Reservation result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Reservation> update(List<Reservation> ts, boolean createIfNotExist) {
        List<Reservation> result = new ArrayList<>();
        if (ts != null) {
            for (Reservation t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Reservation loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Reservation t, Reservation loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Reservation findByReferenceEntity(Reservation t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Reservation t){
        if( t != null) {
            t.setTrajet(trajetService.findOrSave(t.getTrajet()));
            t.setPassenger(passengerService.findOrSave(t.getPassenger()));
            t.setDriver(driverService.findOrSave(t.getDriver()));
            t.setCarteBancaire(carteBancaireService.findOrSave(t.getCarteBancaire()));
            t.setConversation(conversationService.findOrSave(t.getConversation()));
        }
    }



    public List<Reservation> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<Reservation>> getToBeSavedAndToBeDeleted(List<Reservation> oldList, List<Reservation> newList) {
        List<List<Reservation>> result = new ArrayList<>();
        List<Reservation> resultDelete = new ArrayList<>();
        List<Reservation> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Reservation> oldList, List<Reservation> newList, List<Reservation> resultUpdateOrSave, List<Reservation> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Reservation myOld = oldList.get(i);
                Reservation t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Reservation myNew = newList.get(i);
                Reservation t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }







    @Autowired
    private DriverPassengerService driverService ;
    @Autowired
    private PassengerPassengerService passengerService ;
    @Autowired
    private CarteBancairePassengerService carteBancaireService ;
    @Autowired
    private ConversationPassengerService conversationService ;
    @Autowired
    private TrajetPassengerService trajetService ;

    public ReservationPassengerServiceImpl(ReservationDao dao) {
        this.dao = dao;
    }

    private ReservationDao dao;
}
