package ma.zyn.app.dao.facade.core.paiement;

import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CarteBancaireDao extends AbstractRepository<CarteBancaire,Long>  {



}
