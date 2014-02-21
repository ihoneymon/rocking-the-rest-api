package kr.pe.ihoney.jco.restapi.repository.executor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;

@Transactional(readOnly = true)
public abstract class AbstractJPAQueryPredicateExecutor<T> implements JPQLQueryPredicateExecutor<T> {
    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final Class<T> entityClass;
    private final EntityPath<T> path;
    private final PathBuilder<T> builder;

    @PersistenceContext(unitName = "rocking-the-rest-api")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractJPAQueryPredicateExecutor() {
        this.entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),
                AbstractJPAQueryPredicateExecutor.class);
        this.path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityClass);
        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
    }

    @Override
    public List<T> findAll(JPQLQuery query) {
        return query.list(path);
    }

    @Override
    public Page<T> findAll(JPQLQuery query, Pageable pageable) {
        long count = query.count();
        applyPagination(query, pageable);
        return new PageImpl<T>(query.list(path), pageable, count);
    }

    /*
     * @see org.springframework.data.jpa.repository.support.QueryDslJpaRepository#createQuery(Predicate... predicate)
     */
    @Override
    public JPQLQuery createQuery(Predicate... predicate) {
        return new JPAQuery(entityManager).from(path).where(predicate);
    }

    /*
     * @see org.springframework.data.jpa.repository.support.QueryDslJpaRepository#applyPagination(JPQLQuery query,
     * Pageable pageable)
     */
    protected JPQLQuery applyPagination(JPQLQuery query, Pageable pageable) {
        if (pageable == null) {
            return query;
        }

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        return applySorting(query, pageable.getSort());
    }

    /*
     * @see org.springframework.data.jpa.repository.support.QueryDslJpaRepository#applySorting(JPQLQuery query, Sort
     * sort)
     */
    protected JPQLQuery applySorting(JPQLQuery query, Sort sort) {
        if (sort == null) {
            return query;
        }

        for (Order order : sort) {
            query.orderBy(toOrder(order));
        }

        return query;
    }

    /*
     * @see org.springframework.data.jpa.repository.support.QueryDslJpaRepository#toOrder(Order order)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected OrderSpecifier<?> toOrder(Order order) {
        Expression<Object> property = builder.get(order.getProperty());
        return new OrderSpecifier(order.isAscending() ? com.mysema.query.types.Order.ASC
                : com.mysema.query.types.Order.DESC, property);
    }
}
