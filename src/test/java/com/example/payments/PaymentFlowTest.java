package com.example.payments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentFlowTest {
    @Autowired MockMvc mvc;

    private static final String SINPE = """
        {"amount":5000,"currency":"CRC","method":{"type":"SINPE_MOVIL","phone":"88887777"}}""";
    private static final String CARD = """
        {"amount":25.50,"currency":"USD","method":{"type":"CARD","token":"tok_visa","brand":"VISA"}}""";
    private static final String BANK = """
        {"amount":100,"currency":"USD","method":{"type":"BANK_TRANSFER","iban":"CR05015202001026284066"}}""";

    private String pay(String key, String body) throws Exception {
        return mvc.perform(post("/payments").header("Idempotency-Key", key)
                .contentType(MediaType.APPLICATION_JSON).content(body))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }

    @Test void sinpe_paga_y_reembolsa() throws Exception {
        var res = pay(UUID.randomUUID().toString(), SINPE);
        assertThat((String) JsonPath.read(res, "$.status")).isEqualTo("CAPTURED");
        String id = JsonPath.read(res, "$.paymentId");
        mvc.perform(post("/payments/" + id + "/refund"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("REFUNDED"));
    }

    @Test void sinpe_no_soporta_captura() throws Exception {
        String id = JsonPath.read(pay(UUID.randomUUID().toString(), SINPE), "$.paymentId");
        mvc.perform(post("/payments/" + id + "/capture")).andExpect(status().isUnprocessableEntity());
    }

    @Test void tarjeta_autoriza_y_captura() throws Exception {
        var res = pay(UUID.randomUUID().toString(), CARD);
        assertThat((String) JsonPath.read(res, "$.status")).isEqualTo("AUTHORIZED");
        String id = JsonPath.read(res, "$.paymentId");
        mvc.perform(post("/payments/" + id + "/capture"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("CAPTURED"));
    }

    @Test void transferencia_no_se_reembolsa() throws Exception {
        var res = pay(UUID.randomUUID().toString(), BANK);
        assertThat((String) JsonPath.read(res, "$.status")).isEqualTo("PENDING");
        String id = JsonPath.read(res, "$.paymentId");
        mvc.perform(post("/payments/" + id + "/refund")).andExpect(status().isUnprocessableEntity());
    }

    @Test void misma_idempotency_key_devuelve_el_mismo_pago() throws Exception {
        var key = UUID.randomUUID().toString();
        String a = JsonPath.read(pay(key, CARD), "$.paymentId");
        String b = JsonPath.read(pay(key, CARD), "$.paymentId");
        assertThat(a).isEqualTo(b);
    }

    @Test void tipo_desconocido_es_400() throws Exception {
        mvc.perform(post("/payments").header("Idempotency-Key", "k1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":1,\"currency\":\"USD\",\"method\":{\"type\":\"BITCOIN\"}}"))
            .andExpect(status().isBadRequest());
    }
}
