package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.RegnCanc;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.RegnCancRepository;
import com.mycompany.myapp.service.dto.RegnCancCriteria;
import com.mycompany.myapp.service.dto.RegnCancDTO;
import com.mycompany.myapp.service.mapper.RegnCancMapper;

/**
 * Service for executing complex queries for {@link RegnCanc} entities in the database.
 * The main input is a {@link RegnCancCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegnCancDTO} or a {@link Page} of {@link RegnCancDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegnCancQueryService extends QueryService<RegnCanc> {

    private final Logger log = LoggerFactory.getLogger(RegnCancQueryService.class);

    private final RegnCancRepository regnCancRepository;

    private final RegnCancMapper regnCancMapper;

    public RegnCancQueryService(RegnCancRepository regnCancRepository, RegnCancMapper regnCancMapper) {
        this.regnCancRepository = regnCancRepository;
        this.regnCancMapper = regnCancMapper;
    }

    /**
     * Return a {@link List} of {@link RegnCancDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegnCancDTO> findByCriteria(RegnCancCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegnCanc> specification = createSpecification(criteria);
        return regnCancMapper.toDto(regnCancRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegnCancDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegnCancDTO> findByCriteria(RegnCancCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegnCanc> specification = createSpecification(criteria);
        return regnCancRepository.findAll(specification, page)
            .map(regnCancMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegnCancCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegnCanc> specification = createSpecification(criteria);
        return regnCancRepository.count(specification);
    }

    /**
     * Function to convert {@link RegnCancCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegnCanc> createSpecification(RegnCancCriteria criteria) {
        Specification<RegnCanc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegnCanc_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RegnCanc_.name));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), RegnCanc_.descString));
            }
            if (criteria.getOrgInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgInfo(), RegnCanc_.orgInfo));
            }
            if (criteria.getCancellationWay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCancellationWay(), RegnCanc_.cancellationWay));
            }
            if (criteria.getCancellationReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCancellationReason(), RegnCanc_.cancellationReason));
            }
            if (criteria.getCancellationTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCancellationTime(), RegnCanc_.cancellationTime));
            }
            if (criteria.getCancellationProof() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCancellationProof(), RegnCanc_.cancellationProof));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), RegnCanc_.remarks));
            }
            if (criteria.getOwnerById() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerById(),
                    root -> root.join(RegnCanc_.ownerBy, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
