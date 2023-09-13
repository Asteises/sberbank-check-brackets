package ru.asteises.checkbrackets.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.asteises.checkbrackets.model.TextDto;
import ru.asteises.checkbrackets.service.BracketsService;
import ru.asteises.checkbrackets.util.Endpoints;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Getter
@Setter
@WebMvcTest(BracketsController.class)
class BracketsControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BracketsService bracketsService;

    private static String objectToJsonString(TextDto text) {
        try {
            return new ObjectMapper().writeValueAsString(text);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    void checkBrackets_ok() throws Exception {
        TextDto text = new TextDto();
        text.setContent("Test (text).");

        when(bracketsService.checkBrackets(any(TextDto.class))).thenReturn(true);

        this.mockMvc.perform(post(Endpoints.API + Endpoints.CHECK_BRACKETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJsonString(text))))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkBrackets_textContentIsEmpty() throws Exception {
        TextDto text = new TextDto();
        text.setContent(null);

        this.mockMvc.perform(post(Endpoints.API + Endpoints.CHECK_BRACKETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJsonString(text))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void checkBrackets_textIsNull() throws Exception {
        this.mockMvc.perform(post(Endpoints.API + Endpoints.CHECK_BRACKETS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJsonString(null)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException));
    }
}