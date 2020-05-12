package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class RegnCancTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegnCanc.class);
        RegnCanc regnCanc1 = new RegnCanc();
        regnCanc1.setId(1L);
        RegnCanc regnCanc2 = new RegnCanc();
        regnCanc2.setId(regnCanc1.getId());
        assertThat(regnCanc1).isEqualTo(regnCanc2);
        regnCanc2.setId(2L);
        assertThat(regnCanc1).isNotEqualTo(regnCanc2);
        regnCanc1.setId(null);
        assertThat(regnCanc1).isNotEqualTo(regnCanc2);
    }
}
