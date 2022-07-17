package com.db.tradestore.controller;

import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import com.db.tradestore.schedular.TradeExpiration;
import com.db.tradestore.service.TradeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerTest {

   @MockBean
   TradeService tradeService;

   @MockBean
   TradeRepository tradeRepository;

   @MockBean
   TradeExpiration tradeExpiration;

   @Autowired
   MockMvc mockMvc;


    @SneakyThrows
    @Test
    void givenValidTrade_ExpectCreated() {

        Trade sampleTrade = new Trade();
        sampleTrade.setId(1);
        sampleTrade.setTradeId("T1");
        sampleTrade.setBookId("B1");
        sampleTrade.setCounterPartyId("CP-1");
        sampleTrade.setVersion(1);
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        sampleTrade.setMaturityDate(formatter.parse("2023-10-20"));
        sampleTrade.setCreatedDate(new Date());
        sampleTrade.setExpired('N');

        Mockito.when(tradeService.isLatestVersion(any())).thenReturn(true);
        Mockito.when(tradeRepository.save(any())).thenReturn(sampleTrade);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("tradeId","T1");
        body.put("version",1);
        body.put("counterPartyId","CP-1");
        body.put("bookId","B1");
        body.put("maturityDate","2023-10-20");

        mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @SneakyThrows
    @Test
    void givenOldMaturityDateTrade_ExpectBadRequest() {

        Trade sampleTrade = new Trade();
        sampleTrade.setId(1);
        sampleTrade.setTradeId("T1");
        sampleTrade.setBookId("B1");
        sampleTrade.setCounterPartyId("CP-1");
        sampleTrade.setVersion(1);
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        sampleTrade.setMaturityDate(formatter.parse("2023-10-20"));
        sampleTrade.setCreatedDate(new Date());
        sampleTrade.setExpired('N');

        Mockito.when(tradeService.isLatestVersion(any())).thenReturn(true);
        Mockito.when(tradeRepository.save(any())).thenReturn(sampleTrade);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("tradeId","T1");
        body.put("version",1);
        body.put("counterPartyId","CP-1");
        body.put("bookId","B1");
        body.put("maturityDate","2020-10-20");

        mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    @Test
    void givenLowVersionTrade_ExpectBadRequest() {

        Trade sampleTrade = new Trade();
        sampleTrade.setId(1);
        sampleTrade.setTradeId("T1");
        sampleTrade.setBookId("B1");
        sampleTrade.setCounterPartyId("CP-1");
        sampleTrade.setVersion(1);
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        sampleTrade.setMaturityDate(formatter.parse("2023-10-20"));
        sampleTrade.setCreatedDate(new Date());
        sampleTrade.setExpired('N');

        Mockito.when(tradeService.isLatestVersion(any())).thenReturn(false);
        Mockito.when(tradeRepository.save(any())).thenReturn(sampleTrade);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = new HashMap<>();
        body.put("tradeId","T1");
        body.put("version",1);
        body.put("counterPartyId","CP-1");
        body.put("bookId","B1");
        body.put("maturityDate","2023-10-20");

        mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    @Test
    void validMaturedTradesFound() {

        Trade sampleTrade = new Trade();
        sampleTrade.setId(1);
        sampleTrade.setTradeId("T1");
        sampleTrade.setBookId("B1");
        sampleTrade.setCounterPartyId("CP-1");
        sampleTrade.setVersion(1);
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        sampleTrade.setMaturityDate(formatter.parse("2020-10-20"));
        sampleTrade.setCreatedDate(new Date());
        sampleTrade.setExpired('N');

        Mockito.when(tradeExpiration.expireMaturedTrade()).thenReturn(Collections.singletonList(sampleTrade));

        mockMvc.perform((get("/api/expire")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].tradeId",Matchers.is("T1")));
    }
}