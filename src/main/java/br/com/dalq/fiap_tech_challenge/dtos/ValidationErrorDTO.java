package br.com.dalq.fiap_tech_challenge.dtos;

import java.util.List;

public record ValidationErrorDTO(
        List<String> errors,
        int status
) {
}
