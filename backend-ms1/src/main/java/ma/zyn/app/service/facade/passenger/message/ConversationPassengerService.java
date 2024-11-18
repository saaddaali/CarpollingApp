package ma.zyn.app.service.facade.passenger.message;

import java.util.List;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.dao.criteria.core.message.ConversationCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface ConversationPassengerService {



    List<Conversation> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);
    List<Conversation> findByPassengerId(Long id);
    int deleteByPassengerId(Long id);
    long countByPassengerEmail(String email);




	Conversation create(Conversation t);

    Conversation update(Conversation t);

    List<Conversation> update(List<Conversation> ts,boolean createIfNotExist);

    Conversation findById(Long id);

    Conversation findOrSave(Conversation t);

    Conversation findByReferenceEntity(Conversation t);

    Conversation findWithAssociatedLists(Long id);

    List<Conversation> findAllOptimized();

    List<Conversation> findAll();

    List<Conversation> findByCriteria(ConversationCriteria criteria);

    List<Conversation> findPaginatedByCriteria(ConversationCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ConversationCriteria criteria);

    List<Conversation> delete(List<Conversation> ts);

    boolean deleteById(Long id);

    List<List<Conversation>> getToBeSavedAndToBeDeleted(List<Conversation> oldList, List<Conversation> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
