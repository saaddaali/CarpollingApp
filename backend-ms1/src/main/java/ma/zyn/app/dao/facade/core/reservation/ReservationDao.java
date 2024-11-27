package ma.zyn.app.dao.facade.core.reservation;

import ma.zyn.app.utils.repository.AbstractRepository;
import ma.zyn.app.bean.core.reservation.Reservation;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ReservationDao extends AbstractRepository<Reservation,Long>  {

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


}
