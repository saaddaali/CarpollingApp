package ma.zyn.app.dao.facade.core.message;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.utils.repository.AbstractRepository;
import ma.zyn.app.bean.core.message.Conversation;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConversationDao extends AbstractRepository<Conversation,Long>  {
    Conversation findByCode(String code);
    int deleteByCode(String code);

    List<Conversation> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);
    List<Conversation> findByPassengerId(Long id);
    int deleteByPassengerId(Long id);
    long countByPassengerEmail(String email);

    @Query("SELECT NEW Conversation(item.id,item.libelle) FROM Conversation item")
    List<Conversation> findAllOptimized();

}
