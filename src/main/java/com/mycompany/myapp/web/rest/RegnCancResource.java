package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.RegnCancService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.RegnCancDTO;
import com.mycompany.myapp.service.dto.RegnCancCriteria;
import com.mycompany.myapp.service.RegnCancQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.RegnCanc}.
 */
@RestController
@RequestMapping("/api")
public class RegnCancResource {

    private final Logger log = LoggerFactory.getLogger(RegnCancResource.class);

    private static final String ENTITY_NAME = "regnCanc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegnCancService regnCancService;

    private final RegnCancQueryService regnCancQueryService;

    public RegnCancResource(RegnCancService regnCancService, RegnCancQueryService regnCancQueryService) {
        this.regnCancService = regnCancService;
        this.regnCancQueryService = regnCancQueryService;
    }

    /**
     * {@code POST  /regn-cancs} : Create a new regnCanc.
     *
     * @param regnCancDTO the regnCancDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regnCancDTO, or with status {@code 400 (Bad Request)} if the regnCanc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regn-cancs")
    public ResponseEntity<RegnCancDTO> createRegnCanc(@Valid @RequestBody RegnCancDTO regnCancDTO) throws URISyntaxException {
        log.debug("REST request to save RegnCanc : {}", regnCancDTO);
        if (regnCancDTO.getId() != null) {
            throw new BadRequestAlertException("A new regnCanc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegnCancDTO result = regnCancService.save(regnCancDTO);
        return ResponseEntity.created(new URI("/api/regn-cancs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regn-cancs} : Updates an existing regnCanc.
     *
     * @param regnCancDTO the regnCancDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regnCancDTO,
     * or with status {@code 400 (Bad Request)} if the regnCancDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regnCancDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regn-cancs")
    public ResponseEntity<RegnCancDTO> updateRegnCanc(@Valid @RequestBody RegnCancDTO regnCancDTO) throws URISyntaxException {
        log.debug("REST request to update RegnCanc : {}", regnCancDTO);
        if (regnCancDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegnCancDTO result = regnCancService.save(regnCancDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regnCancDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regn-cancs} : get all the regnCancs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regnCancs in body.
     */
    @GetMapping("/regn-cancs")
    public ResponseEntity<List<RegnCancDTO>> getAllRegnCancs(RegnCancCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegnCancs by criteria: {}", criteria);
        Page<RegnCancDTO> page = regnCancQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regn-cancs/count} : count all the regnCancs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regn-cancs/count")
    public ResponseEntity<Long> countRegnCancs(RegnCancCriteria criteria) {
        log.debug("REST request to count RegnCancs by criteria: {}", criteria);
        return ResponseEntity.ok().body(regnCancQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regn-cancs/:id} : get the "id" regnCanc.
     *
     * @param id the id of the regnCancDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regnCancDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regn-cancs/{id}")
    public ResponseEntity<RegnCancDTO> getRegnCanc(@PathVariable Long id) {
        log.debug("REST request to get RegnCanc : {}", id);
        Optional<RegnCancDTO> regnCancDTO = regnCancService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regnCancDTO);
    }

    /**
     * {@code DELETE  /regn-cancs/:id} : delete the "id" regnCanc.
     *
     * @param id the id of the regnCancDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regn-cancs/{id}")
    public ResponseEntity<Void> deleteRegnCanc(@PathVariable Long id) {
        log.debug("REST request to delete RegnCanc : {}", id);
        regnCancService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
