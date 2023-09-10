package ru.asteises.checkbrackets.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.checkbrackets.service.BracketsService;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
@Service
public class BracketsServiceImpl implements BracketsService {

    private int bracketCount;
    private boolean isText;
    @Override
    public Boolean checkBrackets(String text) {
        char[] chars = text.toCharArray();
        if (!isLastCharIsPoint(chars)) {
            log.info("Текст должен заканчиваться точкой");
            return false;
        }
        for (char checkChar: chars) {
            if (isLetterOrDigit(checkChar)) {
                log.info("Это действительно буква или цифра: {}", checkChar);
                setText(true);
            }
            if (isItLastPoint(checkChar)) {
                log.info("Нельзя поставить точку внутри скобок");
                return false;
            }
            if (isItBracket(checkChar)) {
                if (!isItOpenBracket(checkChar) && getBracketCount() == 0) {
                    log.info("Первая скобка в тексте оказалась закрывающей - не годиться");
                    return false;
                }
                if (isItOpenBracket(checkChar) && isText()) {
                    setBracketCount(getBracketCount() + 1);
                    log.info("Найдена открывающая скобка, увеличиваем флаг: {}", getBracketCount());
                    setText(false);
                }
                // Если это закрывающая скобка и между скобками был текст, то уменьшаем count
                if (!isItOpenBracket(checkChar) && isText()) {
                    setBracketCount(getBracketCount() - 1);
                    log.info("Найдена закрывающая скобка, пара закрылась: {}", getBracketCount());
                }
            }
        }
        if (getBracketCount() == 0) {
            log.info("Все пары скобок закрылись, или это был текст без скобок. В любом случае, поздравляю!");
            return true;
        } else {
            log.info("Где-то не закрыли скобку: {}", getBracketCount());
            return false;
        }
    }

    // Проверяем, что это открывающая скобка
    public Boolean isItOpenBracket(char checkChar) {
        return checkChar == '(';
    }
    // Проверяем, что символ, это буква или цифра
    public Boolean isLetterOrDigit(char c) {
        return Character.isLetter(c) || Character.isAlphabetic(c) || Character.isDigit(c);
    }
    // Проверяем, что текст заканчивается точкой
    public Boolean isLastCharIsPoint(char[] chars) {
        return chars[chars.length - 1] == '.';
    }
    // Проверяем, что точка появляется до закрытия каждой второй скобки
    public Boolean isItLastPoint(char checkChar) {
        return checkChar != '.' || getBracketCount() % 2 == 0;
    }
    // Проверяем, что символ это скобка
    public Boolean isItBracket(char checkChar) {
        return checkChar == '(' || checkChar == ')';
    }
}
