package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;

public interface GeneralDomainService<T> {
    T save(T t) throws RestApiException;

    T modify(T t);

    void delete(T t);
}
