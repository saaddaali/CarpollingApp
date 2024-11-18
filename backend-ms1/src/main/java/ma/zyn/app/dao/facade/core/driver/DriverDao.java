package ma.zyn.app.dao.facade.core.driver;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.driver.Driver;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.driver.Driver;
import java.util.List;


@Repository
public interface DriverDao extends AbstractRepository<Driver,Long>  {
    Driver findByEmail(String email);
    int deleteByEmail(String email);

    Driver findByUsername(String username);

    @Query("SELECT NEW Driver(item.id,item.email) FROM Driver item")
    List<Driver> findAllOptimized();

}
