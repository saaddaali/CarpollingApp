package ma.zyn.app.service.impl.admin.driver;


import ma.zyn.app.utils.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.dao.facade.core.driver.DriverDao;
import ma.zyn.app.dao.specification.core.driver.DriverSpecification;
import ma.zyn.app.service.facade.admin.driver.DriverAdminService;

import static ma.zyn.app.utils.util.ListUtil.*;

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


import java.time.LocalDateTime;
import ma.zyn.app.utils.security.service.facade.UserService;
import ma.zyn.app.utils.security.service.facade.RoleService;
import ma.zyn.app.utils.security.service.facade.RoleUserService;
import ma.zyn.app.utils.security.bean.Role;
import ma.zyn.app.utils.security.bean.RoleUser;
import ma.zyn.app.utils.security.common.AuthoritiesConstants;
import ma.zyn.app.utils.security.service.facade.ModelPermissionUserService;

@Service
public class DriverAdminServiceImpl implements DriverAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Driver update(Driver t) {
        Driver loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Driver.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Driver findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Driver findOrSave(Driver t) {
        if (t != null) {
            Driver result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Driver> findAll() {
        return dao.findAll();
    }

    public List<Driver> findByCriteria(DriverCriteria criteria) {
        List<Driver> content = null;
        if (criteria != null) {
            DriverSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private DriverSpecification constructSpecification(DriverCriteria criteria) {
        DriverSpecification mySpecification =  (DriverSpecification) RefelexivityUtil.constructObjectUsingOneParam(DriverSpecification.class, criteria);
        return mySpecification;
    }

    public List<Driver> findPaginatedByCriteria(DriverCriteria criteria, int page, int pageSize, String order, String sortField) {
        DriverSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(DriverCriteria criteria) {
        DriverSpecification mySpecification = constructSpecification(criteria);
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
    public List<Driver> delete(List<Driver> list) {
		List<Driver> result = new ArrayList();
        if (list != null) {
            for (Driver t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }


    public Driver findWithAssociatedLists(Long id){
        Driver result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Driver> update(List<Driver> ts, boolean createIfNotExist) {
        List<Driver> result = new ArrayList<>();
        if (ts != null) {
            for (Driver t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Driver loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Driver t, Driver loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Driver findByReferenceEntity(Driver t){
        return t==null? null : dao.findByEmail(t.getEmail());
    }



    public List<Driver> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Driver>> getToBeSavedAndToBeDeleted(List<Driver> oldList, List<Driver> newList) {
        List<List<Driver>> result = new ArrayList<>();
        List<Driver> resultDelete = new ArrayList<>();
        List<Driver> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Driver> oldList, List<Driver> newList, List<Driver> resultUpdateOrSave, List<Driver> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Driver myOld = oldList.get(i);
                Driver t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Driver myNew = newList.get(i);
                Driver t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    public Driver create(Driver t) {
        if (findByUsername(t.getUsername()) != null || t.getPassword() == null) return null;
        t.setPassword(userService.cryptPassword(t.getPassword()));
        t.setEnabled(true);
        t.setAccountNonExpired(true);
        t.setAccountNonLocked(true);
        t.setCredentialsNonExpired(true);
        t.setPasswordChanged(false);

        Role role = new Role();
        role.setAuthority(AuthoritiesConstants.DRIVER);
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

        Driver mySaved = dao.save(t);

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

    public Driver findByUsername(String username){
        return dao.findByUsername(username);
    }

    public boolean changePassword(String username, String newPassword) {
        return userService.changePassword(username, newPassword);
    }




    private @Autowired UserService userService;
    private @Autowired RoleService roleService;
    private @Autowired ModelPermissionUserService modelPermissionUserService;
    private @Autowired RoleUserService roleUserService;


    public DriverAdminServiceImpl(DriverDao dao) {
        this.dao = dao;
    }

    private DriverDao dao;
}
