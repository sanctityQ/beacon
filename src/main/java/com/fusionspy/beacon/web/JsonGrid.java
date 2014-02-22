package com.fusionspy.beacon.web;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-10-17
 * Time: 下午11:26
 */
public class JsonGrid {

    private int total;

    private List<JsonRow> rows = new ArrayList<JsonRow>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<JsonRow> getRows() {
        return rows;
    }

    public void addRow(JsonRow row) {
        rows.add(row);
    }

    public static class JsonRow {
        private String id;

        private List<String> cell = new ArrayList<String>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getCell() {
            return cell;
        }

        public void addCell(String cell) {
            this.cell.add(cell);
        }
    }

    public static interface JsonRowHandler<T> {

        public JsonRow buildRow(T t);
    }

    public static JsonGrid buildGrid(List recordList, JsonRowHandler handler) {
        if(recordList!=null) {
            JsonGrid grid = new JsonGrid();
            grid.setTotal(recordList.size());
            for(Object o : recordList) {
                grid.addRow(handler.buildRow(o));
            }
            return grid;
        }
        return null;
    }
}
