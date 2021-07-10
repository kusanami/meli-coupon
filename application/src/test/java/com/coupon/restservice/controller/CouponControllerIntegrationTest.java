package com.coupon.restservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.coupon.restservice.RestServiceApplication;
import com.coupon.restservice.model.RequestCoupon;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @author <a href="mailto:luism_fr@hotmail.com">Luis Ruiz</a>
 * @since 1.0.0
 */
@SpringBootTest(classes = RestServiceApplication.class)
@AutoConfigureMockMvc
public class CouponControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Circuit breaker registry
     */
    @Autowired
    private CircuitBreaker circuitBreakerMeli;

    @Test
    public void shouldReturn200ListWithValidIdItem_whenRequestIsValid() throws Exception {

        String expectResponse = "[\"MLA810645375\",\"MLA811601010\"]";

        MvcResult mvcResult = this.mockMvc.perform(post("/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestCoupon(60000.0f)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertEquals(expectResponse,responseBody);
    }

    @Test
    public void shouldReturn404EmptyList_whenAmountInCouponNotReachForNothing() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(post("/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestCoupon(10000.0f)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertEquals("[]", responseBody);
    }

    @Test
    public void shouldReturn404EmptyList_whenCircuitBreakerIsOpen() throws Exception {

        circuitBreakerMeli.transitionToForcedOpenState();

        MvcResult mvcResult = this.mockMvc.perform(post("/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestCoupon(60000.0f)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        assertEquals("[]", responseBody);

        circuitBreakerMeli.transitionToClosedState();
    }

    private String getRequestCoupon(float amountCoupon) {

        ObjectMapper Obj = new ObjectMapper();

        Set<String> idItems = new HashSet<>();
        idItems.add("MLA811601010");
        idItems.add("MLA816019440");
        idItems.add("MLA810645375");
        RequestCoupon request = new RequestCoupon(idItems, amountCoupon);

        try {

            return Obj.writeValueAsString(request);
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
