package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RegnRevoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.RegnRevo}.
 */
public interface RegnRevoService {

    /**
     * Save a regnRevo.
     *
     * @param regnRevoDTO the entity to save.
     * @return the persisted entity.
     */
    RegnRevoDTO save(RegnRevoDTO regnRevoDTO);

    /**
     * Get all the regnRevos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegnRevoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" regnRevo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegnRevoDTO> findOne(Long id);

    /**
     * Delete the "id" regnRevo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
