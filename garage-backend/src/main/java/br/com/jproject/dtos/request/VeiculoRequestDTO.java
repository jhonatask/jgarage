package br.com.jproject.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VeiculoRequestDTO {
    @NotBlank(message = "O nome do veículo é obrigatório.")
    private String veiculo;

    @NotBlank(message = "A marca do veículo é obrigatória.")
    private String marca;

    @NotNull(message = "O ano de fabricação é obrigatório.")
    @Min(value = 1886, message = "O ano deve ser maior ou igual a 1886.")
    @Max(value = Year.MAX_VALUE, message = "O ano não pode ser no futuro.")
    private Integer ano;

    private String descricao;

    @NotNull(message = "O status de venda (vendido) é obrigatório.")
    private Boolean vendido;
}
