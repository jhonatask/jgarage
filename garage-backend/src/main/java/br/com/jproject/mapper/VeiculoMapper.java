package br.com.jproject.mapper;

import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.response.VeiculoResponseDTO;
import br.com.jproject.entity.VeiculoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "cdi")
public interface  VeiculoMapper {


    VeiculoEntity veiculoRequestDTOToVeiculoEntity(VeiculoRequestDTO entity);

    @Mapping(target = "createdData", expression = "java(formatLocalDateTime(entity.getCreated()))")
    @Mapping(target = "updatedData", expression = "java(formatLocalDateTime(entity.getUpdated()))")
    VeiculoResponseDTO veiculoEntityToVeiculoResponseDTO(VeiculoEntity entity);
    VeiculoRequestDTO veiculoEntityToVeiculoRequestDTO(VeiculoEntity entity);



    default String formatLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
