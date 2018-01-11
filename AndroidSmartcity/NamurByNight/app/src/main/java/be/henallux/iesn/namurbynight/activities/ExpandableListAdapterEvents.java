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
import be.henallux.iesn.namurbynight.model.Event;
import be.henallux.iesn.namurbynight.model.EventCalendar;

public class ExpandableListAdapterEvents extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<EventCalendar> listDates;
    private HashMap<String, ArrayList<Event>> listEvents;

    public ExpandableListAdapterEvents(Context context, ArrayList<EventCalendar> listDates,
                                       HashMap<String, ArrayList<Event>> listEvents) {
        this.context = context;
        this.listDates = listDates;
        this.listEvents = listEvents;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listEvents.get(this.listDates.get(groupPosition).getDate()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override       //Returns view for list events
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.events_list_view_childs, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expandableChildTextView);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listEvents.get(this.listDates.get(groupPosition).getDate()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDates.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDates.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override       //Returns view for the list dates
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).toString();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.events_list_view_groups, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.expandableGroupTextView);
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
