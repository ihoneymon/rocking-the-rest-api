package kr.pe.ihoney.jco.restapi.repository.executor;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JPQLQueryPredicateExecutor<T> {
	List<T> findAll(JPQLQuery query);
	Page<T> findAll(JPQLQuery query, Pageable pageable);
	JPQLQuery createQuery(Predicate... predicate);
}
