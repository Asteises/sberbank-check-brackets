package ru.asteises.checkbrackets.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextDto {

    @NotBlank(message = "текст не должен быть пустым")
    private String text;

    @Override
    public String toString() {
        return "TextDto{" +
                "text='" + text + '\'' +
                '}';
    }
}
