package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RegnCancDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.RegnCanc}.
 */
public interface RegnCancService {

    /**
     * Save a regnCanc.
     *
     * @param regnCancDTO the entity to save.
     * @return the persisted entity.
     */
    RegnCancDTO save(RegnCancDTO regnCancDTO);

    /**
     * Get all the regnCancs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegnCancDTO> findAll(Pageable pageable);

    /**
     * Get the "id" regnCanc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegnCancDTO> findOne(Long id);

    /**
     * Delete the "id" regnCanc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
