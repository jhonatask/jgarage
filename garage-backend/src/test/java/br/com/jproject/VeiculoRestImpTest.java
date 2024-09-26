package br.com.jproject;

import br.com.jproject.dtos.filter.VeiculoFilterDTO;
import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.request.VeiculoUpdateDTO;
import br.com.jproject.dtos.response.VeiculoResponseDTO;
import br.com.jproject.pagination.PageResult;
import br.com.jproject.rest.VeiculoRestImp;
import br.com.jproject.services.VeiculoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@QuarkusTest
public class VeiculoRestImpTest {

    @InjectMock
    private VeiculoService veiculoService;

    @Inject
    private VeiculoRestImp veiculoRestImp;

    @BeforeEach
    void setUp() {
        // Inicie os mocks
        org.mockito.MockitoAnnotations.openMocks(this);
    }

    @Test
    void pesquisarVeiculo_returnsVeiculos_whenFound() {
        VeiculoFilterDTO filtro = new VeiculoFilterDTO();
        PageResult<VeiculoResponseDTO> pageResult = new PageResult<>(List.of(new VeiculoResponseDTO()), 1, 1, 1L);
        when(veiculoService.getVeiculos(filtro)).thenReturn(pageResult);

        Response response = veiculoRestImp.pesquisarVeiculo(filtro);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(pageResult, response.getEntity());
    }

    @Test
    void pesquisarVeiculo_returnsNotFound_whenNoVeiculosFound() {
        VeiculoFilterDTO filtro = new VeiculoFilterDTO();
        PageResult<VeiculoResponseDTO> pageResult = new PageResult<>(Collections.emptyList(), 0, 0, 0);
        when(veiculoService.getVeiculos(filtro)).thenReturn(pageResult);

        Response response = veiculoRestImp.pesquisarVeiculo(filtro);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void getVeiculo_returnsVeiculo_whenFound() {
        Long id = 1L;
        VeiculoResponseDTO veiculo = new VeiculoResponseDTO();
        when(veiculoService.getVeiculo(id)).thenReturn(veiculo);

        Response response = veiculoRestImp.getVeiculo(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(veiculo, response.getEntity());
    }

    @Test
    void getVeiculo_returnsNotFound_whenVeiculoNotFound() {
        Long id = 1L;
        when(veiculoService.getVeiculo(id)).thenReturn(null);

        Response response = veiculoRestImp.getVeiculo(id);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void createVeiculo_returnsCreatedVeiculo_whenValid() {
        VeiculoRequestDTO veiculoRequest = new VeiculoRequestDTO();
        VeiculoResponseDTO veiculoResponse = new VeiculoResponseDTO();
        when(veiculoService.createVeiculo(veiculoRequest)).thenReturn(veiculoResponse);

        Response response = veiculoRestImp.createVeiculo(veiculoRequest);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(veiculoResponse, response.getEntity());
    }

    @Test
    void createVeiculo_returnsBadRequest_whenInvalid() {
        VeiculoRequestDTO veiculoRequest = new VeiculoRequestDTO();
        when(veiculoService.createVeiculo(veiculoRequest)).thenReturn(null);

        Response response = veiculoRestImp.createVeiculo(veiculoRequest);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void updateVeiculo_returnsUpdatedVeiculo_whenFound() {
        Long id = 1L;
        VeiculoUpdateDTO veiculoUpdate = new VeiculoUpdateDTO();
        VeiculoResponseDTO veiculoResponse = new VeiculoResponseDTO();
        when(veiculoService.updateVeiculo(id, veiculoUpdate)).thenReturn(veiculoResponse);

        Response response = veiculoRestImp.updateVeiculo(id, veiculoUpdate);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(veiculoResponse, response.getEntity());
    }

    @Test
    void updateVeiculo_returnsNotFound_whenVeiculoNotFound() {
        Long id = 1L;
        VeiculoUpdateDTO veiculoUpdate = new VeiculoUpdateDTO();
        when(veiculoService.updateVeiculo(id, veiculoUpdate)).thenReturn(null);

        Response response = veiculoRestImp.updateVeiculo(id, veiculoUpdate);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void deleteVeiculo_returnsNoContent_whenDeleted() {
        Long id = 1L;
        when(veiculoService.deleteVeiculo(id)).thenReturn(true);

        Response response = veiculoRestImp.deleteVeiculo(id);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void deleteVeiculo_returnsNotFound_whenVeiculoNotFound() {
        Long id = 1L;
        when(veiculoService.deleteVeiculo(id)).thenReturn(false);

        Response response = veiculoRestImp.deleteVeiculo(id);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void patchVeiculo_returnsUpdatedVeiculo_whenFound() {
        Long id = 1L;
        VeiculoUpdateDTO veiculoUpdate = new VeiculoUpdateDTO();
        VeiculoResponseDTO veiculoResponse = new VeiculoResponseDTO();
        when(veiculoService.patchVeiculo(id, veiculoUpdate)).thenReturn(veiculoResponse);

        Response response = veiculoRestImp.patchVeiculo(id, veiculoUpdate);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(veiculoResponse, response.getEntity());
    }

    @Test
    void patchVeiculo_returnsNotFound_whenVeiculoNotFound() {
        Long id = 1L;
        VeiculoUpdateDTO veiculoUpdate = new VeiculoUpdateDTO();
        when(veiculoService.patchVeiculo(id, veiculoUpdate)).thenReturn(null);

        Response response = veiculoRestImp.patchVeiculo(id, veiculoUpdate);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void veiculosNaoVendidos_returnsCount() {
        long count = 10L;
        when(veiculoService.veiculosNaoVendidos()).thenReturn(count);

        Response response = veiculoRestImp.veiculosNaoVendidos();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(count, response.getEntity());
    }

    @Test
    void distribruicaoPorDecada_returnsDistribution() {
        Map<String, Long> distribution = Map.of("1990s", 15L, "2000s", 31L);
        when(veiculoService.distribuicaoPorDecada()).thenReturn(distribution);

        Response response = veiculoRestImp.distribruicaoPorDecada();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(distribution, response.getEntity());
    }

    @Test
    void distribuicaoPorFabricante_returnsDistribution() {
        Map<String, Long> distribution = Map.of("Ford", 14L, "Honda", 8L);
        when(veiculoService.distribuicaoPorFabricante()).thenReturn(distribution);

        Response response = veiculoRestImp.distribuicaoPorFabricante();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(distribution, response.getEntity());
    }

    @Test
    void veiculosRegistradosUltimaSemana_returnsVeiculos() {
        List<VeiculoResponseDTO> veiculos = List.of(new VeiculoResponseDTO());
        when(veiculoService.veiculosRegistradosUltimaSemana()).thenReturn(veiculos);

        Response response = veiculoRestImp.veiculosRegistradosUltimaSemana();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(veiculos, response.getEntity());
    }
}
