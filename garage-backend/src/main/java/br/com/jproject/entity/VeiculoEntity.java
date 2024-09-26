package br.com.jproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "veiculo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String veiculo;
    private String marca;
    private int ano;
    private String cor;
    private String descricao;
    private boolean vendido;
    private LocalDateTime created;
    private LocalDateTime updated;

}
