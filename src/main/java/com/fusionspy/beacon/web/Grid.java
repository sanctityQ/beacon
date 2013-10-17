package com.fusionspy.beacon.web;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于dhtmlx-grid
 * User: qc
 * Date: 11-8-28
 * Time: 下午12:51
 */
@XmlRootElement(name = "rows")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Grid {

    public Grid(){
    }

    public Grid(String totalCount){
        this(totalCount,null);
    }

    public Grid(String totalCount, String pos) {
        this.totalCount = totalCount;
        this.pos = pos;
    }

    @XmlAttribute(name = "total_count")
    private String totalCount;

    @XmlAttribute(name = "pos")
    private String pos;

    @XmlElement(name = "row")
    private List<Row> rowList;

    public void addRow(Row row){
        if(this.rowList == null){
            rowList = new ArrayList<Row>();
        }
        rowList.add(row);
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class Row {

        public Row(){
        }

        public Row(String id) {
            this(id, null, null);
        }

        public Row(String id, String cssClass, String style) {
            this.id = id;
            this.cssClass = cssClass;
            this.style = style;
        }

        @XmlAttribute(name = "id", required = true)
        //id of the row (must be unique).
        private String id;

        @XmlAttribute(name = "class")
        //css class name
        private String cssClass;

        @XmlAttribute(name = "style")
        //css style definition
        private String style;

        @XmlElement(name = "cell")
        private List<Cell> cellList;

        public void addCell(Cell cell){
            if(cellList == null){
                cellList = new ArrayList<Cell>();
            }
            this.cellList.add(cell);
        }
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class Cell {

        public Cell(){}

        public Cell(String value) {
            this(null, null, value);
        }

        public Cell(String style, String type, String value) {
            this.style = style;
            this.type = type;
            this.value = value;
        }

        @XmlAttribute(name = "style")
        //css style definition
        private String style;

        @XmlAttribute(name = "type")
        private String type;

        @XmlValue
        private String value;

    }

}
