package ru.asteises.checkbrackets.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.checkbrackets.model.TextDto;
import ru.asteises.checkbrackets.service.BracketsService;

import java.util.Stack;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
@Service
public class BracketsServiceImpl implements BracketsService {

    @Override
    public Boolean checkBrackets(TextDto text) {
        Stack<Character> charsBetweenBrackets = new Stack<>();
        int bracketCount = 0;
        char checkChar;
        for (int i = 0; i < text.getText().length(); i++) {
            checkChar = text.getText().charAt(i);
            if (isBracket(checkChar)) {
                if (isOpenBracket(checkChar) && !charsBetweenBrackets.isEmpty()) {
                    bracketCount += 1;
                    log.info("Найдена открывающая скобка, увеличиваем флаг: {}", bracketCount);
                    charsBetweenBrackets.clear();
                } else if (!isOpenBracket(checkChar) && !charsBetweenBrackets.isEmpty() && bracketCount != 0) {
                    bracketCount -= 1;
                    log.info("Найдена закрывающая скобка, пара закрылась: {}", bracketCount);
                } else if (!isOpenBracket(checkChar) && bracketCount != 0 && charsBetweenBrackets.isEmpty()) {
                    log.info("Между скобками нет текста");
                    return false;
                } else if (!isOpenBracket(checkChar) && !charsBetweenBrackets.isEmpty() && bracketCount == 0) {
                    log.info("Первая скобка в тексте оказалась закрывающей - не годиться");
                    return false;
                }
            } else if (isPointBetweenBracket(checkChar, bracketCount)) {
                log.info("Нельзя поставить точку внутри скобок");
                return false;
            } else {
                charsBetweenBrackets.push(checkChar);
            }
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
     * @param checkChar - char
     * @return - boolean
     */
    private Boolean isOpenBracket(char checkChar) {
        return checkChar == '(';
    }

    /**
     * Метод проверяет, чтобы внутри пары скобок не было точки.
     *
     * @param checkChar    - char
     * @param bracketCount - int
     * @return - Boolean
     */
    private Boolean isPointBetweenBracket(char checkChar, int bracketCount) {
        return checkChar == '.' && bracketCount > 0;
    }

    /**
     * Проверяем, что символ это скобка.
     *
     * @param checkChar - char
     * @return - Boolean
     */
    private Boolean isBracket(char checkChar) {
        return checkChar == '(' || checkChar == ')';
    }
}
