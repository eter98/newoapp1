package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.CuentaAsociada;
import io.github.jhipster.application.repository.CuentaAsociadaRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing CuentaAsociada.
 */
@RestController
@RequestMapping("/api")
public class CuentaAsociadaResource {

    private final Logger log = LoggerFactory.getLogger(CuentaAsociadaResource.class);

    private static final String ENTITY_NAME = "cuentaAsociada";

    private final CuentaAsociadaRepository cuentaAsociadaRepository;

    public CuentaAsociadaResource(CuentaAsociadaRepository cuentaAsociadaRepository) {
        this.cuentaAsociadaRepository = cuentaAsociadaRepository;
    }

    /**
     * POST  /cuenta-asociadas : Create a new cuentaAsociada.
     *
     * @param cuentaAsociada the cuentaAsociada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cuentaAsociada, or with status 400 (Bad Request) if the cuentaAsociada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cuenta-asociadas")
    public ResponseEntity<CuentaAsociada> createCuentaAsociada(@RequestBody CuentaAsociada cuentaAsociada) throws URISyntaxException {
        log.debug("REST request to save CuentaAsociada : {}", cuentaAsociada);
        if (cuentaAsociada.getId() != null) {
            throw new BadRequestAlertException("A new cuentaAsociada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CuentaAsociada result = cuentaAsociadaRepository.save(cuentaAsociada);
        return ResponseEntity.created(new URI("/api/cuenta-asociadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cuenta-asociadas : Updates an existing cuentaAsociada.
     *
     * @param cuentaAsociada the cuentaAsociada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cuentaAsociada,
     * or with status 400 (Bad Request) if the cuentaAsociada is not valid,
     * or with status 500 (Internal Server Error) if the cuentaAsociada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cuenta-asociadas")
    public ResponseEntity<CuentaAsociada> updateCuentaAsociada(@RequestBody CuentaAsociada cuentaAsociada) throws URISyntaxException {
        log.debug("REST request to update CuentaAsociada : {}", cuentaAsociada);
        if (cuentaAsociada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CuentaAsociada result = cuentaAsociadaRepository.save(cuentaAsociada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cuentaAsociada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cuenta-asociadas : get all the cuentaAsociadas.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of cuentaAsociadas in body
     */
    @GetMapping("/cuenta-asociadas")
    public List<CuentaAsociada> getAllCuentaAsociadas(@RequestParam(required = false) String filter) {
        if ("facturacion-is-null".equals(filter)) {
            log.debug("REST request to get all CuentaAsociadas where facturacion is null");
            return StreamSupport
                .stream(cuentaAsociadaRepository.findAll().spliterator(), false)
                .filter(cuentaAsociada -> cuentaAsociada.getFacturacion() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all CuentaAsociadas");
        return cuentaAsociadaRepository.findAll();
    }

    /**
     * GET  /cuenta-asociadas/:id : get the "id" cuentaAsociada.
     *
     * @param id the id of the cuentaAsociada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cuentaAsociada, or with status 404 (Not Found)
     */
    @GetMapping("/cuenta-asociadas/{id}")
    public ResponseEntity<CuentaAsociada> getCuentaAsociada(@PathVariable Long id) {
        log.debug("REST request to get CuentaAsociada : {}", id);
        Optional<CuentaAsociada> cuentaAsociada = cuentaAsociadaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cuentaAsociada);
    }

    /**
     * DELETE  /cuenta-asociadas/:id : delete the "id" cuentaAsociada.
     *
     * @param id the id of the cuentaAsociada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cuenta-asociadas/{id}")
    public ResponseEntity<Void> deleteCuentaAsociada(@PathVariable Long id) {
        log.debug("REST request to delete CuentaAsociada : {}", id);
        cuentaAsociadaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}