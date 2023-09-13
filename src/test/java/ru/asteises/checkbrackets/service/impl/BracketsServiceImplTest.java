package ru.asteises.checkbrackets.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.asteises.checkbrackets.model.TextDto;
import ru.asteises.checkbrackets.service.BracketsService;

class BracketsServiceImplTest {

    @Autowired
    BracketsService bracketsService;

    @Autowired
    TextDto text;

    @BeforeEach
    void init() {
        bracketsService = new BracketsServiceImpl();
        text = new TextDto();
    }

    @Test
    void checkBrackets_closedBracket_beforeOpenBracket_test() {
        text.setText("Test text ) (.");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_noTextBetweenBrackets() {
        text.setText("Test text ()");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_pointInnerBrackets() {
        text.setText("Test text ( text .)");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_toManyCloseBrackets() {
        text.setText("Test text (text))");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_toManyOpenBrackets() {
        text.setText("Test text (text). Text (");
        Assertions.assertFalse(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_textWithoutBrackets() {
        text.setText("Text (text)");
        Assertions.assertTrue(bracketsService.checkBrackets(text));
    }

    @Test
    void checkBrackets_normalText() {
        text.setText("Text (text). Text (text) in (text (text)).");
        Assertions.assertTrue(bracketsService.checkBrackets(text));
    }
}