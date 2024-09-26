package br.com.jproject.pagination;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PageResult<T> {
    public int page;
    public int pageSize;
    public int pageTotal;
    public List<T> result;

    public PageResult(List<T> veiculoResponseDTOS, int i, int i1, long l) {
        if (veiculoResponseDTOS != null) {
            this.result = veiculoResponseDTOS;
        }
    }


    public static <U> PageResult<U> from(PageResult<?> pageResult, List<U> result) {
        PageResult<U> convertedPageResult = new PageResult<>();
        convertedPageResult.page = pageResult.page;
        convertedPageResult.pageSize = pageResult.pageSize;
        convertedPageResult.pageTotal = pageResult.pageTotal;
        convertedPageResult.result = result;
        return convertedPageResult;
    }


}
