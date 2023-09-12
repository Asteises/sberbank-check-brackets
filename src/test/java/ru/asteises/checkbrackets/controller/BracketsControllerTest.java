package ru.asteises.checkbrackets.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import ru.asteises.checkbrackets.exception.ExceptionApiHandler;
import ru.asteises.checkbrackets.model.TextDto;
import ru.asteises.checkbrackets.service.BracketsService;
import ru.asteises.checkbrackets.service.impl.BracketsServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BracketsControllerTest {

    @Mock
    BracketsController bracketsController;

    @Mock
    BracketsService bracketsService;

    @Mock
    ExceptionApiHandler exceptionApiHandler;

    @Mock
    TextDto text;

    @BeforeEach
    void init() {
        bracketsService = new BracketsServiceImpl();
        bracketsController = new BracketsController(bracketsService);
        exceptionApiHandler = new ExceptionApiHandler();
        text = new TextDto();
    }

    @Test
    void checkBrackets_ok() {
        text.setText("Text (text). Text ( text (text)).");
        ResponseEntity<Boolean> response = bracketsController.checkBrackets(text);
        ResponseEntity<Boolean> expected = new ResponseEntity<Boolean>(true, HttpStatus.OK);
        Assertions.assertEquals(expected, response);
    }

    @Test
    void checkBrackets_textIsEmpty() {
        text.setText(null);

        Exception exception = assertThrows(HttpMessageNotReadableException.class, () -> bracketsController.checkBrackets(text));
        when(bracketsController.checkBrackets(text)).thenThrow(HttpMessageNotReadableException.class);

        String expectedMessage = "текст не должен быть пустым";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}