package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class RegnCancDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegnCancDTO.class);
        RegnCancDTO regnCancDTO1 = new RegnCancDTO();
        regnCancDTO1.setId(1L);
        RegnCancDTO regnCancDTO2 = new RegnCancDTO();
        assertThat(regnCancDTO1).isNotEqualTo(regnCancDTO2);
        regnCancDTO2.setId(regnCancDTO1.getId());
        assertThat(regnCancDTO1).isEqualTo(regnCancDTO2);
        regnCancDTO2.setId(2L);
        assertThat(regnCancDTO1).isNotEqualTo(regnCancDTO2);
        regnCancDTO1.setId(null);
        assertThat(regnCancDTO1).isNotEqualTo(regnCancDTO2);
    }
}
