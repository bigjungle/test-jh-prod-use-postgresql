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

import com.mycompany.myapp.domain.RegnRevo;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.RegnRevoRepository;
import com.mycompany.myapp.service.dto.RegnRevoCriteria;
import com.mycompany.myapp.service.dto.RegnRevoDTO;
import com.mycompany.myapp.service.mapper.RegnRevoMapper;

/**
 * Service for executing complex queries for {@link RegnRevo} entities in the database.
 * The main input is a {@link RegnRevoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegnRevoDTO} or a {@link Page} of {@link RegnRevoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegnRevoQueryService extends QueryService<RegnRevo> {

    private final Logger log = LoggerFactory.getLogger(RegnRevoQueryService.class);

    private final RegnRevoRepository regnRevoRepository;

    private final RegnRevoMapper regnRevoMapper;

    public RegnRevoQueryService(RegnRevoRepository regnRevoRepository, RegnRevoMapper regnRevoMapper) {
        this.regnRevoRepository = regnRevoRepository;
        this.regnRevoMapper = regnRevoMapper;
    }

    /**
     * Return a {@link List} of {@link RegnRevoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegnRevoDTO> findByCriteria(RegnRevoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegnRevo> specification = createSpecification(criteria);
        return regnRevoMapper.toDto(regnRevoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegnRevoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegnRevoDTO> findByCriteria(RegnRevoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegnRevo> specification = createSpecification(criteria);
        return regnRevoRepository.findAll(specification, page)
            .map(regnRevoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegnRevoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegnRevo> specification = createSpecification(criteria);
        return regnRevoRepository.count(specification);
    }

    /**
     * Function to convert {@link RegnRevoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegnRevo> createSpecification(RegnRevoCriteria criteria) {
        Specification<RegnRevo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegnRevo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RegnRevo_.name));
            }
            if (criteria.getDescString() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescString(), RegnRevo_.descString));
            }
            if (criteria.getOrgInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgInfo(), RegnRevo_.orgInfo));
            }
            if (criteria.getRevokeTimeSpan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRevokeTimeSpan(), RegnRevo_.revokeTimeSpan));
            }
            if (criteria.getRevokeStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRevokeStart(), RegnRevo_.revokeStart));
            }
            if (criteria.getRevokeOver() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRevokeOver(), RegnRevo_.revokeOver));
            }
            if (criteria.getPunishOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPunishOrg(), RegnRevo_.punishOrg));
            }
            if (criteria.getPunishTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPunishTime(), RegnRevo_.punishTime));
            }
            if (criteria.getFacts() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFacts(), RegnRevo_.facts));
            }
            if (criteria.getAutoProcess() != null) {
                specification = specification.and(buildSpecification(criteria.getAutoProcess(), RegnRevo_.autoProcess));
            }
            if (criteria.getRevokeProof() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRevokeProof(), RegnRevo_.revokeProof));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), RegnRevo_.remarks));
            }
            if (criteria.getPunishPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPunishPersonId(),
                    root -> root.join(RegnRevo_.punishPerson, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
