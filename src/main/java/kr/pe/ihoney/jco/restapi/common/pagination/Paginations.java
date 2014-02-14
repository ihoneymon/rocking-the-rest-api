package kr.pe.ihoney.jco.restapi.common.pagination;

import java.util.Collections;
import java.util.List;

import kr.pe.ihoney.jco.restapi.common.mapper.ExtensibleModelMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Paginations {
    private final static ExtensibleModelMapper modelMapper = new ExtensibleModelMapper();

    public static Pagination<Object> empty(Pageable pageable) {
        return new Pagination<Object>(Collections.emptyList(), pageable.getPageNumber(),
                pageable.getPageSize(), pageable.getSort(), 0);
    }

    public static <D> Pagination<D> pagination(List<D> contents, Pageable pageable) {
        return new Pagination<D>(contents, pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort(), contents.size());
    }

    public static <D> Pagination<D> pagination(Page<D> page) {
        return new Pagination<D>(page.getContent(), page.getNumber(), page.getSize(),
                page.getSort(), page.getTotalElements());
    }

    public static <D> Pagination<D> transform(Page<?> page, Class<D> destinationType) {
        List<D> content = null;
        if (page.getContent().isEmpty()) {
            content = Collections.emptyList();
        } else {
            content = modelMapper.map(page.getContent(), destinationType);
        }

        return new Pagination<D>(content, page.getNumber(), page.getSize(), page.getSort(),
                page.getTotalElements());
    }
}
