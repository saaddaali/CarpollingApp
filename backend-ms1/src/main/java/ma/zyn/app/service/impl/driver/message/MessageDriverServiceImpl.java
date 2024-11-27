package ma.zyn.app.service.impl.driver.message;


import ma.zyn.app.utils.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.dao.criteria.core.message.MessageCriteria;
import ma.zyn.app.dao.facade.core.message.MessageDao;
import ma.zyn.app.dao.specification.core.message.MessageSpecification;
import ma.zyn.app.service.facade.driver.message.MessageDriverService;

import static ma.zyn.app.utils.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.utils.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.zyn.app.service.facade.driver.driver.DriverDriverService ;
import ma.zyn.app.service.facade.driver.passenger.PassengerDriverService ;
import ma.zyn.app.service.facade.driver.message.ConversationDriverService ;
import ma.zyn.app.service.facade.driver.trajet.TrajetDriverService ;

@Service
public class MessageDriverServiceImpl implements MessageDriverService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Message update(Message t) {
        Message loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Message.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Message findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Message findOrSave(Message t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Message result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Message> findAll() {
        return dao.findAll();
    }

    public List<Message> findByCriteria(MessageCriteria criteria) {
        List<Message> content = null;
        if (criteria != null) {
            MessageSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private MessageSpecification constructSpecification(MessageCriteria criteria) {
        MessageSpecification mySpecification =  (MessageSpecification) RefelexivityUtil.constructObjectUsingOneParam(MessageSpecification.class, criteria);
        return mySpecification;
    }

    public List<Message> findPaginatedByCriteria(MessageCriteria criteria, int page, int pageSize, String order, String sortField) {
        MessageSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(MessageCriteria criteria) {
        MessageSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Message> findByTrajetId(Long id){
        return dao.findByTrajetId(id);
    }
    public int deleteByTrajetId(Long id){
        return dao.deleteByTrajetId(id);
    }
    public long countByTrajetId(Long id){
        return dao.countByTrajetId(id);
    }
    public List<Message> findByDriverId(Long id){
        return dao.findByDriverId(id);
    }
    public int deleteByDriverId(Long id){
        return dao.deleteByDriverId(id);
    }
    public long countByDriverEmail(String email){
        return dao.countByDriverEmail(email);
    }
    public List<Message> findByPassengerId(Long id){
        return dao.findByPassengerId(id);
    }
    public int deleteByPassengerId(Long id){
        return dao.deleteByPassengerId(id);
    }
    public long countByPassengerEmail(String email){
        return dao.countByPassengerEmail(email);
    }
    public List<Message> findByConversationId(Long id){
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
    public List<Message> delete(List<Message> list) {
		List<Message> result = new ArrayList();
        if (list != null) {
            for (Message t : list) {
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
    public Message create(Message t) {
        Message loaded = findByReferenceEntity(t);
        Message saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Message findWithAssociatedLists(Long id){
        Message result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Message> update(List<Message> ts, boolean createIfNotExist) {
        List<Message> result = new ArrayList<>();
        if (ts != null) {
            for (Message t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Message loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Message t, Message loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Message findByReferenceEntity(Message t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Message t){
        if( t != null) {
            t.setTrajet(trajetService.findOrSave(t.getTrajet()));
            t.setDriver(driverService.findOrSave(t.getDriver()));
            t.setPassenger(passengerService.findOrSave(t.getPassenger()));
            t.setConversation(conversationService.findOrSave(t.getConversation()));
        }
    }



    public List<Message> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<Message>> getToBeSavedAndToBeDeleted(List<Message> oldList, List<Message> newList) {
        List<List<Message>> result = new ArrayList<>();
        List<Message> resultDelete = new ArrayList<>();
        List<Message> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Message> oldList, List<Message> newList, List<Message> resultUpdateOrSave, List<Message> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Message myOld = oldList.get(i);
                Message t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Message myNew = newList.get(i);
                Message t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private DriverDriverService driverService ;
    @Autowired
    private PassengerDriverService passengerService ;
    @Autowired
    private ConversationDriverService conversationService ;
    @Autowired
    private TrajetDriverService trajetService ;

    public MessageDriverServiceImpl(MessageDao dao) {
        this.dao = dao;
    }

    private MessageDao dao;
}
