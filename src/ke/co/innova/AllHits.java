package ke.co.innova;


import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class AllHits extends ExpandableListActivity implements
		OnChildClickListener, ExpandableListView.OnGroupExpandListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ExpandableListView expandbleLis = getExpandableListView();
		expandbleLis.setDividerHeight(2);
		expandbleLis.setGroupIndicator(null);
		expandbleLis.setClickable(true);

		setGroupData();
		setChildGroupData();

		//NewAdapter mNewAdapter = new NewAdapter(groupItem, childItem);
		/*mNewAdapter
				.setInflater(
						(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
						this);
		getExpandableListView().setAdapter(mNewAdapter);*/
		expandbleLis.setOnChildClickListener(this);
		
		expandbleLis.setOnGroupExpandListener(new OnGroupExpandListener() {
			int previousGroup=-1;
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				if(groupPosition != previousGroup)
	                expandbleLis.collapseGroup(previousGroup);
	            previousGroup = groupPosition;
			}
		});
	}

	public void setGroupData() {
		groupItem.add("Top 10 Kenyan Hits");
		groupItem.add("Top 10 Gospel Hits");
		groupItem.add("Top 10 Ziki Hits");
		groupItem.add("Top 10 New Releases");
	}

	ArrayList<String> groupItem = new ArrayList<String>();
	ArrayList<Object> childItem = new ArrayList<Object>();

	public void setChildGroupData() {
		/**
		 * Add Data For TecthNology
		 */
		ArrayList<String> child = new ArrayList<String>();
		child.add("Java");
		child.add("Drupal");
		child.add(".Net Framework");
		child.add("PHP");
		childItem.add(child);

		/**
		 * Add Data For Mobile
		 */
		child = new ArrayList<String>();
		child.add("Android");
		child.add("Window Mobile");
		child.add("iPHone");
		child.add("Blackberry");
		childItem.add(child);
		/**
		 * Add Data For Manufacture
		 */
		child = new ArrayList<String>();
		child.add("HTC");
		child.add("Apple");
		child.add("Samsung");
		child.add("Nokia");
		childItem.add(child);
		/**
		 * Add Data For Extras
		 */
		child = new ArrayList<String>();
		child.add("Contact Us");
		child.add("About Us");
		child.add("Location");
		child.add("Root Cause");
		childItem.add(child);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(AllHits.this, "Clicked On Child",
				Toast.LENGTH_SHORT).show();
		return true;
	}

	
}
