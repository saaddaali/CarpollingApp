package ma.zyn.app.service.impl.passenger.message;


import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.service.facade.passenger.message.MessagePassengerService;
import ma.zyn.app.utils.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.dao.criteria.core.message.ConversationCriteria;
import ma.zyn.app.dao.facade.core.message.ConversationDao;
import ma.zyn.app.dao.specification.core.message.ConversationSpecification;
import ma.zyn.app.service.facade.passenger.message.ConversationPassengerService;

import static ma.zyn.app.utils.util.ListUtil.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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

@Service
public class ConversationPassengerServiceImpl implements ConversationPassengerService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Conversation update(Conversation t) {
        Conversation loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Conversation.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Conversation findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Conversation findOrSave(Conversation t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Conversation result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Conversation> findAll() {
        return dao.findAll();
    }

    public List<Conversation> findByCriteria(ConversationCriteria criteria) {
        List<Conversation> content = null;
        if (criteria != null) {
            ConversationSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ConversationSpecification constructSpecification(ConversationCriteria criteria) {
        ConversationSpecification mySpecification =  (ConversationSpecification) RefelexivityUtil.constructObjectUsingOneParam(ConversationSpecification.class, criteria);
        return mySpecification;
    }

    public List<Conversation> findPaginatedByCriteria(ConversationCriteria criteria, int page, int pageSize, String order, String sortField) {
        ConversationSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ConversationCriteria criteria) {
        ConversationSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Conversation> findByDriverId(Long id){
        return dao.findByDriverId(id);
    }
    public int deleteByDriverId(Long id){
        return dao.deleteByDriverId(id);
    }
    public long countByDriverEmail(String email){
        return dao.countByDriverEmail(email);
    }
    public List<Conversation> findByPassengerId(Long id){
        return dao.findByPassengerId(id);
    }
    public int deleteByPassengerId(Long id){
        return dao.deleteByPassengerId(id);
    }
    public long countByPassengerEmail(String email){
        return dao.countByPassengerEmail(email);
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
    public List<Conversation> delete(List<Conversation> list) {
		List<Conversation> result = new ArrayList();
        if (list != null) {
            for (Conversation t : list) {
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
    public Conversation create(Conversation t) {
        Conversation loaded = findByReferenceEntity(t);
        Conversation saved;
        Message message = new Message();
        message.setConversation(t);
        message.setPassenger(t.getPassenger());
        message.setDriver(t.getDriver());
        message.setContenu("Bonjour, je peux vous aider ?");
        t.setLibelle("Bonjour, je peux vous aider ?");
        LocalDateTime date = LocalDateTime.now();
        message.setDateEnvoi(date);
        if (loaded == null) {
            saved = dao.save(t);
            messageService.create(message);
        }else {
            saved = null;
        }
        return saved;
    }

    public Conversation findWithAssociatedLists(Long id){
        Conversation result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Conversation> update(List<Conversation> ts, boolean createIfNotExist) {
        List<Conversation> result = new ArrayList<>();
        if (ts != null) {
            for (Conversation t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Conversation loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Conversation t, Conversation loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Conversation findByReferenceEntity(Conversation t){
        return t==null? null : dao.findByCode(t.getCode());
    }
    public void findOrSaveAssociatedObject(Conversation t){
        if( t != null) {
            t.setDriver(driverService.findOrSave(t.getDriver()));
            t.setPassenger(passengerService.findOrSave(t.getPassenger()));
        }
    }



    public List<Conversation> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Conversation>> getToBeSavedAndToBeDeleted(List<Conversation> oldList, List<Conversation> newList) {
        List<List<Conversation>> result = new ArrayList<>();
        List<Conversation> resultDelete = new ArrayList<>();
        List<Conversation> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Conversation> oldList, List<Conversation> newList, List<Conversation> resultUpdateOrSave, List<Conversation> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Conversation myOld = oldList.get(i);
                Conversation t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Conversation myNew = newList.get(i);
                Conversation t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private MessagePassengerService messageService ;

    public ConversationPassengerServiceImpl(ConversationDao dao) {
        this.dao = dao;
    }

    private ConversationDao dao;
}
