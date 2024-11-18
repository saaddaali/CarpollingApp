package ma.zyn.app.service.impl.admin.trajet;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.trajet.Ville;
import ma.zyn.app.dao.criteria.core.trajet.VilleCriteria;
import ma.zyn.app.dao.facade.core.trajet.VilleDao;
import ma.zyn.app.dao.specification.core.trajet.VilleSpecification;
import ma.zyn.app.service.facade.admin.trajet.VilleAdminService;
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
public class VilleAdminServiceImpl implements VilleAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Ville update(Ville t) {
        Ville loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Ville.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Ville findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Ville findOrSave(Ville t) {
        if (t != null) {
            Ville result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Ville> findAll() {
        return dao.findAll();
    }

    public List<Ville> findByCriteria(VilleCriteria criteria) {
        List<Ville> content = null;
        if (criteria != null) {
            VilleSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private VilleSpecification constructSpecification(VilleCriteria criteria) {
        VilleSpecification mySpecification =  (VilleSpecification) RefelexivityUtil.constructObjectUsingOneParam(VilleSpecification.class, criteria);
        return mySpecification;
    }

    public List<Ville> findPaginatedByCriteria(VilleCriteria criteria, int page, int pageSize, String order, String sortField) {
        VilleSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(VilleCriteria criteria) {
        VilleSpecification mySpecification = constructSpecification(criteria);
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
    public List<Ville> delete(List<Ville> list) {
		List<Ville> result = new ArrayList();
        if (list != null) {
            for (Ville t : list) {
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
    public Ville create(Ville t) {
        Ville loaded = findByReferenceEntity(t);
        Ville saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Ville findWithAssociatedLists(Long id){
        Ville result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Ville> update(List<Ville> ts, boolean createIfNotExist) {
        List<Ville> result = new ArrayList<>();
        if (ts != null) {
            for (Ville t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Ville loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Ville t, Ville loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Ville findByReferenceEntity(Ville t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<Ville> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Ville>> getToBeSavedAndToBeDeleted(List<Ville> oldList, List<Ville> newList) {
        List<List<Ville>> result = new ArrayList<>();
        List<Ville> resultDelete = new ArrayList<>();
        List<Ville> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Ville> oldList, List<Ville> newList, List<Ville> resultUpdateOrSave, List<Ville> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Ville myOld = oldList.get(i);
                Ville t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Ville myNew = newList.get(i);
                Ville t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public VilleAdminServiceImpl(VilleDao dao) {
        this.dao = dao;
    }

    private VilleDao dao;
}
