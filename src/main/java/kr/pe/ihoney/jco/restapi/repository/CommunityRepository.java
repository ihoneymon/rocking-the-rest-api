package kr.pe.ihoney.jco.restapi.repository;

import kr.pe.ihoney.jco.restapi.domain.Community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Community 저장소
 * @author ihoneymon
 *
 */
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, QueryDslPredicateExecutor<Community> {
    Community findByName(String name);
}
