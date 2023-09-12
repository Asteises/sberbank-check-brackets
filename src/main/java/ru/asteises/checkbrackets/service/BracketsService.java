package ru.asteises.checkbrackets.service;

import ru.asteises.checkbrackets.model.TextDto;

public interface BracketsService {
    Boolean checkBrackets(TextDto text);
}
