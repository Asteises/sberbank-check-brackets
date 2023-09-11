package ru.asteises.checkbrackets.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.checkbrackets.service.BracketsService;

import java.util.Stack;


@Slf4j
@AllArgsConstructor
@Getter
@Setter
@Service
public class BracketsServiceImpl implements BracketsService {
    // A ( A (  A  ) A) A (.
    // A ( A ( A. ) ).
    private int bracketCount;
    private Stack<Character> charsBetweenBrackets;

    @Override
    public Boolean checkBrackets(String text) {
        char checkChar;
        for (int i = 0; i < text.length(); i++) {
            checkChar = text.charAt(i);
            if (isBracket(checkChar)) {
                if (isOpenBracket(checkChar) && !charsBetweenBrackets.isEmpty()) {
                    bracketCount += 1;
                    log.info("Найдена открывающая скобка, увеличиваем флаг: {}", bracketCount);
                    charsBetweenBrackets.clear();
                }
                if (!isOpenBracket(checkChar) && !charsBetweenBrackets.isEmpty() && bracketCount != 0) {
                    bracketCount -= 1;
                    log.info("Найдена закрывающая скобка, пара закрылась: {}", bracketCount);
                } else if (!isOpenBracket(checkChar) && bracketCount != 0 && charsBetweenBrackets.isEmpty()) {
                    log.info("Между скобками нет текста");
                    return false;
                } else if (!isOpenBracket(checkChar) && !charsBetweenBrackets.isEmpty() && bracketCount == 0) {
                    log.info("Первая скобка в тексте оказалась закрывающей - не годиться");
                    return false;
                }
            }
            if (isPointBetweenBracket(checkChar)) {
                log.info("Нельзя поставить точку внутри скобок");
                return false;
            }
            charsBetweenBrackets.push(checkChar);
        }
        if (bracketCount == 0) {
            log.info("Все пары скобок закрылись, или это был текст без скобок. В любом случае, все хорошо. Ура!");
            return true;
        } else if (bracketCount > 0) {
            log.info("Где-то не закрыли скобку: {}", bracketCount);
            return false;
        } else {
            log.info("Закрывающих скобок  больше чем нужно: {}", bracketCount);
            return false;
        }
    }

    /**
     * Метод проверяет, что принятый символ это открывающая скобка.
     *
     * @param checkChar char
     * @return boolean
     */
    public Boolean isOpenBracket(char checkChar) {
        return checkChar == '(';
    }

    /**
     * Метод проверяет, чтобы внутри пары скобок не было точки.
     * @param checkChar
     * @return
     */
    public Boolean isPointBetweenBracket(char checkChar) {
        return checkChar != '.' || bracketCount > 0;
    }
    // Проверяем, что символ это скобка
    public Boolean isBracket(char checkChar) {
        return checkChar == '(' || checkChar == ')';
    }
}
