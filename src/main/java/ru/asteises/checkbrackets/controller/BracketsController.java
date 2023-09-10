package ru.asteises.checkbrackets.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.asteises.checkbrackets.service.BracketsService;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class BracketsController {

    private final BracketsService bracketsService;

    /**
     *
     * @param text
     * @return
     */
    @PostMapping("/checkBrackets")
    public ResponseEntity<Boolean> checkBrackets(@RequestParam String text) {
        return ResponseEntity.ok(bracketsService.checkBrackets(text));
    }
}
