package com.fusionspy.beacon.web;

import java.util.ArrayList;

/**
 * menu.
 * User: qc
 * Date: 11-8-25
 * Time: 下午8:46
 * To change this template use File | Settings | File Templates.
 */
public class Menu {

    //id of the node
    private String id;

    //label of the node
    private String text;

    //tooltip of the node
    private String tooltip;

    //image for a node without child items (tree will get images from the path specified in setImagePath() method);
    private String im0;

    //image for an expanded node with child items
    private String im1;

    //image for a collapsed node with child items
    private String im2;

    //colour of an item that is not selected;
    private String aCol;

    //colour of a selected item
    private String sCol;

    //select a node on load (any value)
    private String select;

    //text style of a node
    private String style;

    //show a node opened (any value)
    private String open;

    //call function on select(any value)
    private String call;

    //check checkbox if exists (in case of 3-state checkboxes values can be 1 - checked or -1 - unsure)
    private String checked;

    //instruct component, to not render checkbox for related item, optional
    private String nocheckbox;

    //specifies whether a node has child items (1) or not (0);
    private String child;

    //height of the icon
    private String imheight;

    //width of the icon
    private String imwidth;

    //offset of the item from the node above
    private String topoffset;

    //if not empty, child items of this node will have radio buttons
    private String radio;

    private ArrayList<Menu> item;

    public ArrayList<Menu> getItem() {
        return item;
    }

    public void setItem(ArrayList<Menu> item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getIm0() {
        return im0;
    }

    public void setIm0(String im0) {
        this.im0 = im0;
    }

    public String getIm1() {
        return im1;
    }

    public void setIm1(String im1) {
        this.im1 = im1;
    }

    public String getIm2() {
        return im2;
    }

    public void setIm2(String im2) {
        this.im2 = im2;
    }

    public String getaCol() {
        return aCol;
    }

    public void setaCol(String aCol) {
        this.aCol = aCol;
    }

    public String getsCol() {
        return sCol;
    }

    public void setsCol(String sCol) {
        this.sCol = sCol;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getNocheckbox() {
        return nocheckbox;
    }

    public void setNocheckbox(String nocheckbox) {
        this.nocheckbox = nocheckbox;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getImheight() {
        return imheight;
    }

    public void setImheight(String imheight) {
        this.imheight = imheight;
    }

    public String getImwidth() {
        return imwidth;
    }

    public void setImwidth(String imwidth) {
        this.imwidth = imwidth;
    }

    public String getTopoffset() {
        return topoffset;
    }

    public void setTopoffset(String topoffset) {
        this.topoffset = topoffset;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }
}
