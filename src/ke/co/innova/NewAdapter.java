package ke.co.innova;


import java.util.ArrayList;
import java.util.HashMap;


import ke.co.innova.Hits.LoadSubscriptions;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unchecked")
public class NewAdapter extends BaseExpandableListAdapter {

	public ArrayList<HashMap<String,String>> groupItem;
	ArrayList<String> tempChild;
	public ArrayList<Object> Childtem = new ArrayList<Object>();
	public LayoutInflater minflater;
	public Activity activity;
	private ImageLoader imageLoader;
	private ArrayList<Parent> mParent;
	
	/*public NewAdapter(ArrayList<HashMap<String, String>> grList, ArrayList<Object> childItem) {
		groupItem = grList;
		this.Childtem = childItem;
		// imageLoader=new ImageLoader(activity.getApplicationContext());
	}*/

	public NewAdapter(Context context, ArrayList<Parent> parent) {
		// TODO Auto-generated constructor stub
		mParent = parent;
        minflater = LayoutInflater.from(context);
	}

	public void setInflater(LayoutInflater mInflater, Activity loadSubscriptions) {
		this.minflater = mInflater;
		activity = loadSubscriptions;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		//return null;
		return mParent.get(groupPosition).getArrayChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		//return 0;
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		//tempChild = (ArrayList<String>) Childtem.get(groupPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.childrow,parent,false);
		}
		text = (TextView) convertView.findViewById(R.id.textView1);
		text.setText(mParent.get(groupPosition).getArrayChildren().get(childPosition));
		//text.setText(tempChild.get(childPosition));
		/*convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, tempChild.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});*/
		 
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		//return ((ArrayList<String>) Childtem.get(groupPosition)).size();
		 return mParent.get(groupPosition).getArrayChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
	//	return null;
		 return mParent.get(groupPosition).getTitle();
	}

	@Override
	public int getGroupCount() {
		//return groupItem.size();
		return mParent.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.parent,parent,false);
		}
		 //HashMap<String, String> song = new HashMap<String, String>();
	      //  song = groupItem.get(groupPosition);
	        
		//((CheckedTextView) convertView).setText(song.get(Hits.TAG_ARTIST));
		//((CheckedTextView) convertView).setChecked(isExpanded);

        //TextView pid = (TextView)convertView.findViewById(R.id.title); // title
      // TextView name = (TextView)convertView.findViewById(R.id.artist); // artist name
       // TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
      //  ImageView thumb_image=(ImageView)convertView.findViewById(R.id.list_image); // thumb image
        
       // HashMap<String, String> song = new HashMap<String, String>();
       // song = groupItem.get(groupPosition);
        
        // Setting all values in listview
        //pid.setText(song.get(Hits.TAG_ID));
      //  name.setText("Ogopa");
        //duration.setText(song.get(CustomizedListView.KEY_DURATION));
       // imageLoader.DisplayImage(song.get(SubscribedActivity.KEY_THUMB_URL), thumb_image);
       // imageLoader.DisplayImage(song.get(Hits.KEY_THUMB_URL), thumb_image);
      
       // ((CheckedTextView) convertView).setChecked(isExpanded);
       
		TextView textView = (TextView) convertView.findViewById(R.id.list_item_text_view);
       // Button btn=(Button) convertView.findViewById(R.id.button);
      //  btn.setText("Success");
        //"i" is the position of the parent/group in the list
        textView.setText("Home");
        //return the entire view
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		super.registerDataSetObserver(observer);
	}

	
}

