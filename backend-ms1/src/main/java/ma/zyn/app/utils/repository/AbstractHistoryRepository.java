package ma.zyn.app.utils.repository;

import ma.zyn.app.utils.bean.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractHistoryRepository<H extends BaseEntity, ID> extends JpaRepository<H, ID>, JpaSpecificationExecutor<H> {
}
