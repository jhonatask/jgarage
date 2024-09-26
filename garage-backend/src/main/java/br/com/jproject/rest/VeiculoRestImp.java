package br.com.jproject.rest;

import br.com.jproject.dtos.filter.VeiculoFilterDTO;
import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.request.VeiculoUpdateDTO;
import br.com.jproject.dtos.response.VeiculoResponseDTO;
import br.com.jproject.pagination.PageResult;
import br.com.jproject.services.VeiculoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@Path("/veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Garage API", description = "Operações relacionadas ao gerenciamento de veículos")
public class VeiculoRestImp implements VeiculoRest {


    private final VeiculoService veiculoService;

    @Inject
    public VeiculoRestImp(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @Operation(summary = "Pesquisar Veiculo", description = "Pesquisar Veiculo")
    @APIResponse(
            responseCode = "200",
            description = "Lista de veículos retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VeiculoResponseDTO.class)))
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response pesquisarVeiculo(VeiculoFilterDTO filtro) {
        PageResult<VeiculoResponseDTO> veiculos = veiculoService.getVeiculos(filtro);
        if(veiculos.result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensagem\":\"Não foi encontrado veículo para os filtros fornecidos.\"}")
                    .build();
        }
        return Response.status(Response.Status.OK).entity(veiculos).build();
    }

    @Operation(summary = "Busca um veículo por ID", description = "Retorna os detalhes de um veículo com base no ID fornecido.")
    @Parameter(name = "id", description = "ID do veículo", required = true, schema = @Schema(implementation = Integer.class))
    @APIResponse(
            responseCode = "200",
            description = "Veículo encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VeiculoResponseDTO.class))
    )
    @APIResponse(
            responseCode = "404",
            description = "Veículo não encontrado"
    )
    @Override
    @GET
    @Path("/{id}")
    public Response getVeiculo(@PathParam("id") Long id) {
        VeiculoResponseDTO veiculo = veiculoService.getVeiculo(id);
        if (veiculo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensagem\":\"Veículo não encontrado.\"}")
                    .build();
        }
        return Response.status(Response.Status.OK).entity(veiculo).build();
    }

    @POST
    @Operation(summary = "Cadastra um novo veículo", description = "Permite cadastrar um novo veículo no sistema.")
    @APIResponse(
            responseCode = "201",
            description = "Veículo cadastrado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VeiculoRequestDTO.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
    )
    @Override
    public Response createVeiculo(VeiculoRequestDTO veiculoRequest) {
        VeiculoResponseDTO veiculoResponse = veiculoService.createVeiculo(veiculoRequest);
        if (veiculoResponse == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"mensagem\":\"Dados inválidos fornecidos.\"}")
                    .build();
        }
        return Response.status(Response.Status.CREATED).entity(veiculoResponse).build();
    }
    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza totalmente os dados de um veículo", description = "Atualiza todas as informações de um veículo pelo seu ID.")
    @APIResponse(
            responseCode = "200",
            description = "Veículo atualizado  com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VeiculoRequestDTO.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
    )
    @APIResponse(
            responseCode = "404",
            description = "Veículo não encontrado"
    )
    @Override
    public Response updateVeiculo(@PathParam("id") Long id, VeiculoUpdateDTO veiculo) {
        VeiculoResponseDTO veiculoAtualizado = veiculoService.updateVeiculo(id, veiculo);
        if (veiculoAtualizado == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensagem\":\"Veículo não encontrado.\"}")
                    .build();
        }
        return Response.status(Response.Status.OK).entity(veiculoAtualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deleta um veículo por ID", description = "Remove um veículo existente com base no ID.")
    @APIResponse(
            responseCode = "204",
            description = "Veículo deletado com sucesso"
    )
    @APIResponse(
            responseCode = "404",
            description = "Veículo não encontrado"
    )
    @Override
    public Response deleteVeiculo(@PathParam("id") Long id) {
         if(veiculoService.deleteVeiculo(id)){
             return Response.noContent().build();
         }
         return Response.status(Response.Status.NOT_FOUND)
                 .entity("{\"mensagem\":\"Veículo não encontrado.\"}")
                 .build();

    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "Atualiza parcialmente os dados de um veículo", description = "Atualiza apenas os campos fornecidos de um veículo pelo seu ID.")
    @APIResponse(
            responseCode = "200",
            description = "Veículo atualizado  com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VeiculoRequestDTO.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Dados inválidos fornecidos"
    )
    @APIResponse(
            responseCode = "404",
            description = "Veículo não encontrado"
    )
    @Override
    public Response patchVeiculo(@PathParam("id") Long id, VeiculoUpdateDTO veiculo) {
        VeiculoResponseDTO veiculoResponseDTO = veiculoService.patchVeiculo(id, veiculo);
        if (veiculoResponseDTO == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensagem\":\"Veículo não encontrado.\"}")
                    .build();
        }
        return Response.status(Response.Status.OK).entity(veiculoResponseDTO).build();
    }

    @GET
    @Path("/nao-vendidos")
    @Operation(summary = "Informacao de quantos veiculos vendidos", description = "Retorna a quantidade de veículos não vendidos.")
    @APIResponse(
            responseCode = "200",
            description = "quantidade  retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Override
    public Response veiculosNaoVendidos() {
        long countVeiculosNaoVendidos = veiculoService.veiculosNaoVendidos();
        return Response.status(Response.Status.OK).entity(countVeiculosNaoVendidos).build();
    }

    @GET
    @Path("/distribuicao-decadas")
    @Operation(summary = "Distribuição de veículos por década", description = "Retorna a distribuição de veículos por década de fabricação.")
    @APIResponse(
            responseCode = "200",
            description = "Distribuição de veículos por década retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Override
    public Response distribruicaoPorDecada() {
        Map<String, Long> distribuicao = veiculoService.distribuicaoPorDecada();
        return Response.status(Response.Status.OK).entity(distribuicao).build();
    }

    @GET
    @Path("/distribuicao-fabricante")
    @Operation(summary = "Distribuição de veículos por fabricante", description = "Retorna a distribuição de veículos por fabricante.")
    @APIResponse(
            responseCode = "200",
            description = "Distribuição de veículos por fabricante retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Override
    public Response distribuicaoPorFabricante() {
        Map<String, Long> distribuicao = veiculoService.distribuicaoPorFabricante();
        return Response.status(Response.Status.OK).entity(distribuicao).build();
    }

    @GET
    @Path("/ultima-semana")
    @Operation(summary = "Veiculos Cadastrados na ultima semana", description = "Retorna os veiculos cadastrados na ultima semana.")
    @APIResponse(
            responseCode = "200",
            description = "Distribuição de veículos por década retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Override
    public Response veiculosRegistradosUltimaSemana() {
        List<VeiculoResponseDTO> veiculosUltimaSemana = veiculoService.veiculosRegistradosUltimaSemana();
        return Response.status(Response.Status.OK).entity(veiculosUltimaSemana).build();
    }


}
