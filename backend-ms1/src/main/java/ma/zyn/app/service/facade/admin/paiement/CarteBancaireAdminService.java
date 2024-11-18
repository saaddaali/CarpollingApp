package ma.zyn.app.service.facade.admin.paiement;

import java.util.List;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.dao.criteria.core.paiement.CarteBancaireCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface CarteBancaireAdminService {







	CarteBancaire create(CarteBancaire t);

    CarteBancaire update(CarteBancaire t);

    List<CarteBancaire> update(List<CarteBancaire> ts,boolean createIfNotExist);

    CarteBancaire findById(Long id);

    CarteBancaire findOrSave(CarteBancaire t);

    CarteBancaire findByReferenceEntity(CarteBancaire t);

    CarteBancaire findWithAssociatedLists(Long id);

    List<CarteBancaire> findAllOptimized();

    List<CarteBancaire> findAll();

    List<CarteBancaire> findByCriteria(CarteBancaireCriteria criteria);

    List<CarteBancaire> findPaginatedByCriteria(CarteBancaireCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CarteBancaireCriteria criteria);

    List<CarteBancaire> delete(List<CarteBancaire> ts);

    boolean deleteById(Long id);

    List<List<CarteBancaire>> getToBeSavedAndToBeDeleted(List<CarteBancaire> oldList, List<CarteBancaire> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
