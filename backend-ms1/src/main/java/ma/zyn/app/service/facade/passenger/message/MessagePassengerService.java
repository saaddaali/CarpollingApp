package ma.zyn.app.service.facade.passenger.message;

import java.util.List;
import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.dao.criteria.core.message.MessageCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface MessagePassengerService {



    List<Message> findByTrajetId(Long id);
    int deleteByTrajetId(Long id);
    long countByTrajetId(Long id);
    List<Message> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);
    List<Message> findByPassengerId(Long id);
    int deleteByPassengerId(Long id);
    long countByPassengerEmail(String email);
    List<Message> findByConversationId(Long id);
    int deleteByConversationId(Long id);
    long countByConversationCode(String code);




	Message create(Message t);

    Message update(Message t);

    List<Message> update(List<Message> ts,boolean createIfNotExist);

    Message findById(Long id);

    Message findOrSave(Message t);

    Message findByReferenceEntity(Message t);

    Message findWithAssociatedLists(Long id);

    List<Message> findAllOptimized();

    List<Message> findAll();

    List<Message> findByCriteria(MessageCriteria criteria);

    List<Message> findPaginatedByCriteria(MessageCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(MessageCriteria criteria);

    List<Message> delete(List<Message> ts);

    boolean deleteById(Long id);

    List<List<Message>> getToBeSavedAndToBeDeleted(List<Message> oldList, List<Message> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
