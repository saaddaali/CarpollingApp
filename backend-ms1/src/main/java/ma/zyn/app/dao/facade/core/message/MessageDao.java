package ma.zyn.app.dao.facade.core.message;

import ma.zyn.app.utils.repository.AbstractRepository;
import ma.zyn.app.bean.core.message.Message;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MessageDao extends AbstractRepository<Message,Long>  {

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


}
