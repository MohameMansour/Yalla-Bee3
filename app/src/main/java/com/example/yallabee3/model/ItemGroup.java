package com.example.yallabee3.model;
import java.util.ArrayList;

public class ItemGroup {
    private String headerTitle;
    private ArrayList<ItemData> ListItem;

    public ItemGroup() {
    }

    public ItemGroup(String headerTitle, ArrayList<ItemData> listItem) {
        this.headerTitle = headerTitle;
        ListItem = listItem;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ItemData> getListItem() {
        return ListItem;
    }

    public void setListItem(ArrayList<ItemData> listItem) {
        ListItem = listItem;
    }
}
