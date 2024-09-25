package br.com.jproject.mapper;

import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.response.VeiculoResponseDTO;
import br.com.jproject.entity.VeiculoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface VeiculoMapper {


    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    VeiculoEntity veiculoRequestDTOToVeiculoEntity(VeiculoRequestDTO dto);
    VeiculoResponseDTO veiculoEntityToVeiculoResponseDTO(VeiculoEntity entity);
    VeiculoRequestDTO veiculoEntityToVeiculoRequestDTO(VeiculoEntity entity);

}
