package org.openmrs.module.msfcore.api.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.openmrs.module.msfcore.Pagination;

public class MSFCoreBaseDao {

    public void applyPaginationToCriteria(Pagination pagination, Criteria criteria) {
        if (pagination != null) {
            pagination.setTotalItemsNumber((int) (long) (Long) criteria.setProjection(Projections.rowCount()).uniqueResult());
            criteria.setFirstResult(pagination.getFromItemNumber() - 1);
            if (pagination.getToItemNumber() != null) {
                int maxResults = pagination.getToItemNumber() - pagination.getFromItemNumber();
                criteria.setMaxResults(maxResults + 1);
            }
            criteria.setProjection(null);
        }
    }
}
