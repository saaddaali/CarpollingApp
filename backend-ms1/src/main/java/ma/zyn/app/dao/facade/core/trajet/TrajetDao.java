package ma.zyn.app.dao.facade.core.trajet;

import ma.zyn.app.utils.repository.AbstractRepository;
import ma.zyn.app.bean.core.trajet.Trajet;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TrajetDao extends AbstractRepository<Trajet,Long>  {

    List<Trajet> findByVilleDepartId(Long id);
    int deleteByVilleDepartId(Long id);
    long countByVilleDepartCode(String code);
    List<Trajet> findByVilleDestinationId(Long id);
    int deleteByVilleDestinationId(Long id);
    long countByVilleDestinationCode(String code);
    List<Trajet> findByDriverId(Long id);
    int deleteByDriverId(Long id);
    long countByDriverEmail(String email);
    List<Trajet> findByLocalisationSourceId(Long id);
    int deleteByLocalisationSourceId(Long id);
    long countByLocalisationSourceCode(String code);
    List<Trajet> findByLocalisationDestinationId(Long id);
    int deleteByLocalisationDestinationId(Long id);
    long countByLocalisationDestinationCode(String code);

    List<Trajet> findByDriverUsername(String username);



}
