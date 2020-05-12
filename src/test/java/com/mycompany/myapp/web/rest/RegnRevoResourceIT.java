package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestProdDatabaseApp;
import com.mycompany.myapp.domain.RegnRevo;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.RegnRevoRepository;
import com.mycompany.myapp.service.RegnRevoService;
import com.mycompany.myapp.service.dto.RegnRevoDTO;
import com.mycompany.myapp.service.mapper.RegnRevoMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.RegnRevoCriteria;
import com.mycompany.myapp.service.RegnRevoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegnRevoResource} REST controller.
 */
@SpringBootTest(classes = TestProdDatabaseApp.class)
public class RegnRevoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ORG_INFO = "BBBBBBBBBB";

    private static final Integer DEFAULT_REVOKE_TIME_SPAN = 1;
    private static final Integer UPDATED_REVOKE_TIME_SPAN = 2;
    private static final Integer SMALLER_REVOKE_TIME_SPAN = 1 - 1;

    private static final LocalDate DEFAULT_REVOKE_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVOKE_START = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REVOKE_START = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REVOKE_OVER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVOKE_OVER = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REVOKE_OVER = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PUNISH_ORG = "AAAAAAAAAA";
    private static final String UPDATED_PUNISH_ORG = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUNISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUNISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FACTS = "AAAAAAAAAA";
    private static final String UPDATED_FACTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTO_PROCESS = false;
    private static final Boolean UPDATED_AUTO_PROCESS = true;

    private static final String DEFAULT_REVOKE_PROOF = "AAAAAAAAAA";
    private static final String UPDATED_REVOKE_PROOF = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RegnRevoRepository regnRevoRepository;

    @Autowired
    private RegnRevoMapper regnRevoMapper;

    @Autowired
    private RegnRevoService regnRevoService;

    @Autowired
    private RegnRevoQueryService regnRevoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRegnRevoMockMvc;

    private RegnRevo regnRevo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegnRevoResource regnRevoResource = new RegnRevoResource(regnRevoService, regnRevoQueryService);
        this.restRegnRevoMockMvc = MockMvcBuilders.standaloneSetup(regnRevoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegnRevo createEntity(EntityManager em) {
        RegnRevo regnRevo = new RegnRevo()
            .name(DEFAULT_NAME)
            .descString(DEFAULT_DESC_STRING)
            .orgInfo(DEFAULT_ORG_INFO)
            .revokeTimeSpan(DEFAULT_REVOKE_TIME_SPAN)
            .revokeStart(DEFAULT_REVOKE_START)
            .revokeOver(DEFAULT_REVOKE_OVER)
            .punishOrg(DEFAULT_PUNISH_ORG)
            .punishTime(DEFAULT_PUNISH_TIME)
            .facts(DEFAULT_FACTS)
            .autoProcess(DEFAULT_AUTO_PROCESS)
            .revokeProof(DEFAULT_REVOKE_PROOF)
            .remarks(DEFAULT_REMARKS);
        return regnRevo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegnRevo createUpdatedEntity(EntityManager em) {
        RegnRevo regnRevo = new RegnRevo()
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .revokeTimeSpan(UPDATED_REVOKE_TIME_SPAN)
            .revokeStart(UPDATED_REVOKE_START)
            .revokeOver(UPDATED_REVOKE_OVER)
            .punishOrg(UPDATED_PUNISH_ORG)
            .punishTime(UPDATED_PUNISH_TIME)
            .facts(UPDATED_FACTS)
            .autoProcess(UPDATED_AUTO_PROCESS)
            .revokeProof(UPDATED_REVOKE_PROOF)
            .remarks(UPDATED_REMARKS);
        return regnRevo;
    }

    @BeforeEach
    public void initTest() {
        regnRevo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegnRevo() throws Exception {
        int databaseSizeBeforeCreate = regnRevoRepository.findAll().size();

        // Create the RegnRevo
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);
        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isCreated());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeCreate + 1);
        RegnRevo testRegnRevo = regnRevoList.get(regnRevoList.size() - 1);
        assertThat(testRegnRevo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegnRevo.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testRegnRevo.getOrgInfo()).isEqualTo(DEFAULT_ORG_INFO);
        assertThat(testRegnRevo.getRevokeTimeSpan()).isEqualTo(DEFAULT_REVOKE_TIME_SPAN);
        assertThat(testRegnRevo.getRevokeStart()).isEqualTo(DEFAULT_REVOKE_START);
        assertThat(testRegnRevo.getRevokeOver()).isEqualTo(DEFAULT_REVOKE_OVER);
        assertThat(testRegnRevo.getPunishOrg()).isEqualTo(DEFAULT_PUNISH_ORG);
        assertThat(testRegnRevo.getPunishTime()).isEqualTo(DEFAULT_PUNISH_TIME);
        assertThat(testRegnRevo.getFacts()).isEqualTo(DEFAULT_FACTS);
        assertThat(testRegnRevo.isAutoProcess()).isEqualTo(DEFAULT_AUTO_PROCESS);
        assertThat(testRegnRevo.getRevokeProof()).isEqualTo(DEFAULT_REVOKE_PROOF);
        assertThat(testRegnRevo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createRegnRevoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regnRevoRepository.findAll().size();

        // Create the RegnRevo with an existing ID
        regnRevo.setId(1L);
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setName(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrgInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setOrgInfo(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRevokeTimeSpanIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setRevokeTimeSpan(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRevokeStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setRevokeStart(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRevokeOverIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnRevoRepository.findAll().size();
        // set the field null
        regnRevo.setRevokeOver(null);

        // Create the RegnRevo, which fails.
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        restRegnRevoMockMvc.perform(post("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegnRevos() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList
        restRegnRevoMockMvc.perform(get("/api/regn-revos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regnRevo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].orgInfo").value(hasItem(DEFAULT_ORG_INFO)))
            .andExpect(jsonPath("$.[*].revokeTimeSpan").value(hasItem(DEFAULT_REVOKE_TIME_SPAN)))
            .andExpect(jsonPath("$.[*].revokeStart").value(hasItem(DEFAULT_REVOKE_START.toString())))
            .andExpect(jsonPath("$.[*].revokeOver").value(hasItem(DEFAULT_REVOKE_OVER.toString())))
            .andExpect(jsonPath("$.[*].punishOrg").value(hasItem(DEFAULT_PUNISH_ORG)))
            .andExpect(jsonPath("$.[*].punishTime").value(hasItem(DEFAULT_PUNISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].facts").value(hasItem(DEFAULT_FACTS)))
            .andExpect(jsonPath("$.[*].autoProcess").value(hasItem(DEFAULT_AUTO_PROCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].revokeProof").value(hasItem(DEFAULT_REVOKE_PROOF)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getRegnRevo() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get the regnRevo
        restRegnRevoMockMvc.perform(get("/api/regn-revos/{id}", regnRevo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regnRevo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING))
            .andExpect(jsonPath("$.orgInfo").value(DEFAULT_ORG_INFO))
            .andExpect(jsonPath("$.revokeTimeSpan").value(DEFAULT_REVOKE_TIME_SPAN))
            .andExpect(jsonPath("$.revokeStart").value(DEFAULT_REVOKE_START.toString()))
            .andExpect(jsonPath("$.revokeOver").value(DEFAULT_REVOKE_OVER.toString()))
            .andExpect(jsonPath("$.punishOrg").value(DEFAULT_PUNISH_ORG))
            .andExpect(jsonPath("$.punishTime").value(DEFAULT_PUNISH_TIME.toString()))
            .andExpect(jsonPath("$.facts").value(DEFAULT_FACTS))
            .andExpect(jsonPath("$.autoProcess").value(DEFAULT_AUTO_PROCESS.booleanValue()))
            .andExpect(jsonPath("$.revokeProof").value(DEFAULT_REVOKE_PROOF))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getRegnRevosByIdFiltering() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        Long id = regnRevo.getId();

        defaultRegnRevoShouldBeFound("id.equals=" + id);
        defaultRegnRevoShouldNotBeFound("id.notEquals=" + id);

        defaultRegnRevoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegnRevoShouldNotBeFound("id.greaterThan=" + id);

        defaultRegnRevoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegnRevoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where name equals to DEFAULT_NAME
        defaultRegnRevoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regnRevoList where name equals to UPDATED_NAME
        defaultRegnRevoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where name not equals to DEFAULT_NAME
        defaultRegnRevoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the regnRevoList where name not equals to UPDATED_NAME
        defaultRegnRevoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegnRevoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regnRevoList where name equals to UPDATED_NAME
        defaultRegnRevoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where name is not null
        defaultRegnRevoShouldBeFound("name.specified=true");

        // Get all the regnRevoList where name is null
        defaultRegnRevoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByNameContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where name contains DEFAULT_NAME
        defaultRegnRevoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the regnRevoList where name contains UPDATED_NAME
        defaultRegnRevoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where name does not contain DEFAULT_NAME
        defaultRegnRevoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the regnRevoList where name does not contain UPDATED_NAME
        defaultRegnRevoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where descString equals to DEFAULT_DESC_STRING
        defaultRegnRevoShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the regnRevoList where descString equals to UPDATED_DESC_STRING
        defaultRegnRevoShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByDescStringIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where descString not equals to DEFAULT_DESC_STRING
        defaultRegnRevoShouldNotBeFound("descString.notEquals=" + DEFAULT_DESC_STRING);

        // Get all the regnRevoList where descString not equals to UPDATED_DESC_STRING
        defaultRegnRevoShouldBeFound("descString.notEquals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultRegnRevoShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the regnRevoList where descString equals to UPDATED_DESC_STRING
        defaultRegnRevoShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where descString is not null
        defaultRegnRevoShouldBeFound("descString.specified=true");

        // Get all the regnRevoList where descString is null
        defaultRegnRevoShouldNotBeFound("descString.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByDescStringContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where descString contains DEFAULT_DESC_STRING
        defaultRegnRevoShouldBeFound("descString.contains=" + DEFAULT_DESC_STRING);

        // Get all the regnRevoList where descString contains UPDATED_DESC_STRING
        defaultRegnRevoShouldNotBeFound("descString.contains=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByDescStringNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where descString does not contain DEFAULT_DESC_STRING
        defaultRegnRevoShouldNotBeFound("descString.doesNotContain=" + DEFAULT_DESC_STRING);

        // Get all the regnRevoList where descString does not contain UPDATED_DESC_STRING
        defaultRegnRevoShouldBeFound("descString.doesNotContain=" + UPDATED_DESC_STRING);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByOrgInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where orgInfo equals to DEFAULT_ORG_INFO
        defaultRegnRevoShouldBeFound("orgInfo.equals=" + DEFAULT_ORG_INFO);

        // Get all the regnRevoList where orgInfo equals to UPDATED_ORG_INFO
        defaultRegnRevoShouldNotBeFound("orgInfo.equals=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByOrgInfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where orgInfo not equals to DEFAULT_ORG_INFO
        defaultRegnRevoShouldNotBeFound("orgInfo.notEquals=" + DEFAULT_ORG_INFO);

        // Get all the regnRevoList where orgInfo not equals to UPDATED_ORG_INFO
        defaultRegnRevoShouldBeFound("orgInfo.notEquals=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByOrgInfoIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where orgInfo in DEFAULT_ORG_INFO or UPDATED_ORG_INFO
        defaultRegnRevoShouldBeFound("orgInfo.in=" + DEFAULT_ORG_INFO + "," + UPDATED_ORG_INFO);

        // Get all the regnRevoList where orgInfo equals to UPDATED_ORG_INFO
        defaultRegnRevoShouldNotBeFound("orgInfo.in=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByOrgInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where orgInfo is not null
        defaultRegnRevoShouldBeFound("orgInfo.specified=true");

        // Get all the regnRevoList where orgInfo is null
        defaultRegnRevoShouldNotBeFound("orgInfo.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByOrgInfoContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where orgInfo contains DEFAULT_ORG_INFO
        defaultRegnRevoShouldBeFound("orgInfo.contains=" + DEFAULT_ORG_INFO);

        // Get all the regnRevoList where orgInfo contains UPDATED_ORG_INFO
        defaultRegnRevoShouldNotBeFound("orgInfo.contains=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByOrgInfoNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where orgInfo does not contain DEFAULT_ORG_INFO
        defaultRegnRevoShouldNotBeFound("orgInfo.doesNotContain=" + DEFAULT_ORG_INFO);

        // Get all the regnRevoList where orgInfo does not contain UPDATED_ORG_INFO
        defaultRegnRevoShouldBeFound("orgInfo.doesNotContain=" + UPDATED_ORG_INFO);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan equals to DEFAULT_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.equals=" + DEFAULT_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan equals to UPDATED_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.equals=" + UPDATED_REVOKE_TIME_SPAN);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan not equals to DEFAULT_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.notEquals=" + DEFAULT_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan not equals to UPDATED_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.notEquals=" + UPDATED_REVOKE_TIME_SPAN);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan in DEFAULT_REVOKE_TIME_SPAN or UPDATED_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.in=" + DEFAULT_REVOKE_TIME_SPAN + "," + UPDATED_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan equals to UPDATED_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.in=" + UPDATED_REVOKE_TIME_SPAN);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan is not null
        defaultRegnRevoShouldBeFound("revokeTimeSpan.specified=true");

        // Get all the regnRevoList where revokeTimeSpan is null
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan is greater than or equal to DEFAULT_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.greaterThanOrEqual=" + DEFAULT_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan is greater than or equal to UPDATED_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.greaterThanOrEqual=" + UPDATED_REVOKE_TIME_SPAN);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan is less than or equal to DEFAULT_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.lessThanOrEqual=" + DEFAULT_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan is less than or equal to SMALLER_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.lessThanOrEqual=" + SMALLER_REVOKE_TIME_SPAN);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsLessThanSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan is less than DEFAULT_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.lessThan=" + DEFAULT_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan is less than UPDATED_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.lessThan=" + UPDATED_REVOKE_TIME_SPAN);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeTimeSpanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeTimeSpan is greater than DEFAULT_REVOKE_TIME_SPAN
        defaultRegnRevoShouldNotBeFound("revokeTimeSpan.greaterThan=" + DEFAULT_REVOKE_TIME_SPAN);

        // Get all the regnRevoList where revokeTimeSpan is greater than SMALLER_REVOKE_TIME_SPAN
        defaultRegnRevoShouldBeFound("revokeTimeSpan.greaterThan=" + SMALLER_REVOKE_TIME_SPAN);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart equals to DEFAULT_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.equals=" + DEFAULT_REVOKE_START);

        // Get all the regnRevoList where revokeStart equals to UPDATED_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.equals=" + UPDATED_REVOKE_START);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart not equals to DEFAULT_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.notEquals=" + DEFAULT_REVOKE_START);

        // Get all the regnRevoList where revokeStart not equals to UPDATED_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.notEquals=" + UPDATED_REVOKE_START);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart in DEFAULT_REVOKE_START or UPDATED_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.in=" + DEFAULT_REVOKE_START + "," + UPDATED_REVOKE_START);

        // Get all the regnRevoList where revokeStart equals to UPDATED_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.in=" + UPDATED_REVOKE_START);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart is not null
        defaultRegnRevoShouldBeFound("revokeStart.specified=true");

        // Get all the regnRevoList where revokeStart is null
        defaultRegnRevoShouldNotBeFound("revokeStart.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart is greater than or equal to DEFAULT_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.greaterThanOrEqual=" + DEFAULT_REVOKE_START);

        // Get all the regnRevoList where revokeStart is greater than or equal to UPDATED_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.greaterThanOrEqual=" + UPDATED_REVOKE_START);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart is less than or equal to DEFAULT_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.lessThanOrEqual=" + DEFAULT_REVOKE_START);

        // Get all the regnRevoList where revokeStart is less than or equal to SMALLER_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.lessThanOrEqual=" + SMALLER_REVOKE_START);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsLessThanSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart is less than DEFAULT_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.lessThan=" + DEFAULT_REVOKE_START);

        // Get all the regnRevoList where revokeStart is less than UPDATED_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.lessThan=" + UPDATED_REVOKE_START);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeStart is greater than DEFAULT_REVOKE_START
        defaultRegnRevoShouldNotBeFound("revokeStart.greaterThan=" + DEFAULT_REVOKE_START);

        // Get all the regnRevoList where revokeStart is greater than SMALLER_REVOKE_START
        defaultRegnRevoShouldBeFound("revokeStart.greaterThan=" + SMALLER_REVOKE_START);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver equals to DEFAULT_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.equals=" + DEFAULT_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver equals to UPDATED_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.equals=" + UPDATED_REVOKE_OVER);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver not equals to DEFAULT_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.notEquals=" + DEFAULT_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver not equals to UPDATED_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.notEquals=" + UPDATED_REVOKE_OVER);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver in DEFAULT_REVOKE_OVER or UPDATED_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.in=" + DEFAULT_REVOKE_OVER + "," + UPDATED_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver equals to UPDATED_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.in=" + UPDATED_REVOKE_OVER);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver is not null
        defaultRegnRevoShouldBeFound("revokeOver.specified=true");

        // Get all the regnRevoList where revokeOver is null
        defaultRegnRevoShouldNotBeFound("revokeOver.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver is greater than or equal to DEFAULT_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.greaterThanOrEqual=" + DEFAULT_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver is greater than or equal to UPDATED_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.greaterThanOrEqual=" + UPDATED_REVOKE_OVER);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver is less than or equal to DEFAULT_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.lessThanOrEqual=" + DEFAULT_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver is less than or equal to SMALLER_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.lessThanOrEqual=" + SMALLER_REVOKE_OVER);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsLessThanSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver is less than DEFAULT_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.lessThan=" + DEFAULT_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver is less than UPDATED_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.lessThan=" + UPDATED_REVOKE_OVER);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeOverIsGreaterThanSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeOver is greater than DEFAULT_REVOKE_OVER
        defaultRegnRevoShouldNotBeFound("revokeOver.greaterThan=" + DEFAULT_REVOKE_OVER);

        // Get all the regnRevoList where revokeOver is greater than SMALLER_REVOKE_OVER
        defaultRegnRevoShouldBeFound("revokeOver.greaterThan=" + SMALLER_REVOKE_OVER);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByPunishOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishOrg equals to DEFAULT_PUNISH_ORG
        defaultRegnRevoShouldBeFound("punishOrg.equals=" + DEFAULT_PUNISH_ORG);

        // Get all the regnRevoList where punishOrg equals to UPDATED_PUNISH_ORG
        defaultRegnRevoShouldNotBeFound("punishOrg.equals=" + UPDATED_PUNISH_ORG);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishOrg not equals to DEFAULT_PUNISH_ORG
        defaultRegnRevoShouldNotBeFound("punishOrg.notEquals=" + DEFAULT_PUNISH_ORG);

        // Get all the regnRevoList where punishOrg not equals to UPDATED_PUNISH_ORG
        defaultRegnRevoShouldBeFound("punishOrg.notEquals=" + UPDATED_PUNISH_ORG);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishOrgIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishOrg in DEFAULT_PUNISH_ORG or UPDATED_PUNISH_ORG
        defaultRegnRevoShouldBeFound("punishOrg.in=" + DEFAULT_PUNISH_ORG + "," + UPDATED_PUNISH_ORG);

        // Get all the regnRevoList where punishOrg equals to UPDATED_PUNISH_ORG
        defaultRegnRevoShouldNotBeFound("punishOrg.in=" + UPDATED_PUNISH_ORG);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishOrg is not null
        defaultRegnRevoShouldBeFound("punishOrg.specified=true");

        // Get all the regnRevoList where punishOrg is null
        defaultRegnRevoShouldNotBeFound("punishOrg.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByPunishOrgContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishOrg contains DEFAULT_PUNISH_ORG
        defaultRegnRevoShouldBeFound("punishOrg.contains=" + DEFAULT_PUNISH_ORG);

        // Get all the regnRevoList where punishOrg contains UPDATED_PUNISH_ORG
        defaultRegnRevoShouldNotBeFound("punishOrg.contains=" + UPDATED_PUNISH_ORG);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishOrgNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishOrg does not contain DEFAULT_PUNISH_ORG
        defaultRegnRevoShouldNotBeFound("punishOrg.doesNotContain=" + DEFAULT_PUNISH_ORG);

        // Get all the regnRevoList where punishOrg does not contain UPDATED_PUNISH_ORG
        defaultRegnRevoShouldBeFound("punishOrg.doesNotContain=" + UPDATED_PUNISH_ORG);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByPunishTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishTime equals to DEFAULT_PUNISH_TIME
        defaultRegnRevoShouldBeFound("punishTime.equals=" + DEFAULT_PUNISH_TIME);

        // Get all the regnRevoList where punishTime equals to UPDATED_PUNISH_TIME
        defaultRegnRevoShouldNotBeFound("punishTime.equals=" + UPDATED_PUNISH_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishTime not equals to DEFAULT_PUNISH_TIME
        defaultRegnRevoShouldNotBeFound("punishTime.notEquals=" + DEFAULT_PUNISH_TIME);

        // Get all the regnRevoList where punishTime not equals to UPDATED_PUNISH_TIME
        defaultRegnRevoShouldBeFound("punishTime.notEquals=" + UPDATED_PUNISH_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishTimeIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishTime in DEFAULT_PUNISH_TIME or UPDATED_PUNISH_TIME
        defaultRegnRevoShouldBeFound("punishTime.in=" + DEFAULT_PUNISH_TIME + "," + UPDATED_PUNISH_TIME);

        // Get all the regnRevoList where punishTime equals to UPDATED_PUNISH_TIME
        defaultRegnRevoShouldNotBeFound("punishTime.in=" + UPDATED_PUNISH_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByPunishTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where punishTime is not null
        defaultRegnRevoShouldBeFound("punishTime.specified=true");

        // Get all the regnRevoList where punishTime is null
        defaultRegnRevoShouldNotBeFound("punishTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegnRevosByFactsIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where facts equals to DEFAULT_FACTS
        defaultRegnRevoShouldBeFound("facts.equals=" + DEFAULT_FACTS);

        // Get all the regnRevoList where facts equals to UPDATED_FACTS
        defaultRegnRevoShouldNotBeFound("facts.equals=" + UPDATED_FACTS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByFactsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where facts not equals to DEFAULT_FACTS
        defaultRegnRevoShouldNotBeFound("facts.notEquals=" + DEFAULT_FACTS);

        // Get all the regnRevoList where facts not equals to UPDATED_FACTS
        defaultRegnRevoShouldBeFound("facts.notEquals=" + UPDATED_FACTS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByFactsIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where facts in DEFAULT_FACTS or UPDATED_FACTS
        defaultRegnRevoShouldBeFound("facts.in=" + DEFAULT_FACTS + "," + UPDATED_FACTS);

        // Get all the regnRevoList where facts equals to UPDATED_FACTS
        defaultRegnRevoShouldNotBeFound("facts.in=" + UPDATED_FACTS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByFactsIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where facts is not null
        defaultRegnRevoShouldBeFound("facts.specified=true");

        // Get all the regnRevoList where facts is null
        defaultRegnRevoShouldNotBeFound("facts.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByFactsContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where facts contains DEFAULT_FACTS
        defaultRegnRevoShouldBeFound("facts.contains=" + DEFAULT_FACTS);

        // Get all the regnRevoList where facts contains UPDATED_FACTS
        defaultRegnRevoShouldNotBeFound("facts.contains=" + UPDATED_FACTS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByFactsNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where facts does not contain DEFAULT_FACTS
        defaultRegnRevoShouldNotBeFound("facts.doesNotContain=" + DEFAULT_FACTS);

        // Get all the regnRevoList where facts does not contain UPDATED_FACTS
        defaultRegnRevoShouldBeFound("facts.doesNotContain=" + UPDATED_FACTS);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByAutoProcessIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where autoProcess equals to DEFAULT_AUTO_PROCESS
        defaultRegnRevoShouldBeFound("autoProcess.equals=" + DEFAULT_AUTO_PROCESS);

        // Get all the regnRevoList where autoProcess equals to UPDATED_AUTO_PROCESS
        defaultRegnRevoShouldNotBeFound("autoProcess.equals=" + UPDATED_AUTO_PROCESS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByAutoProcessIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where autoProcess not equals to DEFAULT_AUTO_PROCESS
        defaultRegnRevoShouldNotBeFound("autoProcess.notEquals=" + DEFAULT_AUTO_PROCESS);

        // Get all the regnRevoList where autoProcess not equals to UPDATED_AUTO_PROCESS
        defaultRegnRevoShouldBeFound("autoProcess.notEquals=" + UPDATED_AUTO_PROCESS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByAutoProcessIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where autoProcess in DEFAULT_AUTO_PROCESS or UPDATED_AUTO_PROCESS
        defaultRegnRevoShouldBeFound("autoProcess.in=" + DEFAULT_AUTO_PROCESS + "," + UPDATED_AUTO_PROCESS);

        // Get all the regnRevoList where autoProcess equals to UPDATED_AUTO_PROCESS
        defaultRegnRevoShouldNotBeFound("autoProcess.in=" + UPDATED_AUTO_PROCESS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByAutoProcessIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where autoProcess is not null
        defaultRegnRevoShouldBeFound("autoProcess.specified=true");

        // Get all the regnRevoList where autoProcess is null
        defaultRegnRevoShouldNotBeFound("autoProcess.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeProofIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeProof equals to DEFAULT_REVOKE_PROOF
        defaultRegnRevoShouldBeFound("revokeProof.equals=" + DEFAULT_REVOKE_PROOF);

        // Get all the regnRevoList where revokeProof equals to UPDATED_REVOKE_PROOF
        defaultRegnRevoShouldNotBeFound("revokeProof.equals=" + UPDATED_REVOKE_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeProofIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeProof not equals to DEFAULT_REVOKE_PROOF
        defaultRegnRevoShouldNotBeFound("revokeProof.notEquals=" + DEFAULT_REVOKE_PROOF);

        // Get all the regnRevoList where revokeProof not equals to UPDATED_REVOKE_PROOF
        defaultRegnRevoShouldBeFound("revokeProof.notEquals=" + UPDATED_REVOKE_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeProofIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeProof in DEFAULT_REVOKE_PROOF or UPDATED_REVOKE_PROOF
        defaultRegnRevoShouldBeFound("revokeProof.in=" + DEFAULT_REVOKE_PROOF + "," + UPDATED_REVOKE_PROOF);

        // Get all the regnRevoList where revokeProof equals to UPDATED_REVOKE_PROOF
        defaultRegnRevoShouldNotBeFound("revokeProof.in=" + UPDATED_REVOKE_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeProofIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeProof is not null
        defaultRegnRevoShouldBeFound("revokeProof.specified=true");

        // Get all the regnRevoList where revokeProof is null
        defaultRegnRevoShouldNotBeFound("revokeProof.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByRevokeProofContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeProof contains DEFAULT_REVOKE_PROOF
        defaultRegnRevoShouldBeFound("revokeProof.contains=" + DEFAULT_REVOKE_PROOF);

        // Get all the regnRevoList where revokeProof contains UPDATED_REVOKE_PROOF
        defaultRegnRevoShouldNotBeFound("revokeProof.contains=" + UPDATED_REVOKE_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRevokeProofNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where revokeProof does not contain DEFAULT_REVOKE_PROOF
        defaultRegnRevoShouldNotBeFound("revokeProof.doesNotContain=" + DEFAULT_REVOKE_PROOF);

        // Get all the regnRevoList where revokeProof does not contain UPDATED_REVOKE_PROOF
        defaultRegnRevoShouldBeFound("revokeProof.doesNotContain=" + UPDATED_REVOKE_PROOF);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where remarks equals to DEFAULT_REMARKS
        defaultRegnRevoShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the regnRevoList where remarks equals to UPDATED_REMARKS
        defaultRegnRevoShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where remarks not equals to DEFAULT_REMARKS
        defaultRegnRevoShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the regnRevoList where remarks not equals to UPDATED_REMARKS
        defaultRegnRevoShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultRegnRevoShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the regnRevoList where remarks equals to UPDATED_REMARKS
        defaultRegnRevoShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where remarks is not null
        defaultRegnRevoShouldBeFound("remarks.specified=true");

        // Get all the regnRevoList where remarks is null
        defaultRegnRevoShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnRevosByRemarksContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where remarks contains DEFAULT_REMARKS
        defaultRegnRevoShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the regnRevoList where remarks contains UPDATED_REMARKS
        defaultRegnRevoShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnRevosByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        // Get all the regnRevoList where remarks does not contain DEFAULT_REMARKS
        defaultRegnRevoShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the regnRevoList where remarks does not contain UPDATED_REMARKS
        defaultRegnRevoShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllRegnRevosByPunishPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);
        User punishPerson = UserResourceIT.createEntity(em);
        em.persist(punishPerson);
        em.flush();
        regnRevo.setPunishPerson(punishPerson);
        regnRevoRepository.saveAndFlush(regnRevo);
        Long punishPersonId = punishPerson.getId();

        // Get all the regnRevoList where punishPerson equals to punishPersonId
        defaultRegnRevoShouldBeFound("punishPersonId.equals=" + punishPersonId);

        // Get all the regnRevoList where punishPerson equals to punishPersonId + 1
        defaultRegnRevoShouldNotBeFound("punishPersonId.equals=" + (punishPersonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegnRevoShouldBeFound(String filter) throws Exception {
        restRegnRevoMockMvc.perform(get("/api/regn-revos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regnRevo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].orgInfo").value(hasItem(DEFAULT_ORG_INFO)))
            .andExpect(jsonPath("$.[*].revokeTimeSpan").value(hasItem(DEFAULT_REVOKE_TIME_SPAN)))
            .andExpect(jsonPath("$.[*].revokeStart").value(hasItem(DEFAULT_REVOKE_START.toString())))
            .andExpect(jsonPath("$.[*].revokeOver").value(hasItem(DEFAULT_REVOKE_OVER.toString())))
            .andExpect(jsonPath("$.[*].punishOrg").value(hasItem(DEFAULT_PUNISH_ORG)))
            .andExpect(jsonPath("$.[*].punishTime").value(hasItem(DEFAULT_PUNISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].facts").value(hasItem(DEFAULT_FACTS)))
            .andExpect(jsonPath("$.[*].autoProcess").value(hasItem(DEFAULT_AUTO_PROCESS.booleanValue())))
            .andExpect(jsonPath("$.[*].revokeProof").value(hasItem(DEFAULT_REVOKE_PROOF)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restRegnRevoMockMvc.perform(get("/api/regn-revos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegnRevoShouldNotBeFound(String filter) throws Exception {
        restRegnRevoMockMvc.perform(get("/api/regn-revos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegnRevoMockMvc.perform(get("/api/regn-revos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegnRevo() throws Exception {
        // Get the regnRevo
        restRegnRevoMockMvc.perform(get("/api/regn-revos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegnRevo() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        int databaseSizeBeforeUpdate = regnRevoRepository.findAll().size();

        // Update the regnRevo
        RegnRevo updatedRegnRevo = regnRevoRepository.findById(regnRevo.getId()).get();
        // Disconnect from session so that the updates on updatedRegnRevo are not directly saved in db
        em.detach(updatedRegnRevo);
        updatedRegnRevo
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .revokeTimeSpan(UPDATED_REVOKE_TIME_SPAN)
            .revokeStart(UPDATED_REVOKE_START)
            .revokeOver(UPDATED_REVOKE_OVER)
            .punishOrg(UPDATED_PUNISH_ORG)
            .punishTime(UPDATED_PUNISH_TIME)
            .facts(UPDATED_FACTS)
            .autoProcess(UPDATED_AUTO_PROCESS)
            .revokeProof(UPDATED_REVOKE_PROOF)
            .remarks(UPDATED_REMARKS);
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(updatedRegnRevo);

        restRegnRevoMockMvc.perform(put("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isOk());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeUpdate);
        RegnRevo testRegnRevo = regnRevoList.get(regnRevoList.size() - 1);
        assertThat(testRegnRevo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegnRevo.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testRegnRevo.getOrgInfo()).isEqualTo(UPDATED_ORG_INFO);
        assertThat(testRegnRevo.getRevokeTimeSpan()).isEqualTo(UPDATED_REVOKE_TIME_SPAN);
        assertThat(testRegnRevo.getRevokeStart()).isEqualTo(UPDATED_REVOKE_START);
        assertThat(testRegnRevo.getRevokeOver()).isEqualTo(UPDATED_REVOKE_OVER);
        assertThat(testRegnRevo.getPunishOrg()).isEqualTo(UPDATED_PUNISH_ORG);
        assertThat(testRegnRevo.getPunishTime()).isEqualTo(UPDATED_PUNISH_TIME);
        assertThat(testRegnRevo.getFacts()).isEqualTo(UPDATED_FACTS);
        assertThat(testRegnRevo.isAutoProcess()).isEqualTo(UPDATED_AUTO_PROCESS);
        assertThat(testRegnRevo.getRevokeProof()).isEqualTo(UPDATED_REVOKE_PROOF);
        assertThat(testRegnRevo.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingRegnRevo() throws Exception {
        int databaseSizeBeforeUpdate = regnRevoRepository.findAll().size();

        // Create the RegnRevo
        RegnRevoDTO regnRevoDTO = regnRevoMapper.toDto(regnRevo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegnRevoMockMvc.perform(put("/api/regn-revos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnRevoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnRevo in the database
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegnRevo() throws Exception {
        // Initialize the database
        regnRevoRepository.saveAndFlush(regnRevo);

        int databaseSizeBeforeDelete = regnRevoRepository.findAll().size();

        // Delete the regnRevo
        restRegnRevoMockMvc.perform(delete("/api/regn-revos/{id}", regnRevo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegnRevo> regnRevoList = regnRevoRepository.findAll();
        assertThat(regnRevoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
