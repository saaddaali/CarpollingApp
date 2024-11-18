package ma.zyn.app.dao.facade.core.passenger;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.passenger.Passenger;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.passenger.Passenger;
import java.util.List;


@Repository
public interface PassengerDao extends AbstractRepository<Passenger,Long>  {
    Passenger findByEmail(String email);
    int deleteByEmail(String email);

    List<Passenger> findByCarteBancaireId(Long id);
    int deleteByCarteBancaireId(Long id);
    long countByCarteBancaireId(Long id);
    Passenger findByUsername(String username);

    @Query("SELECT NEW Passenger(item.id,item.email) FROM Passenger item")
    List<Passenger> findAllOptimized();

}
