package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RegnRevoService;
import com.mycompany.myapp.domain.RegnRevo;
import com.mycompany.myapp.repository.RegnRevoRepository;
import com.mycompany.myapp.service.dto.RegnRevoDTO;
import com.mycompany.myapp.service.mapper.RegnRevoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RegnRevo}.
 */
@Service
@Transactional
public class RegnRevoServiceImpl implements RegnRevoService {

    private final Logger log = LoggerFactory.getLogger(RegnRevoServiceImpl.class);

    private final RegnRevoRepository regnRevoRepository;

    private final RegnRevoMapper regnRevoMapper;

    public RegnRevoServiceImpl(RegnRevoRepository regnRevoRepository, RegnRevoMapper regnRevoMapper) {
        this.regnRevoRepository = regnRevoRepository;
        this.regnRevoMapper = regnRevoMapper;
    }

    /**
     * Save a regnRevo.
     *
     * @param regnRevoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegnRevoDTO save(RegnRevoDTO regnRevoDTO) {
        log.debug("Request to save RegnRevo : {}", regnRevoDTO);
        RegnRevo regnRevo = regnRevoMapper.toEntity(regnRevoDTO);
        regnRevo = regnRevoRepository.save(regnRevo);
        return regnRevoMapper.toDto(regnRevo);
    }

    /**
     * Get all the regnRevos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegnRevoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegnRevos");
        return regnRevoRepository.findAll(pageable)
            .map(regnRevoMapper::toDto);
    }

    /**
     * Get one regnRevo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegnRevoDTO> findOne(Long id) {
        log.debug("Request to get RegnRevo : {}", id);
        return regnRevoRepository.findById(id)
            .map(regnRevoMapper::toDto);
    }

    /**
     * Delete the regnRevo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegnRevo : {}", id);
        regnRevoRepository.deleteById(id);
    }
}
