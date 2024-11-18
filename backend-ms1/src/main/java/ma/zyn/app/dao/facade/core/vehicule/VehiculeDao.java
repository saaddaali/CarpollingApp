package ma.zyn.app.dao.facade.core.vehicule;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.vehicule.Vehicule;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface VehiculeDao extends AbstractRepository<Vehicule,Long>  {

    List<Vehicule> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);

    @Query("SELECT NEW Vehicule(item.id,item.plaqueImmatriculation) FROM Vehicule item")
    List<Vehicule> findAllOptimized();

}
