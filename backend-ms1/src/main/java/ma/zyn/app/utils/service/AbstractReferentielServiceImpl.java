package ma.zyn.app.utils.service;

import ma.zyn.app.utils.bean.BaseEntity;
import ma.zyn.app.utils.criteria.BaseCriteria;
import ma.zyn.app.utils.repository.AbstractRepository;

public abstract class AbstractReferentielServiceImpl<T extends BaseEntity, CRITERIA extends BaseCriteria, REPO extends AbstractRepository<T, Long>> extends AbstractServiceImpl<T, CRITERIA, REPO> {

    public AbstractReferentielServiceImpl(REPO dao) {
        super(dao);
    }

}
