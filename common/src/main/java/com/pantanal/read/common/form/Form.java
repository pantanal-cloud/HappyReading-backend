package com.pantanal.read.common.form;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gudong
 */
public class Form {
    private Long[] ids;
    private Long id;
    private int page = 1;// easyui pagination: The page number, start with 1.
    private int rows = 10;// eaysui pagination: The page rows per page.
    private Integer draw;// dataTables datasource ajax param
    private Integer start;// dataTables datasource ajax param
    private Integer length;// dataTables datasource ajax param

    private List<Column> columns = new ArrayList<Column>();
    private Search search;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPageStart() {
        if (isDataTable()) {
            return getStart();
        } else {
            return (getPage() - 1) * getRows();
        }
    }

    public int getPageSize() {
        if (isDataTable()) {
            return getLength();
        } else {
            return getRows();
        }
    }

    public boolean isDataTable() {
        return getDraw() != null;
    }
}
