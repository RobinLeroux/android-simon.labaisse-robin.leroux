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
import be.henallux.iesn.namurbynight.model.Organizer;
import be.henallux.iesn.namurbynight.model.OrganizerType;

public class ExpandableListAdapterOrganizers extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<OrganizerType> listTypes;
    private HashMap<String, ArrayList<Organizer>> listOrganizers;

    public ExpandableListAdapterOrganizers(Context context, ArrayList<OrganizerType> listTypes,
                                       HashMap<String, ArrayList<Organizer>> listOrganizers) {
        this.context = context;
        this.listTypes = listTypes;
        this.listOrganizers = listOrganizers;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listOrganizers.get(this.listTypes.get(groupPosition).getType()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override       //Returns view for list organizers
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.organizers_list_view_childs, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expandableChildOrgaTextView);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listOrganizers.get(this.listTypes.get(groupPosition).getType()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listTypes.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listTypes.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override       //Returns view for the list organizer types
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition).toString();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.organizers_list_view_groups, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.expandableGroupOrgaTextView);
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
