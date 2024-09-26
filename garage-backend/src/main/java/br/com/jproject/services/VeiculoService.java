package br.com.jproject.services;

import br.com.jproject.dtos.filter.VeiculoFilterDTO;
import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.request.VeiculoUpdateDTO;
import br.com.jproject.dtos.response.VeiculoResponseDTO;
import br.com.jproject.entity.VeiculoEntity;
import br.com.jproject.exceptions.VeiculoNotFoundException;
import br.com.jproject.factory.VeiculoFactory;
import br.com.jproject.mapper.VeiculoMapper;
import br.com.jproject.pagination.PageResult;
import br.com.jproject.repositories.VeiculoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper veiculoMapper;

    private static final List<String> MARCAS_VALIDAS = Arrays.asList(
            "Toyota",
            "Volkswagen",
            "Ford",
            "Honda",
            "Chevrolet",
            "Nissan",
            "BMW",
            "Mercedes-Benz",
            "Audi",
            "Hyundai",
            "Kia",
            "Tesla",
            "Peugeot",
            "Renault",
            "Fiat",
            "Jeep",
            "Subaru",
            "Mazda",
            "Land Rover",
            "Volvo"
    );


    @Inject
    public VeiculoService(VeiculoRepository veiculoRepository, VeiculoMapper veiculoMapper) {
        this.veiculoRepository = veiculoRepository;
        this.veiculoMapper = veiculoMapper;
    }

    public PageResult<VeiculoResponseDTO> getVeiculos(VeiculoFilterDTO filtro) {
        PageResult<VeiculoEntity> veiculos = veiculoRepository.searchVeiculos(filtro);
        return PageResult.from(veiculos, veiculos.result.stream()
                .map(veiculoMapper::veiculoEntityToVeiculoResponseDTO)
                .toList());
    }

    public VeiculoResponseDTO getVeiculo(Long id) {
        VeiculoEntity veiculo = veiculoRepository.findById(id);
        return veiculoMapper.veiculoEntityToVeiculoResponseDTO(veiculo);
    }

    public VeiculoResponseDTO createVeiculo(VeiculoRequestDTO veiculo) {
        if (marcaIsValida(veiculo.getMarca())) {
            throw new IllegalArgumentException("Marca inválida");
        }
        VeiculoEntity newVeiculo = VeiculoFactory.createVeiculo(veiculo);
        veiculoRepository.persist(newVeiculo);
        return veiculoMapper.veiculoEntityToVeiculoResponseDTO(newVeiculo);
    }

    private boolean marcaIsValida(String marca) {
        return !MARCAS_VALIDAS.contains(marca);
    }

    public VeiculoResponseDTO updateVeiculo(Long id, VeiculoUpdateDTO veiculo) {
        VeiculoEntity veiculoEntity = veiculoRepository.findById(id);
        validaEntityIsPresent(veiculoEntity);
        validaMarca(veiculo);
        VeiculoEntity veiculoAtualizado = veiculoRepository.updateVeiculo(id, veiculo);
        return veiculoMapper.veiculoEntityToVeiculoResponseDTO(veiculoAtualizado);
    }

    public VeiculoResponseDTO patchVeiculo(Long id, VeiculoUpdateDTO veiculo) {
        VeiculoEntity veiculoEntity = veiculoRepository.findById(id);
        validaEntityIsPresent(veiculoEntity);
        validaMarca(veiculo);
        VeiculoEntity veiculoAtualizado = veiculoRepository.patchVeiculo(id, veiculo);
        return veiculoMapper.veiculoEntityToVeiculoResponseDTO(veiculoAtualizado);
    }

    private void validaMarca(VeiculoUpdateDTO veiculo) {
        if (marcaIsValida(veiculo.getMarca())) {
            throw new IllegalArgumentException("Marca inválida");
        }
    }

    @Transactional
    public boolean deleteVeiculo(Long id) {
       return veiculoRepository.deleteById(id);
    }

    private static void validaEntityIsPresent(VeiculoEntity veiculoEntity) {
        if (veiculoEntity == null) {
            throw new VeiculoNotFoundException("Veículo não encontrado");
        }
    }

    public List<VeiculoResponseDTO> veiculosRegistradosUltimaSemana() {
        List<VeiculoEntity> veiculos = veiculoRepository.findVeiculosRegistradosUltimaSemana();
        return veiculos.stream()
                .map(veiculoMapper::veiculoEntityToVeiculoResponseDTO)
                .toList();
    }

    public Map<String, Long> distribuicaoPorDecada() {
        List<VeiculoEntity> veiculos = veiculoRepository.listAll();
        return veiculos.stream()
                .collect(Collectors.groupingBy(
                        veiculo -> (veiculo.getAno() / 10 * 10) + "s",
                        Collectors.counting()
                ));
    }

    public Map<String, Long> distribuicaoPorFabricante() {
        List<VeiculoEntity> veiculos = veiculoRepository.listAll();
        return veiculos.stream()
                .collect(Collectors.groupingBy(
                        VeiculoEntity::getMarca,
                        Collectors.counting()
                ));
    }

    public long veiculosNaoVendidos() {
        return veiculoRepository.count("vendido", false);
    }
}
