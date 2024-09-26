package br.com.jproject.pagination;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PageQuery {
    @QueryParam("page")
    @DefaultValue("0")
    public Integer page;

    @QueryParam("pageSize")
    @DefaultValue("20")
    public Integer pageSize;
}
