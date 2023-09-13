package ru.asteises.checkbrackets.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.asteises.checkbrackets.model.TextDto;
import ru.asteises.checkbrackets.service.BracketsService;

@ExtendWith(MockitoExtension.class)
class BracketsServiceImplTest {

    @Autowired
    BracketsService bracketsService;
    TextDto text;

    @BeforeEach
    void init() {
        bracketsService = new BracketsServiceImpl();
        text = new TextDto();
    }

    @Test
    void checkBrackets_closedBracket_beforeOpenBracket_test() {
        text.setContent("Test text ) (.");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_noTextBetweenBrackets() {
        text.setContent("Test text ()");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_pointInnerBrackets() {
        text.setContent("Test text ( text .)");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_toManyCloseBrackets() {
        text.setContent("Test text (text))");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_toManyOpenBrackets() {
        text.setContent("Test text (text). Text (");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_textWithoutBrackets() {
        text.setContent("Text (text)");
        Assertions.assertTrue(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_normalText() {
        text.setContent("Text (text). Text (text) in (text (text)).");
        Assertions.assertTrue(bracketsService.checkBrackets(text));
    }
}