package kr.pe.ihoney.jco.restapi.repository;

import kr.pe.ihoney.jco.restapi.domain.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:04
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, QueryDslPredicateExecutor<Group> {
    Group findByName(String name);
}
