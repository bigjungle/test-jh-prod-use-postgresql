package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.RegnRevoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.RegnRevoDTO;
import com.mycompany.myapp.service.dto.RegnRevoCriteria;
import com.mycompany.myapp.service.RegnRevoQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.RegnRevo}.
 */
@RestController
@RequestMapping("/api")
public class RegnRevoResource {

    private final Logger log = LoggerFactory.getLogger(RegnRevoResource.class);

    private static final String ENTITY_NAME = "regnRevo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegnRevoService regnRevoService;

    private final RegnRevoQueryService regnRevoQueryService;

    public RegnRevoResource(RegnRevoService regnRevoService, RegnRevoQueryService regnRevoQueryService) {
        this.regnRevoService = regnRevoService;
        this.regnRevoQueryService = regnRevoQueryService;
    }

    /**
     * {@code POST  /regn-revos} : Create a new regnRevo.
     *
     * @param regnRevoDTO the regnRevoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regnRevoDTO, or with status {@code 400 (Bad Request)} if the regnRevo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regn-revos")
    public ResponseEntity<RegnRevoDTO> createRegnRevo(@Valid @RequestBody RegnRevoDTO regnRevoDTO) throws URISyntaxException {
        log.debug("REST request to save RegnRevo : {}", regnRevoDTO);
        if (regnRevoDTO.getId() != null) {
            throw new BadRequestAlertException("A new regnRevo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegnRevoDTO result = regnRevoService.save(regnRevoDTO);
        return ResponseEntity.created(new URI("/api/regn-revos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regn-revos} : Updates an existing regnRevo.
     *
     * @param regnRevoDTO the regnRevoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regnRevoDTO,
     * or with status {@code 400 (Bad Request)} if the regnRevoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regnRevoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regn-revos")
    public ResponseEntity<RegnRevoDTO> updateRegnRevo(@Valid @RequestBody RegnRevoDTO regnRevoDTO) throws URISyntaxException {
        log.debug("REST request to update RegnRevo : {}", regnRevoDTO);
        if (regnRevoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegnRevoDTO result = regnRevoService.save(regnRevoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regnRevoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regn-revos} : get all the regnRevos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regnRevos in body.
     */
    @GetMapping("/regn-revos")
    public ResponseEntity<List<RegnRevoDTO>> getAllRegnRevos(RegnRevoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RegnRevos by criteria: {}", criteria);
        Page<RegnRevoDTO> page = regnRevoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regn-revos/count} : count all the regnRevos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regn-revos/count")
    public ResponseEntity<Long> countRegnRevos(RegnRevoCriteria criteria) {
        log.debug("REST request to count RegnRevos by criteria: {}", criteria);
        return ResponseEntity.ok().body(regnRevoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regn-revos/:id} : get the "id" regnRevo.
     *
     * @param id the id of the regnRevoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regnRevoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regn-revos/{id}")
    public ResponseEntity<RegnRevoDTO> getRegnRevo(@PathVariable Long id) {
        log.debug("REST request to get RegnRevo : {}", id);
        Optional<RegnRevoDTO> regnRevoDTO = regnRevoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regnRevoDTO);
    }

    /**
     * {@code DELETE  /regn-revos/:id} : delete the "id" regnRevo.
     *
     * @param id the id of the regnRevoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regn-revos/{id}")
    public ResponseEntity<Void> deleteRegnRevo(@PathVariable Long id) {
        log.debug("REST request to delete RegnRevo : {}", id);
        regnRevoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
