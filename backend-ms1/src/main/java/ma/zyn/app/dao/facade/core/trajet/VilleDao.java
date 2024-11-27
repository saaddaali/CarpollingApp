package ma.zyn.app.dao.facade.core.trajet;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.utils.repository.AbstractRepository;
import ma.zyn.app.bean.core.trajet.Ville;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VilleDao extends AbstractRepository<Ville,Long>  {
    Ville findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW Ville(item.id,item.libelle) FROM Ville item")
    List<Ville> findAllOptimized();

}
