package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class RegnRevoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegnRevoDTO.class);
        RegnRevoDTO regnRevoDTO1 = new RegnRevoDTO();
        regnRevoDTO1.setId(1L);
        RegnRevoDTO regnRevoDTO2 = new RegnRevoDTO();
        assertThat(regnRevoDTO1).isNotEqualTo(regnRevoDTO2);
        regnRevoDTO2.setId(regnRevoDTO1.getId());
        assertThat(regnRevoDTO1).isEqualTo(regnRevoDTO2);
        regnRevoDTO2.setId(2L);
        assertThat(regnRevoDTO1).isNotEqualTo(regnRevoDTO2);
        regnRevoDTO1.setId(null);
        assertThat(regnRevoDTO1).isNotEqualTo(regnRevoDTO2);
    }
}
