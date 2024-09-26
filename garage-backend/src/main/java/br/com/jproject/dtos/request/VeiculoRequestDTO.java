package br.com.jproject.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Max(value = Year.MAX_VALUE, message = "O ano não pode ser no futuro.")
    @Length(min = 4, max = 4, message = "O ano deve ter 4 dígitos.")
    private Integer ano;
    @NotBlank(message = "A cor do veículo é obrigatória.")
    private String cor;
    private String descricao;
}
