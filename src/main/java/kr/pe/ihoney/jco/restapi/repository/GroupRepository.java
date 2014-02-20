package kr.pe.ihoney.jco.restapi.repository;

import kr.pe.ihoney.jco.restapi.domain.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, QueryDslPredicateExecutor<Group> {
    Group findByName(String name);
}
