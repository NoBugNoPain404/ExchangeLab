package huynguyen.exchange_lab.market.repository;

import huynguyen.exchange_lab.market.entities.KlineData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class KlineHistoryRepositoryImpl<T extends KlineData>
    implements KlineHistoryRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> entityClass;

    protected KlineHistoryRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<T> findWithPagination(Integer tradingPairId, Instant endTime, Integer limit) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<T> query = cb.createQuery(entityClass);

        Root<T> root = query.from(entityClass);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                cb.equal(
                        root.get("id")
                                .get("tradingPairId"),
                        tradingPairId)
        );

        if (endTime != null) {
            predicates.add(
                    cb.lessThan(
                            root.get("id")
                                    .get("startTime"),
                            endTime
                    )
            );
        }

        query.where(predicates.toArray(Predicate[]::new));

        query.orderBy(
                cb.desc(root.get("id")
                                .get("startTime"))
        );

        return em.createQuery(query)
                .setMaxResults(limit)
                .getResultList();
    }
}

