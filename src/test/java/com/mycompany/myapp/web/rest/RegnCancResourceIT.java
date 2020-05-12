package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestProdDatabaseApp;
import com.mycompany.myapp.domain.RegnCanc;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.RegnCancRepository;
import com.mycompany.myapp.service.RegnCancService;
import com.mycompany.myapp.service.dto.RegnCancDTO;
import com.mycompany.myapp.service.mapper.RegnCancMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.RegnCancCriteria;
import com.mycompany.myapp.service.RegnCancQueryService;

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
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegnCancResource} REST controller.
 */
@SpringBootTest(classes = TestProdDatabaseApp.class)
public class RegnCancResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DESC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ORG_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CANCELLATION_WAY = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_WAY = "BBBBBBBBBB";

    private static final String DEFAULT_CANCELLATION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_REASON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CANCELLATION_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CANCELLATION_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CANCELLATION_TIME = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CANCELLATION_PROOF = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_PROOF = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RegnCancRepository regnCancRepository;

    @Autowired
    private RegnCancMapper regnCancMapper;

    @Autowired
    private RegnCancService regnCancService;

    @Autowired
    private RegnCancQueryService regnCancQueryService;

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

    private MockMvc restRegnCancMockMvc;

    private RegnCanc regnCanc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegnCancResource regnCancResource = new RegnCancResource(regnCancService, regnCancQueryService);
        this.restRegnCancMockMvc = MockMvcBuilders.standaloneSetup(regnCancResource)
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
    public static RegnCanc createEntity(EntityManager em) {
        RegnCanc regnCanc = new RegnCanc()
            .name(DEFAULT_NAME)
            .descString(DEFAULT_DESC_STRING)
            .orgInfo(DEFAULT_ORG_INFO)
            .cancellationWay(DEFAULT_CANCELLATION_WAY)
            .cancellationReason(DEFAULT_CANCELLATION_REASON)
            .cancellationTime(DEFAULT_CANCELLATION_TIME)
            .cancellationProof(DEFAULT_CANCELLATION_PROOF)
            .remarks(DEFAULT_REMARKS);
        return regnCanc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegnCanc createUpdatedEntity(EntityManager em) {
        RegnCanc regnCanc = new RegnCanc()
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .cancellationWay(UPDATED_CANCELLATION_WAY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .cancellationProof(UPDATED_CANCELLATION_PROOF)
            .remarks(UPDATED_REMARKS);
        return regnCanc;
    }

    @BeforeEach
    public void initTest() {
        regnCanc = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegnCanc() throws Exception {
        int databaseSizeBeforeCreate = regnCancRepository.findAll().size();

        // Create the RegnCanc
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);
        restRegnCancMockMvc.perform(post("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isCreated());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeCreate + 1);
        RegnCanc testRegnCanc = regnCancList.get(regnCancList.size() - 1);
        assertThat(testRegnCanc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegnCanc.getDescString()).isEqualTo(DEFAULT_DESC_STRING);
        assertThat(testRegnCanc.getOrgInfo()).isEqualTo(DEFAULT_ORG_INFO);
        assertThat(testRegnCanc.getCancellationWay()).isEqualTo(DEFAULT_CANCELLATION_WAY);
        assertThat(testRegnCanc.getCancellationReason()).isEqualTo(DEFAULT_CANCELLATION_REASON);
        assertThat(testRegnCanc.getCancellationTime()).isEqualTo(DEFAULT_CANCELLATION_TIME);
        assertThat(testRegnCanc.getCancellationProof()).isEqualTo(DEFAULT_CANCELLATION_PROOF);
        assertThat(testRegnCanc.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createRegnCancWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regnCancRepository.findAll().size();

        // Create the RegnCanc with an existing ID
        regnCanc.setId(1L);
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegnCancMockMvc.perform(post("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regnCancRepository.findAll().size();
        // set the field null
        regnCanc.setName(null);

        // Create the RegnCanc, which fails.
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);

        restRegnCancMockMvc.perform(post("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isBadRequest());

        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegnCancs() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList
        restRegnCancMockMvc.perform(get("/api/regn-cancs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regnCanc.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].orgInfo").value(hasItem(DEFAULT_ORG_INFO)))
            .andExpect(jsonPath("$.[*].cancellationWay").value(hasItem(DEFAULT_CANCELLATION_WAY)))
            .andExpect(jsonPath("$.[*].cancellationReason").value(hasItem(DEFAULT_CANCELLATION_REASON)))
            .andExpect(jsonPath("$.[*].cancellationTime").value(hasItem(DEFAULT_CANCELLATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].cancellationProof").value(hasItem(DEFAULT_CANCELLATION_PROOF)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getRegnCanc() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get the regnCanc
        restRegnCancMockMvc.perform(get("/api/regn-cancs/{id}", regnCanc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regnCanc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.descString").value(DEFAULT_DESC_STRING))
            .andExpect(jsonPath("$.orgInfo").value(DEFAULT_ORG_INFO))
            .andExpect(jsonPath("$.cancellationWay").value(DEFAULT_CANCELLATION_WAY))
            .andExpect(jsonPath("$.cancellationReason").value(DEFAULT_CANCELLATION_REASON))
            .andExpect(jsonPath("$.cancellationTime").value(DEFAULT_CANCELLATION_TIME.toString()))
            .andExpect(jsonPath("$.cancellationProof").value(DEFAULT_CANCELLATION_PROOF))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getRegnCancsByIdFiltering() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        Long id = regnCanc.getId();

        defaultRegnCancShouldBeFound("id.equals=" + id);
        defaultRegnCancShouldNotBeFound("id.notEquals=" + id);

        defaultRegnCancShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegnCancShouldNotBeFound("id.greaterThan=" + id);

        defaultRegnCancShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegnCancShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where name equals to DEFAULT_NAME
        defaultRegnCancShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regnCancList where name equals to UPDATED_NAME
        defaultRegnCancShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where name not equals to DEFAULT_NAME
        defaultRegnCancShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the regnCancList where name not equals to UPDATED_NAME
        defaultRegnCancShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegnCancShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regnCancList where name equals to UPDATED_NAME
        defaultRegnCancShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where name is not null
        defaultRegnCancShouldBeFound("name.specified=true");

        // Get all the regnCancList where name is null
        defaultRegnCancShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByNameContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where name contains DEFAULT_NAME
        defaultRegnCancShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the regnCancList where name contains UPDATED_NAME
        defaultRegnCancShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where name does not contain DEFAULT_NAME
        defaultRegnCancShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the regnCancList where name does not contain UPDATED_NAME
        defaultRegnCancShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByDescStringIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where descString equals to DEFAULT_DESC_STRING
        defaultRegnCancShouldBeFound("descString.equals=" + DEFAULT_DESC_STRING);

        // Get all the regnCancList where descString equals to UPDATED_DESC_STRING
        defaultRegnCancShouldNotBeFound("descString.equals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByDescStringIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where descString not equals to DEFAULT_DESC_STRING
        defaultRegnCancShouldNotBeFound("descString.notEquals=" + DEFAULT_DESC_STRING);

        // Get all the regnCancList where descString not equals to UPDATED_DESC_STRING
        defaultRegnCancShouldBeFound("descString.notEquals=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByDescStringIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where descString in DEFAULT_DESC_STRING or UPDATED_DESC_STRING
        defaultRegnCancShouldBeFound("descString.in=" + DEFAULT_DESC_STRING + "," + UPDATED_DESC_STRING);

        // Get all the regnCancList where descString equals to UPDATED_DESC_STRING
        defaultRegnCancShouldNotBeFound("descString.in=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByDescStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where descString is not null
        defaultRegnCancShouldBeFound("descString.specified=true");

        // Get all the regnCancList where descString is null
        defaultRegnCancShouldNotBeFound("descString.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByDescStringContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where descString contains DEFAULT_DESC_STRING
        defaultRegnCancShouldBeFound("descString.contains=" + DEFAULT_DESC_STRING);

        // Get all the regnCancList where descString contains UPDATED_DESC_STRING
        defaultRegnCancShouldNotBeFound("descString.contains=" + UPDATED_DESC_STRING);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByDescStringNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where descString does not contain DEFAULT_DESC_STRING
        defaultRegnCancShouldNotBeFound("descString.doesNotContain=" + DEFAULT_DESC_STRING);

        // Get all the regnCancList where descString does not contain UPDATED_DESC_STRING
        defaultRegnCancShouldBeFound("descString.doesNotContain=" + UPDATED_DESC_STRING);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByOrgInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where orgInfo equals to DEFAULT_ORG_INFO
        defaultRegnCancShouldBeFound("orgInfo.equals=" + DEFAULT_ORG_INFO);

        // Get all the regnCancList where orgInfo equals to UPDATED_ORG_INFO
        defaultRegnCancShouldNotBeFound("orgInfo.equals=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByOrgInfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where orgInfo not equals to DEFAULT_ORG_INFO
        defaultRegnCancShouldNotBeFound("orgInfo.notEquals=" + DEFAULT_ORG_INFO);

        // Get all the regnCancList where orgInfo not equals to UPDATED_ORG_INFO
        defaultRegnCancShouldBeFound("orgInfo.notEquals=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByOrgInfoIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where orgInfo in DEFAULT_ORG_INFO or UPDATED_ORG_INFO
        defaultRegnCancShouldBeFound("orgInfo.in=" + DEFAULT_ORG_INFO + "," + UPDATED_ORG_INFO);

        // Get all the regnCancList where orgInfo equals to UPDATED_ORG_INFO
        defaultRegnCancShouldNotBeFound("orgInfo.in=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByOrgInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where orgInfo is not null
        defaultRegnCancShouldBeFound("orgInfo.specified=true");

        // Get all the regnCancList where orgInfo is null
        defaultRegnCancShouldNotBeFound("orgInfo.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByOrgInfoContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where orgInfo contains DEFAULT_ORG_INFO
        defaultRegnCancShouldBeFound("orgInfo.contains=" + DEFAULT_ORG_INFO);

        // Get all the regnCancList where orgInfo contains UPDATED_ORG_INFO
        defaultRegnCancShouldNotBeFound("orgInfo.contains=" + UPDATED_ORG_INFO);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByOrgInfoNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where orgInfo does not contain DEFAULT_ORG_INFO
        defaultRegnCancShouldNotBeFound("orgInfo.doesNotContain=" + DEFAULT_ORG_INFO);

        // Get all the regnCancList where orgInfo does not contain UPDATED_ORG_INFO
        defaultRegnCancShouldBeFound("orgInfo.doesNotContain=" + UPDATED_ORG_INFO);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByCancellationWayIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationWay equals to DEFAULT_CANCELLATION_WAY
        defaultRegnCancShouldBeFound("cancellationWay.equals=" + DEFAULT_CANCELLATION_WAY);

        // Get all the regnCancList where cancellationWay equals to UPDATED_CANCELLATION_WAY
        defaultRegnCancShouldNotBeFound("cancellationWay.equals=" + UPDATED_CANCELLATION_WAY);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationWayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationWay not equals to DEFAULT_CANCELLATION_WAY
        defaultRegnCancShouldNotBeFound("cancellationWay.notEquals=" + DEFAULT_CANCELLATION_WAY);

        // Get all the regnCancList where cancellationWay not equals to UPDATED_CANCELLATION_WAY
        defaultRegnCancShouldBeFound("cancellationWay.notEquals=" + UPDATED_CANCELLATION_WAY);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationWayIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationWay in DEFAULT_CANCELLATION_WAY or UPDATED_CANCELLATION_WAY
        defaultRegnCancShouldBeFound("cancellationWay.in=" + DEFAULT_CANCELLATION_WAY + "," + UPDATED_CANCELLATION_WAY);

        // Get all the regnCancList where cancellationWay equals to UPDATED_CANCELLATION_WAY
        defaultRegnCancShouldNotBeFound("cancellationWay.in=" + UPDATED_CANCELLATION_WAY);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationWayIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationWay is not null
        defaultRegnCancShouldBeFound("cancellationWay.specified=true");

        // Get all the regnCancList where cancellationWay is null
        defaultRegnCancShouldNotBeFound("cancellationWay.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByCancellationWayContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationWay contains DEFAULT_CANCELLATION_WAY
        defaultRegnCancShouldBeFound("cancellationWay.contains=" + DEFAULT_CANCELLATION_WAY);

        // Get all the regnCancList where cancellationWay contains UPDATED_CANCELLATION_WAY
        defaultRegnCancShouldNotBeFound("cancellationWay.contains=" + UPDATED_CANCELLATION_WAY);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationWayNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationWay does not contain DEFAULT_CANCELLATION_WAY
        defaultRegnCancShouldNotBeFound("cancellationWay.doesNotContain=" + DEFAULT_CANCELLATION_WAY);

        // Get all the regnCancList where cancellationWay does not contain UPDATED_CANCELLATION_WAY
        defaultRegnCancShouldBeFound("cancellationWay.doesNotContain=" + UPDATED_CANCELLATION_WAY);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByCancellationReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationReason equals to DEFAULT_CANCELLATION_REASON
        defaultRegnCancShouldBeFound("cancellationReason.equals=" + DEFAULT_CANCELLATION_REASON);

        // Get all the regnCancList where cancellationReason equals to UPDATED_CANCELLATION_REASON
        defaultRegnCancShouldNotBeFound("cancellationReason.equals=" + UPDATED_CANCELLATION_REASON);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationReason not equals to DEFAULT_CANCELLATION_REASON
        defaultRegnCancShouldNotBeFound("cancellationReason.notEquals=" + DEFAULT_CANCELLATION_REASON);

        // Get all the regnCancList where cancellationReason not equals to UPDATED_CANCELLATION_REASON
        defaultRegnCancShouldBeFound("cancellationReason.notEquals=" + UPDATED_CANCELLATION_REASON);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationReasonIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationReason in DEFAULT_CANCELLATION_REASON or UPDATED_CANCELLATION_REASON
        defaultRegnCancShouldBeFound("cancellationReason.in=" + DEFAULT_CANCELLATION_REASON + "," + UPDATED_CANCELLATION_REASON);

        // Get all the regnCancList where cancellationReason equals to UPDATED_CANCELLATION_REASON
        defaultRegnCancShouldNotBeFound("cancellationReason.in=" + UPDATED_CANCELLATION_REASON);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationReason is not null
        defaultRegnCancShouldBeFound("cancellationReason.specified=true");

        // Get all the regnCancList where cancellationReason is null
        defaultRegnCancShouldNotBeFound("cancellationReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByCancellationReasonContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationReason contains DEFAULT_CANCELLATION_REASON
        defaultRegnCancShouldBeFound("cancellationReason.contains=" + DEFAULT_CANCELLATION_REASON);

        // Get all the regnCancList where cancellationReason contains UPDATED_CANCELLATION_REASON
        defaultRegnCancShouldNotBeFound("cancellationReason.contains=" + UPDATED_CANCELLATION_REASON);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationReasonNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationReason does not contain DEFAULT_CANCELLATION_REASON
        defaultRegnCancShouldNotBeFound("cancellationReason.doesNotContain=" + DEFAULT_CANCELLATION_REASON);

        // Get all the regnCancList where cancellationReason does not contain UPDATED_CANCELLATION_REASON
        defaultRegnCancShouldBeFound("cancellationReason.doesNotContain=" + UPDATED_CANCELLATION_REASON);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime equals to DEFAULT_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.equals=" + DEFAULT_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime equals to UPDATED_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.equals=" + UPDATED_CANCELLATION_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime not equals to DEFAULT_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.notEquals=" + DEFAULT_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime not equals to UPDATED_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.notEquals=" + UPDATED_CANCELLATION_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime in DEFAULT_CANCELLATION_TIME or UPDATED_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.in=" + DEFAULT_CANCELLATION_TIME + "," + UPDATED_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime equals to UPDATED_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.in=" + UPDATED_CANCELLATION_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime is not null
        defaultRegnCancShouldBeFound("cancellationTime.specified=true");

        // Get all the regnCancList where cancellationTime is null
        defaultRegnCancShouldNotBeFound("cancellationTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime is greater than or equal to DEFAULT_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.greaterThanOrEqual=" + DEFAULT_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime is greater than or equal to UPDATED_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.greaterThanOrEqual=" + UPDATED_CANCELLATION_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime is less than or equal to DEFAULT_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.lessThanOrEqual=" + DEFAULT_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime is less than or equal to SMALLER_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.lessThanOrEqual=" + SMALLER_CANCELLATION_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime is less than DEFAULT_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.lessThan=" + DEFAULT_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime is less than UPDATED_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.lessThan=" + UPDATED_CANCELLATION_TIME);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationTime is greater than DEFAULT_CANCELLATION_TIME
        defaultRegnCancShouldNotBeFound("cancellationTime.greaterThan=" + DEFAULT_CANCELLATION_TIME);

        // Get all the regnCancList where cancellationTime is greater than SMALLER_CANCELLATION_TIME
        defaultRegnCancShouldBeFound("cancellationTime.greaterThan=" + SMALLER_CANCELLATION_TIME);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByCancellationProofIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationProof equals to DEFAULT_CANCELLATION_PROOF
        defaultRegnCancShouldBeFound("cancellationProof.equals=" + DEFAULT_CANCELLATION_PROOF);

        // Get all the regnCancList where cancellationProof equals to UPDATED_CANCELLATION_PROOF
        defaultRegnCancShouldNotBeFound("cancellationProof.equals=" + UPDATED_CANCELLATION_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationProofIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationProof not equals to DEFAULT_CANCELLATION_PROOF
        defaultRegnCancShouldNotBeFound("cancellationProof.notEquals=" + DEFAULT_CANCELLATION_PROOF);

        // Get all the regnCancList where cancellationProof not equals to UPDATED_CANCELLATION_PROOF
        defaultRegnCancShouldBeFound("cancellationProof.notEquals=" + UPDATED_CANCELLATION_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationProofIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationProof in DEFAULT_CANCELLATION_PROOF or UPDATED_CANCELLATION_PROOF
        defaultRegnCancShouldBeFound("cancellationProof.in=" + DEFAULT_CANCELLATION_PROOF + "," + UPDATED_CANCELLATION_PROOF);

        // Get all the regnCancList where cancellationProof equals to UPDATED_CANCELLATION_PROOF
        defaultRegnCancShouldNotBeFound("cancellationProof.in=" + UPDATED_CANCELLATION_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationProofIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationProof is not null
        defaultRegnCancShouldBeFound("cancellationProof.specified=true");

        // Get all the regnCancList where cancellationProof is null
        defaultRegnCancShouldNotBeFound("cancellationProof.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByCancellationProofContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationProof contains DEFAULT_CANCELLATION_PROOF
        defaultRegnCancShouldBeFound("cancellationProof.contains=" + DEFAULT_CANCELLATION_PROOF);

        // Get all the regnCancList where cancellationProof contains UPDATED_CANCELLATION_PROOF
        defaultRegnCancShouldNotBeFound("cancellationProof.contains=" + UPDATED_CANCELLATION_PROOF);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByCancellationProofNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where cancellationProof does not contain DEFAULT_CANCELLATION_PROOF
        defaultRegnCancShouldNotBeFound("cancellationProof.doesNotContain=" + DEFAULT_CANCELLATION_PROOF);

        // Get all the regnCancList where cancellationProof does not contain UPDATED_CANCELLATION_PROOF
        defaultRegnCancShouldBeFound("cancellationProof.doesNotContain=" + UPDATED_CANCELLATION_PROOF);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where remarks equals to DEFAULT_REMARKS
        defaultRegnCancShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the regnCancList where remarks equals to UPDATED_REMARKS
        defaultRegnCancShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where remarks not equals to DEFAULT_REMARKS
        defaultRegnCancShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the regnCancList where remarks not equals to UPDATED_REMARKS
        defaultRegnCancShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultRegnCancShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the regnCancList where remarks equals to UPDATED_REMARKS
        defaultRegnCancShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where remarks is not null
        defaultRegnCancShouldBeFound("remarks.specified=true");

        // Get all the regnCancList where remarks is null
        defaultRegnCancShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegnCancsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where remarks contains DEFAULT_REMARKS
        defaultRegnCancShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the regnCancList where remarks contains UPDATED_REMARKS
        defaultRegnCancShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRegnCancsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        // Get all the regnCancList where remarks does not contain DEFAULT_REMARKS
        defaultRegnCancShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the regnCancList where remarks does not contain UPDATED_REMARKS
        defaultRegnCancShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllRegnCancsByOwnerByIsEqualToSomething() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);
        User ownerBy = UserResourceIT.createEntity(em);
        em.persist(ownerBy);
        em.flush();
        regnCanc.setOwnerBy(ownerBy);
        regnCancRepository.saveAndFlush(regnCanc);
        Long ownerById = ownerBy.getId();

        // Get all the regnCancList where ownerBy equals to ownerById
        defaultRegnCancShouldBeFound("ownerById.equals=" + ownerById);

        // Get all the regnCancList where ownerBy equals to ownerById + 1
        defaultRegnCancShouldNotBeFound("ownerById.equals=" + (ownerById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegnCancShouldBeFound(String filter) throws Exception {
        restRegnCancMockMvc.perform(get("/api/regn-cancs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regnCanc.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].descString").value(hasItem(DEFAULT_DESC_STRING)))
            .andExpect(jsonPath("$.[*].orgInfo").value(hasItem(DEFAULT_ORG_INFO)))
            .andExpect(jsonPath("$.[*].cancellationWay").value(hasItem(DEFAULT_CANCELLATION_WAY)))
            .andExpect(jsonPath("$.[*].cancellationReason").value(hasItem(DEFAULT_CANCELLATION_REASON)))
            .andExpect(jsonPath("$.[*].cancellationTime").value(hasItem(DEFAULT_CANCELLATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].cancellationProof").value(hasItem(DEFAULT_CANCELLATION_PROOF)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restRegnCancMockMvc.perform(get("/api/regn-cancs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegnCancShouldNotBeFound(String filter) throws Exception {
        restRegnCancMockMvc.perform(get("/api/regn-cancs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegnCancMockMvc.perform(get("/api/regn-cancs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegnCanc() throws Exception {
        // Get the regnCanc
        restRegnCancMockMvc.perform(get("/api/regn-cancs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegnCanc() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        int databaseSizeBeforeUpdate = regnCancRepository.findAll().size();

        // Update the regnCanc
        RegnCanc updatedRegnCanc = regnCancRepository.findById(regnCanc.getId()).get();
        // Disconnect from session so that the updates on updatedRegnCanc are not directly saved in db
        em.detach(updatedRegnCanc);
        updatedRegnCanc
            .name(UPDATED_NAME)
            .descString(UPDATED_DESC_STRING)
            .orgInfo(UPDATED_ORG_INFO)
            .cancellationWay(UPDATED_CANCELLATION_WAY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .cancellationTime(UPDATED_CANCELLATION_TIME)
            .cancellationProof(UPDATED_CANCELLATION_PROOF)
            .remarks(UPDATED_REMARKS);
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(updatedRegnCanc);

        restRegnCancMockMvc.perform(put("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isOk());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeUpdate);
        RegnCanc testRegnCanc = regnCancList.get(regnCancList.size() - 1);
        assertThat(testRegnCanc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegnCanc.getDescString()).isEqualTo(UPDATED_DESC_STRING);
        assertThat(testRegnCanc.getOrgInfo()).isEqualTo(UPDATED_ORG_INFO);
        assertThat(testRegnCanc.getCancellationWay()).isEqualTo(UPDATED_CANCELLATION_WAY);
        assertThat(testRegnCanc.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testRegnCanc.getCancellationTime()).isEqualTo(UPDATED_CANCELLATION_TIME);
        assertThat(testRegnCanc.getCancellationProof()).isEqualTo(UPDATED_CANCELLATION_PROOF);
        assertThat(testRegnCanc.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingRegnCanc() throws Exception {
        int databaseSizeBeforeUpdate = regnCancRepository.findAll().size();

        // Create the RegnCanc
        RegnCancDTO regnCancDTO = regnCancMapper.toDto(regnCanc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegnCancMockMvc.perform(put("/api/regn-cancs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regnCancDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegnCanc in the database
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegnCanc() throws Exception {
        // Initialize the database
        regnCancRepository.saveAndFlush(regnCanc);

        int databaseSizeBeforeDelete = regnCancRepository.findAll().size();

        // Delete the regnCanc
        restRegnCancMockMvc.perform(delete("/api/regn-cancs/{id}", regnCanc.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegnCanc> regnCancList = regnCancRepository.findAll();
        assertThat(regnCancList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
