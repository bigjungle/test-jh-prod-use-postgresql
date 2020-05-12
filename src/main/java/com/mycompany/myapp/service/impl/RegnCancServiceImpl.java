package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RegnCancService;
import com.mycompany.myapp.domain.RegnCanc;
import com.mycompany.myapp.repository.RegnCancRepository;
import com.mycompany.myapp.service.dto.RegnCancDTO;
import com.mycompany.myapp.service.mapper.RegnCancMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RegnCanc}.
 */
@Service
@Transactional
public class RegnCancServiceImpl implements RegnCancService {

    private final Logger log = LoggerFactory.getLogger(RegnCancServiceImpl.class);

    private final RegnCancRepository regnCancRepository;

    private final RegnCancMapper regnCancMapper;

    public RegnCancServiceImpl(RegnCancRepository regnCancRepository, RegnCancMapper regnCancMapper) {
        this.regnCancRepository = regnCancRepository;
        this.regnCancMapper = regnCancMapper;
    }

    /**
     * Save a regnCanc.
     *
     * @param regnCancDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegnCancDTO save(RegnCancDTO regnCancDTO) {
        log.debug("Request to save RegnCanc : {}", regnCancDTO);
        RegnCanc regnCanc = regnCancMapper.toEntity(regnCancDTO);
        regnCanc = regnCancRepository.save(regnCanc);
        return regnCancMapper.toDto(regnCanc);
    }

    /**
     * Get all the regnCancs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegnCancDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegnCancs");
        return regnCancRepository.findAll(pageable)
            .map(regnCancMapper::toDto);
    }

    /**
     * Get one regnCanc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegnCancDTO> findOne(Long id) {
        log.debug("Request to get RegnCanc : {}", id);
        return regnCancRepository.findById(id)
            .map(regnCancMapper::toDto);
    }

    /**
     * Delete the regnCanc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegnCanc : {}", id);
        regnCancRepository.deleteById(id);
    }
}
