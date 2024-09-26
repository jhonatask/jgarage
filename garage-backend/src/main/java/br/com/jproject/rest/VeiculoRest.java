package br.com.jproject.rest;

import br.com.jproject.dtos.filter.VeiculoFilterDTO;
import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.request.VeiculoUpdateDTO;
import jakarta.ws.rs.core.Response;

public interface VeiculoRest {

    Response pesquisarVeiculo(VeiculoFilterDTO filtro);
    Response getVeiculo(Long id);
    Response createVeiculo(VeiculoRequestDTO veiculo);
    Response updateVeiculo(Long id, VeiculoUpdateDTO veiculo);
    Response deleteVeiculo(Long id);
    Response patchVeiculo(Long id, VeiculoUpdateDTO veiculo);
    Response veiculosNaoVendidos();
    Response distribruicaoPorDecada();
    Response distribuicaoPorFabricante();
    Response veiculosRegistradosUltimaSemana();
}
