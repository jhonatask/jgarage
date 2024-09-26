package br.com.jproject.factory;

import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.entity.VeiculoEntity;

import java.time.LocalDateTime;


public class VeiculoFactory {

    public static VeiculoEntity createVeiculo(VeiculoRequestDTO veiculoRequest) {
        VeiculoEntity novoVeiculo = new VeiculoEntity();
        novoVeiculo.setVeiculo(veiculoRequest.getVeiculo());
        novoVeiculo.setMarca(veiculoRequest.getMarca());
        novoVeiculo.setAno(veiculoRequest.getAno());
        novoVeiculo.setCor(veiculoRequest.getCor());
        novoVeiculo.setDescricao(veiculoRequest.getDescricao());
        novoVeiculo.setVendido(false);
        novoVeiculo.setCreated(LocalDateTime.now());
        return novoVeiculo;
    }
}
