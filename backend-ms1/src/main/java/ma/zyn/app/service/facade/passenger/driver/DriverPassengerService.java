package ma.zyn.app.service.facade.passenger.driver;

import java.util.List;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;


public interface DriverPassengerService {


    Driver findByUsername(String username);
    boolean changePassword(String username, String newPassword);

    boolean verifyDriver(String cin, String fullName);




	Driver create(Driver t);

    Driver update(Driver t);

    List<Driver> update(List<Driver> ts,boolean createIfNotExist);

    Driver findById(Long id);

    Driver findOrSave(Driver t);

    Driver findByReferenceEntity(Driver t);

    Driver findWithAssociatedLists(Long id);

    List<Driver> findAllOptimized();

    List<Driver> findAll();

    List<Driver> findByCriteria(DriverCriteria criteria);

    List<Driver> findPaginatedByCriteria(DriverCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(DriverCriteria criteria);

    List<Driver> delete(List<Driver> ts);

    boolean deleteById(Long id);

    List<List<Driver>> getToBeSavedAndToBeDeleted(List<Driver> oldList, List<Driver> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
