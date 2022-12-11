package ru.mikhailova.customerService.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EmbeddedKafka(brokerProperties = {
        "listeners=PLAINTEXT://localhost:9099",
        "port=9099"
})
@ActiveProfiles(value = "test")
public abstract class AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private static final String API_URL = "/api/v1/support/claim";

    protected String performStart(Object requestBody) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(API_URL + "/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Claim with id: ")))
                .andReturn();
        return mvcResult.getResponse().getContentAsString();
    }

    protected <T> T performRegistration(Long id, Object requestBody, Class<T> response) throws Exception {
        ResultActions resultActions = mockMvc.perform(patch(API_URL + "/register/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk());


        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), response);
    }

    protected <T> T performExecuteBasic(Long id, Object requestBody, Class<T> response) throws Exception {
        ResultActions resultActions = mockMvc.perform(patch(API_URL + "/execute/basic/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk());
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), response);
    }

    protected <T> T performExecuteAssigned(Long id, Object requestBody, Class<T> response) throws Exception {
        ResultActions resultActions = mockMvc.perform(patch(API_URL + "/execute/assigned/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk());
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), response);
    }

    protected ResultActions performClaimAnswerException(Long id, String executionType, Object requestBody, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(patch(API_URL + "/execute" + executionType + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(resultMatcher);
    }

    protected <T> T performGetAll(TypeReference<T> response) throws Exception {
        ResultActions resultActions = mockMvc.perform(get(API_URL + "/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), response);
    }

    protected <T> T performGetById(Long id, Class<T> response) throws Exception {
        ResultActions resultActions = mockMvc.perform(get(API_URL + "/get-by-id/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), response);
    }

    protected void performDelete(Long id) throws Exception {
        mockMvc.perform(delete(API_URL + "/delete-by-id/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    protected <T> T performUpdate(Long id, Object requestBody, Class<T> response) throws Exception {
        ResultActions resultActions = mockMvc.perform(patch(API_URL + "/update-by-id/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print()).andExpect(status().isOk());
        return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), response);
    }
}
