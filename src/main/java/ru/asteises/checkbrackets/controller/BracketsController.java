package ru.asteises.checkbrackets.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asteises.checkbrackets.model.TextDto;
import ru.asteises.checkbrackets.service.BracketsService;
import ru.asteises.checkbrackets.util.Endpoints;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.API)
public class BracketsController {

    private final BracketsService bracketsService;

    /**
     * Метод принимает текст на проверку.
     *
     * @param text - String
     * @return - ResponseEntity<Boolean>
     */
    @PostMapping(value = Endpoints.CHECK_BRACKETS)
    public ResponseEntity<Boolean> checkBrackets(
            @Valid
            @RequestBody TextDto text) throws HttpMessageNotReadableException {
        log.info("Пришел текст: {}", text.getContent());
        return new ResponseEntity<>(bracketsService.checkBrackets(text), HttpStatus.OK);
    }
}
