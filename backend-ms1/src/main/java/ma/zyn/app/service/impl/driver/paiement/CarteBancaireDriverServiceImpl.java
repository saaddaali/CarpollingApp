package ma.zyn.app.service.impl.driver.paiement;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.dao.criteria.core.paiement.CarteBancaireCriteria;
import ma.zyn.app.dao.facade.core.paiement.CarteBancaireDao;
import ma.zyn.app.dao.specification.core.paiement.CarteBancaireSpecification;
import ma.zyn.app.service.facade.driver.paiement.CarteBancaireDriverService;
import ma.zyn.app.zynerator.service.AbstractServiceImpl;
import static ma.zyn.app.zynerator.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.zynerator.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
@Service
public class CarteBancaireDriverServiceImpl implements CarteBancaireDriverService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CarteBancaire update(CarteBancaire t) {
        CarteBancaire loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{CarteBancaire.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public CarteBancaire findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public CarteBancaire findOrSave(CarteBancaire t) {
        if (t != null) {
            CarteBancaire result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<CarteBancaire> findAll() {
        return dao.findAll();
    }

    public List<CarteBancaire> findByCriteria(CarteBancaireCriteria criteria) {
        List<CarteBancaire> content = null;
        if (criteria != null) {
            CarteBancaireSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CarteBancaireSpecification constructSpecification(CarteBancaireCriteria criteria) {
        CarteBancaireSpecification mySpecification =  (CarteBancaireSpecification) RefelexivityUtil.constructObjectUsingOneParam(CarteBancaireSpecification.class, criteria);
        return mySpecification;
    }

    public List<CarteBancaire> findPaginatedByCriteria(CarteBancaireCriteria criteria, int page, int pageSize, String order, String sortField) {
        CarteBancaireSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CarteBancaireCriteria criteria) {
        CarteBancaireSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CarteBancaire> delete(List<CarteBancaire> list) {
		List<CarteBancaire> result = new ArrayList();
        if (list != null) {
            for (CarteBancaire t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CarteBancaire create(CarteBancaire t) {
        CarteBancaire loaded = findByReferenceEntity(t);
        CarteBancaire saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public CarteBancaire findWithAssociatedLists(Long id){
        CarteBancaire result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CarteBancaire> update(List<CarteBancaire> ts, boolean createIfNotExist) {
        List<CarteBancaire> result = new ArrayList<>();
        if (ts != null) {
            for (CarteBancaire t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    CarteBancaire loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, CarteBancaire t, CarteBancaire loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public CarteBancaire findByReferenceEntity(CarteBancaire t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }



    public List<CarteBancaire> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<CarteBancaire>> getToBeSavedAndToBeDeleted(List<CarteBancaire> oldList, List<CarteBancaire> newList) {
        List<List<CarteBancaire>> result = new ArrayList<>();
        List<CarteBancaire> resultDelete = new ArrayList<>();
        List<CarteBancaire> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<CarteBancaire> oldList, List<CarteBancaire> newList, List<CarteBancaire> resultUpdateOrSave, List<CarteBancaire> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                CarteBancaire myOld = oldList.get(i);
                CarteBancaire t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                CarteBancaire myNew = newList.get(i);
                CarteBancaire t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public CarteBancaireDriverServiceImpl(CarteBancaireDao dao) {
        this.dao = dao;
    }

    private CarteBancaireDao dao;
}
