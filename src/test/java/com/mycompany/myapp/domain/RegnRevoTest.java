package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class RegnRevoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegnRevo.class);
        RegnRevo regnRevo1 = new RegnRevo();
        regnRevo1.setId(1L);
        RegnRevo regnRevo2 = new RegnRevo();
        regnRevo2.setId(regnRevo1.getId());
        assertThat(regnRevo1).isEqualTo(regnRevo2);
        regnRevo2.setId(2L);
        assertThat(regnRevo1).isNotEqualTo(regnRevo2);
        regnRevo1.setId(null);
        assertThat(regnRevo1).isNotEqualTo(regnRevo2);
    }
}
