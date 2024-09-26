package br.com.jproject.dtos.filter;

import br.com.jproject.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;

public class VeiculoFilterDTO extends PageQuery {
    @QueryParam("marca")
    public String marca;
    @QueryParam("ano")
    public Integer ano;
    @QueryParam("cor")
    public String cor;
}
