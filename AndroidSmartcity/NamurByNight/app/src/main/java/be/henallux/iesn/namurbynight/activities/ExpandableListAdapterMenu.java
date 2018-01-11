package be.henallux.iesn.namurbynight.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import be.henallux.iesn.namurbynight.R;
import be.henallux.iesn.namurbynight.model.Drink;
import be.henallux.iesn.namurbynight.model.DrinkCategory;

public class ExpandableListAdapterMenu extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<DrinkCategory> listCategories;
    private HashMap<String, ArrayList<Drink>> listDrinks;

    public ExpandableListAdapterMenu(Context context, ArrayList<DrinkCategory> listCategories,
                                       HashMap<String, ArrayList<Drink>> listDrinks) {
        this.context = context;
        this.listCategories = listCategories;
        this.listDrinks = listDrinks;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDrinks.get(this.listCategories.get(groupPosition).getName()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override       //Returns view for list drinks
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.event_page_menu_childs, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expandableChildMenuTextView);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDrinks.get(this.listCategories.get(groupPosition).getName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listCategories.get(groupPosition).getName();
    }

    @Override
    public int getGroupCount() {
        return this.listCategories.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override       //Returns view for the list drink categories
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).toString();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.event_page_menu_groups, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.expandableGroupMenuTextView);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
