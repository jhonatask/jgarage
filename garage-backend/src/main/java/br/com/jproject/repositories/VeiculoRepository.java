package br.com.jproject.repositories;

import br.com.jproject.dtos.filter.VeiculoFilterDTO;
import br.com.jproject.dtos.request.VeiculoRequestDTO;
import br.com.jproject.dtos.request.VeiculoUpdateDTO;
import br.com.jproject.entity.VeiculoEntity;
import br.com.jproject.mapper.VeiculoMapper;
import br.com.jproject.pagination.PageResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@ApplicationScoped
public class VeiculoRepository implements PanacheRepositoryBase<VeiculoEntity, Long> {


    private final VeiculoMapper veiculoMapper;

    @Inject
    public VeiculoRepository(VeiculoMapper veiculoMapper) {
        this.veiculoMapper = veiculoMapper;
    }

    public PageResult<VeiculoEntity> searchVeiculos(VeiculoFilterDTO filtro) {

        StringJoiner query = new StringJoiner(" ");
        query.add("(:marca IS NULL OR marca = :marca)");
        query.add("AND (:ano IS NULL OR ano = :ano)");
        query.add("AND (:cor IS NULL OR cor = :cor)");
        query.add("ORDER BY created DESC");

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("marca", filtro.marca);
        parametros.put("ano", filtro.ano);
        parametros.put("cor", filtro.cor);

        Page page = new Page(filtro.page,filtro.pageSize);
        PanacheQuery<VeiculoEntity> produto = find(query.toString(), parametros);

        PageResult<VeiculoEntity> resultado = new PageResult<>();
        resultado.result = produto.page(page).list();
        resultado.pageTotal = produto.pageCount();
        resultado.page = page.index;
        resultado.pageSize = page.size;

        return resultado;
    }

    public List<VeiculoEntity> findByMarcaAnoCor(String marca, Integer ano, String cor) {
        return find("marca = ?1 and ano = ?2 and cor = ?3", marca, ano, cor).list();
    }

    @Transactional
    public VeiculoEntity updateVeiculo(Long id, VeiculoUpdateDTO veiculoUpdateDTO) {
        VeiculoEntity veiculoEntity = findById(id);
        veiculoEntity.setVeiculo(veiculoUpdateDTO.getVeiculo());
        veiculoEntity.setMarca(veiculoUpdateDTO.getMarca());
        veiculoEntity.setAno(veiculoUpdateDTO.getAno());
        veiculoEntity.setCor(veiculoUpdateDTO.getCor());
        veiculoEntity.setDescricao(veiculoUpdateDTO.getDescricao());
        veiculoEntity.setVendido(veiculoUpdateDTO.getVendido());
        veiculoEntity.setUpdated(LocalDateTime.now());
        persist(veiculoEntity);
        return veiculoEntity;
    }

    @Transactional
    public VeiculoEntity patchVeiculo(Long id, VeiculoUpdateDTO veiculo) {
        VeiculoEntity veiculoEntity = findById(id);
        if (veiculo.getVeiculo() != null) {
            veiculoEntity.setVeiculo(veiculo.getVeiculo());
        }
        if (veiculo.getMarca() != null) {
            veiculoEntity.setMarca(veiculo.getMarca());
        }
        if (veiculo.getAno() != null) {
            veiculoEntity.setAno(veiculo.getAno());
        }
        if (veiculo.getCor() != null) {
            veiculoEntity.setCor(veiculo.getCor());
        }
        if (veiculo.getDescricao() != null) {
            veiculoEntity.setDescricao(veiculo.getDescricao());
        }
        if (veiculo.getVendido() != null) {
            veiculoEntity.setVendido(veiculo.getVendido());
        }
        veiculoEntity.setUpdated(LocalDateTime.now());
        persist(veiculoEntity);
        return veiculoEntity;
    }


    public List<VeiculoEntity> findVeiculosRegistradosUltimaSemana() {
        return find("created >= ?1", LocalDateTime.now().minusDays(7)).list();
    }
}
