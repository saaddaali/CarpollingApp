package ma.zyn.app.service.impl.driver.passenger;


import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;
import ma.zyn.app.dao.facade.core.passenger.PassengerDao;
import ma.zyn.app.dao.specification.core.passenger.PassengerSpecification;
import ma.zyn.app.service.facade.driver.passenger.PassengerDriverService;
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

import ma.zyn.app.service.facade.driver.paiement.CarteBancaireDriverService ;
import ma.zyn.app.bean.core.paiement.CarteBancaire ;

import java.time.LocalDateTime;
import ma.zyn.app.zynerator.security.service.facade.UserService;
import ma.zyn.app.zynerator.security.service.facade.RoleService;
import ma.zyn.app.zynerator.security.service.facade.RoleUserService;
import ma.zyn.app.zynerator.security.bean.Role;
import ma.zyn.app.zynerator.security.bean.RoleUser;
import ma.zyn.app.zynerator.security.common.AuthoritiesConstants;
import ma.zyn.app.zynerator.security.service.facade.ModelPermissionUserService;
import java.util.Collection;
import java.util.List;
@Service
public class PassengerDriverServiceImpl implements PassengerDriverService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Passenger update(Passenger t) {
        Passenger loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Passenger.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Passenger findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Passenger findOrSave(Passenger t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Passenger result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Passenger> findAll() {
        return dao.findAll();
    }

    public List<Passenger> findByCriteria(PassengerCriteria criteria) {
        List<Passenger> content = null;
        if (criteria != null) {
            PassengerSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private PassengerSpecification constructSpecification(PassengerCriteria criteria) {
        PassengerSpecification mySpecification =  (PassengerSpecification) RefelexivityUtil.constructObjectUsingOneParam(PassengerSpecification.class, criteria);
        return mySpecification;
    }

    public List<Passenger> findPaginatedByCriteria(PassengerCriteria criteria, int page, int pageSize, String order, String sortField) {
        PassengerSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(PassengerCriteria criteria) {
        PassengerSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Passenger> findByCarteBancaireId(Long id){
        return dao.findByCarteBancaireId(id);
    }
    public int deleteByCarteBancaireId(Long id){
        return dao.deleteByCarteBancaireId(id);
    }
    public long countByCarteBancaireId(Long id){
        return dao.countByCarteBancaireId(id);
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
    public List<Passenger> delete(List<Passenger> list) {
		List<Passenger> result = new ArrayList();
        if (list != null) {
            for (Passenger t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }


    public Passenger findWithAssociatedLists(Long id){
        Passenger result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Passenger> update(List<Passenger> ts, boolean createIfNotExist) {
        List<Passenger> result = new ArrayList<>();
        if (ts != null) {
            for (Passenger t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Passenger loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Passenger t, Passenger loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Passenger findByReferenceEntity(Passenger t){
        return t==null? null : dao.findByEmail(t.getEmail());
    }
    public void findOrSaveAssociatedObject(Passenger t){
        if( t != null) {
            t.setCarteBancaire(carteBancaireService.findOrSave(t.getCarteBancaire()));
        }
    }



    public List<Passenger> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Passenger>> getToBeSavedAndToBeDeleted(List<Passenger> oldList, List<Passenger> newList) {
        List<List<Passenger>> result = new ArrayList<>();
        List<Passenger> resultDelete = new ArrayList<>();
        List<Passenger> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Passenger> oldList, List<Passenger> newList, List<Passenger> resultUpdateOrSave, List<Passenger> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Passenger myOld = oldList.get(i);
                Passenger t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Passenger myNew = newList.get(i);
                Passenger t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }


    @Override
    public Passenger create(Passenger t) {
        if (findByUsername(t.getUsername()) != null || t.getPassword() == null) return null;
        t.setPassword(userService.cryptPassword(t.getPassword()));
        t.setEnabled(true);
        t.setAccountNonExpired(true);
        t.setAccountNonLocked(true);
        t.setCredentialsNonExpired(true);
        t.setPasswordChanged(false);

        Role role = new Role();
        role.setAuthority(AuthoritiesConstants.PASSENGER);
        role.setCreatedAt(LocalDateTime.now());
        Role myRole = roleService.create(role);
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(myRole);
        if (t.getRoleUsers() == null)
            t.setRoleUsers(new ArrayList<>());

        t.getRoleUsers().add(roleUser);
        if (t.getModelPermissionUsers() == null)
            t.setModelPermissionUsers(new ArrayList<>());

        t.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        Passenger mySaved = dao.save(t);

        if (t.getModelPermissionUsers() != null) {
            t.getModelPermissionUsers().forEach(e -> {
                e.setUser(mySaved);
                modelPermissionUserService.create(e);
            });
        }
        if (t.getRoleUsers() != null) {
            t.getRoleUsers().forEach(element-> {
                element.setUser(mySaved);
                roleUserService.create(element);
            });
        }

        return mySaved;
     }

    public Passenger findByUsername(String username){
        return dao.findByUsername(username);
    }

    public boolean changePassword(String username, String newPassword) {
        return userService.changePassword(username, newPassword);
    }




    private @Autowired UserService userService;
    private @Autowired RoleService roleService;
    private @Autowired ModelPermissionUserService modelPermissionUserService;
    private @Autowired RoleUserService roleUserService;

    @Autowired
    private CarteBancaireDriverService carteBancaireService ;

    public PassengerDriverServiceImpl(PassengerDao dao) {
        this.dao = dao;
    }

    private PassengerDao dao;
}
