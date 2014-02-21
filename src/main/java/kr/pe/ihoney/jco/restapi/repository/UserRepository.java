package kr.pe.ihoney.jco.restapi.repository;

import kr.pe.ihoney.jco.restapi.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {

    User findByEmail(String email);

}
