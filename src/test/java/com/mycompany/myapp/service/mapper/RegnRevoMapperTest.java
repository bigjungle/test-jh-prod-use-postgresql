package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RegnRevoMapperTest {

    private RegnRevoMapper regnRevoMapper;

    @BeforeEach
    public void setUp() {
        regnRevoMapper = new RegnRevoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(regnRevoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regnRevoMapper.fromId(null)).isNull();
    }
}
