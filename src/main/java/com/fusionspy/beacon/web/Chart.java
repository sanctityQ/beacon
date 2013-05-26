package com.fusionspy.beacon.web;



import com.sinosoft.one.util.encode.JaxbBinder;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * used for amChart
 * User: qc
 *
 * Date: 11-10-10
 * Time: 上午11:47
 */

public abstract class Chart {

    private List<Value>  series = new ArrayList<Value>();;

    private int s_id = 0;

    protected int g_id = 0;

    protected static final String[] CHART_LINE_COLOR={"#FF6600","#330066","#008800","#cc0099","#990033",
            "#ff6699","#9900cc","#333366","#0033ff","#00cc99","#ccffcc","#ccccff","#cc9909","#663300"};

    @XmlElementWrapper(name="series")
    @XmlElement(name="value")
    public List<Value> getSeries() {
        return series;
    }

    public void addSeries(Value serie){
        serie.setId(String.valueOf(s_id++));
        series.add(serie);
    }

    private void setSeries(List<Value> series) {
        this.series = series;
    }

    private   Chart(){}

    public abstract JaxbBinder getJaxbBinder();

    @XmlRootElement(name = "chart")
    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class ColumnChart extends Chart{

        private  List<ColumnGraph> graphs = new ArrayList<ColumnGraph>();

        private static JaxbBinder jaxbBinder =  new  JaxbBinder(ColumnChart.class);

        @XmlElementWrapper(name = "graphs")
        @XmlElement(name = "graph")
        public List<ColumnGraph> getGraphs() {
            return graphs;
        }

        private void setGraphs(List<ColumnGraph> graphs) {
            this.graphs = graphs;
        }

        public void addGraph(ColumnGraph graph) {
            graph.setId(String.valueOf(g_id++));
            graphs.add(graph);
        }

        @Override
        @XmlTransient
        public JaxbBinder getJaxbBinder(){
            return   jaxbBinder;
        }

    }

    @XmlRootElement(name = "chart")
    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class LineChart extends Chart {

        private List<Graph> graphs = new ArrayList<Graph>() ;

        private static JaxbBinder jaxbBinder =  new  JaxbBinder(LineChart.class);

        @XmlElementWrapper(name = "graphs")
        @XmlElement(name = "graph")
        public List<Graph> getGraphs() {
            return graphs;
        }

        private void setGraphs(List<Graph> graphs) {
            this.graphs = graphs;
        }

        public void addGraph(Graph graph) {
            graph.setId(String.valueOf(g_id++));
            graphs.add(graph);
        }

        @Override
        @XmlTransient
        public JaxbBinder getJaxbBinder() {
            return jaxbBinder;
        }
    }

    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class Graph{
        private String id;

        private List<Value>  values = new ArrayList<Value>();;

        protected int g_id = 0;

        @XmlAttribute(name = "gid")
        public String getId() {
            return id;
        }

        private void setId(String id) {
            this.id = id;
        }

        @XmlElement(name="value")
        public List<Value> getValues() {
            return values;
        }

        private void setValues(List<Value> values) {
            this.values = values;
        }

        public void addValue(Value value) {
            value.setId(String.valueOf(g_id++));
            values.add(value);
        }


    }


    public static class ColumnGraph{

        private String id;

        private List<ColumnValue>  values = new ArrayList<ColumnValue>();;

        protected int g_id = 0;

        @XmlAttribute(name = "gid")
        public String getId() {
            return id;
        }

        private void setId(String id) {
            this.id = id;
        }


        @XmlElement(name="value")
        public List<ColumnValue> getValues() {
            return values;
        }

        private void setValues(List<ColumnValue> values) {
            this.values = values;
        }

        public void addValue(ColumnValue value) {
            value.setId(String.valueOf(g_id));
            value.setColor(CHART_LINE_COLOR[g_id]);
            values.add(value);
            g_id++;
        }
    }




    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class Value{
         private String id;

        private String value;

        @XmlAttribute(name="xid")
        public String getId() {
            return id;
        }

        void setId(String id) {
            this.id = id;
        }

        @XmlValue
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class ColumnValue extends  Value{

        private String color;

        private String description;

        @XmlAttribute(name="color")
        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @XmlAttribute(name="description")
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


    }

}
