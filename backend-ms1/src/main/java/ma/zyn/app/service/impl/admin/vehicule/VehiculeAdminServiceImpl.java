package ma.zyn.app.service.impl.admin.vehicule;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.dao.criteria.core.vehicule.VehiculeCriteria;
import ma.zyn.app.dao.facade.core.vehicule.VehiculeDao;
import ma.zyn.app.dao.specification.core.vehicule.VehiculeSpecification;
import ma.zyn.app.service.facade.admin.vehicule.VehiculeAdminService;
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

import ma.zyn.app.service.facade.admin.driver.DriverAdminService ;
import ma.zyn.app.bean.core.driver.Driver ;

import java.util.List;
@Service
public class VehiculeAdminServiceImpl implements VehiculeAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Vehicule update(Vehicule t) {
        Vehicule loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Vehicule.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Vehicule findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Vehicule findOrSave(Vehicule t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Vehicule result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Vehicule> findAll() {
        return dao.findAll();
    }

    public List<Vehicule> findByCriteria(VehiculeCriteria criteria) {
        List<Vehicule> content = null;
        if (criteria != null) {
            VehiculeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private VehiculeSpecification constructSpecification(VehiculeCriteria criteria) {
        VehiculeSpecification mySpecification =  (VehiculeSpecification) RefelexivityUtil.constructObjectUsingOneParam(VehiculeSpecification.class, criteria);
        return mySpecification;
    }

    public List<Vehicule> findPaginatedByCriteria(VehiculeCriteria criteria, int page, int pageSize, String order, String sortField) {
        VehiculeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(VehiculeCriteria criteria) {
        VehiculeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Vehicule> findByDriverId(Long id){
        return dao.findByDriverId(id);
    }
    public int deleteByDriverId(Long id){
        return dao.deleteByDriverId(id);
    }
    public long countByDriverEmail(String email){
        return dao.countByDriverEmail(email);
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
    public List<Vehicule> delete(List<Vehicule> list) {
		List<Vehicule> result = new ArrayList();
        if (list != null) {
            for (Vehicule t : list) {
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
    public Vehicule create(Vehicule t) {
        Vehicule loaded = findByReferenceEntity(t);
        Vehicule saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Vehicule findWithAssociatedLists(Long id){
        Vehicule result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Vehicule> update(List<Vehicule> ts, boolean createIfNotExist) {
        List<Vehicule> result = new ArrayList<>();
        if (ts != null) {
            for (Vehicule t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Vehicule loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Vehicule t, Vehicule loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Vehicule findByReferenceEntity(Vehicule t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Vehicule t){
        if( t != null) {
            t.setDriver(driverService.findOrSave(t.getDriver()));
        }
    }



    public List<Vehicule> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Vehicule>> getToBeSavedAndToBeDeleted(List<Vehicule> oldList, List<Vehicule> newList) {
        List<List<Vehicule>> result = new ArrayList<>();
        List<Vehicule> resultDelete = new ArrayList<>();
        List<Vehicule> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Vehicule> oldList, List<Vehicule> newList, List<Vehicule> resultUpdateOrSave, List<Vehicule> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Vehicule myOld = oldList.get(i);
                Vehicule t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Vehicule myNew = newList.get(i);
                Vehicule t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }







    @Autowired
    private DriverAdminService driverService ;

    public VehiculeAdminServiceImpl(VehiculeDao dao) {
        this.dao = dao;
    }

    private VehiculeDao dao;
}
