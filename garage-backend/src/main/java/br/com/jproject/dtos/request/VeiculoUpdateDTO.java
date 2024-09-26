package br.com.jproject.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VeiculoUpdateDTO {
    private String veiculo;
    private String marca;
    private Integer ano;
    private String cor;
    private String descricao;
    private Boolean vendido;
}
