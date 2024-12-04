package ma.zyn.app.service.impl.passenger.trajet;


import ma.zyn.app.utils.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.dao.criteria.core.trajet.TrajetCriteria;
import ma.zyn.app.dao.facade.core.trajet.TrajetDao;
import ma.zyn.app.dao.specification.core.trajet.TrajetSpecification;
import ma.zyn.app.service.facade.passenger.trajet.TrajetPassengerService;

import static ma.zyn.app.utils.security.common.SecurityUtil.getCurrentUser;
import static ma.zyn.app.utils.util.ListUtil.*;

import ma.zyn.app.utils.security.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.utils.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.zyn.app.service.facade.passenger.driver.DriverPassengerService ;
import ma.zyn.app.service.facade.passenger.trajet.VillePassengerService ;

@Service
public class TrajetPassengerServiceImpl implements TrajetPassengerService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Trajet update(Trajet t) {
        Trajet loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Trajet.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Trajet findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Trajet findOrSave(Trajet t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Trajet result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Trajet> findAll() {
        return dao.findAll();
    }

    public List<Trajet> findByCriteria(TrajetCriteria criteria) {
        List<Trajet> content = null;
        if (criteria != null) {
            TrajetSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private TrajetSpecification constructSpecification(TrajetCriteria criteria) {
        TrajetSpecification mySpecification =  (TrajetSpecification) RefelexivityUtil.constructObjectUsingOneParam(TrajetSpecification.class, criteria);
        return mySpecification;
    }

    public List<Trajet> findPaginatedByCriteria(TrajetCriteria criteria, int page, int pageSize, String order, String sortField) {
        TrajetSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(TrajetCriteria criteria) {
        TrajetSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Trajet> findByVilleDepartId(Long id){
        return dao.findByVilleDepartId(id);
    }
    public int deleteByVilleDepartId(Long id){
        return dao.deleteByVilleDepartId(id);
    }
    public long countByVilleDepartCode(String code){
        return dao.countByVilleDepartCode(code);
    }
    public List<Trajet> findByVilleDestinationId(Long id){
        return dao.findByVilleDestinationId(id);
    }
    public int deleteByVilleDestinationId(Long id){
        return dao.deleteByVilleDestinationId(id);
    }
    public long countByVilleDestinationCode(String code){
        return dao.countByVilleDestinationCode(code);
    }
    public List<Trajet> findByDriverId(Long id){
        return dao.findByDriverId(id);
    }
    public int deleteByDriverId(Long id){
        return dao.deleteByDriverId(id);
    }
    public long countByDriverEmail(String email){
        return dao.countByDriverEmail(email);
    }
    public List<Trajet> findByLocalisationSourceId(Long id){
        return dao.findByLocalisationSourceId(id);
    }
    public int deleteByLocalisationSourceId(Long id){
        return dao.deleteByLocalisationSourceId(id);
    }
    public long countByLocalisationSourceCode(String code){
        return dao.countByLocalisationSourceCode(code);
    }
    public List<Trajet> findByLocalisationDestinationId(Long id){
        return dao.findByLocalisationDestinationId(id);
    }
    public int deleteByLocalisationDestinationId(Long id){
        return dao.deleteByLocalisationDestinationId(id);
    }
    public long countByLocalisationDestinationCode(String code){
        return dao.countByLocalisationDestinationCode(code);
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
    public List<Trajet> delete(List<Trajet> list) {
		List<Trajet> result = new ArrayList();
        if (list != null) {
            for (Trajet t : list) {
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
    public Trajet create(Trajet t) {
        Trajet loaded = findByReferenceEntity(t);
        Trajet saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Trajet findWithAssociatedLists(Long id){
        Trajet result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Trajet> update(List<Trajet> ts, boolean createIfNotExist) {
        List<Trajet> result = new ArrayList<>();
        if (ts != null) {
            for (Trajet t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Trajet loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Trajet t, Trajet loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Trajet findByReferenceEntity(Trajet t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(Trajet t){
        if( t != null) {
            t.setVilleDepart(villeService.findOrSave(t.getVilleDepart()));
            t.setVilleDestination(villeService.findOrSave(t.getVilleDestination()));
            t.setDriver(driverService.findOrSave(t.getDriver()));
            t.setLocalisationSource(villeService.findOrSave(t.getLocalisationSource()));
            t.setLocalisationDestination(villeService.findOrSave(t.getLocalisationDestination()));
        }
    }



    public List<Trajet> findAllOptimized() {
        return dao.findAll();
    }

    public List<Trajet> findAllOptimizedDriver() {
        String currentUser = getCurrentUser();
        return dao.findByDriverUsername(currentUser);
    }

    public String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (currentUser != null && currentUser instanceof String) {
                return (String) currentUser;
            } else if (currentUser != null && currentUser instanceof User) {
                return ((User) currentUser).getUsername();
            } else return null;
        }

        return null;
    }

    @Override
    public List<List<Trajet>> getToBeSavedAndToBeDeleted(List<Trajet> oldList, List<Trajet> newList) {
        List<List<Trajet>> result = new ArrayList<>();
        List<Trajet> resultDelete = new ArrayList<>();
        List<Trajet> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Trajet> oldList, List<Trajet> newList, List<Trajet> resultUpdateOrSave, List<Trajet> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Trajet myOld = oldList.get(i);
                Trajet t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Trajet myNew = newList.get(i);
                Trajet t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private DriverPassengerService driverService ;
    @Autowired
    private VillePassengerService villeService ;

    public TrajetPassengerServiceImpl(TrajetDao dao) {
        this.dao = dao;
    }

    private TrajetDao dao;
}
