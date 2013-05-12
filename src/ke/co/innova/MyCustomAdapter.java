package ke.co.innova;



import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zizi
 * Date: 8/10/12
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyCustomAdapter extends BaseExpandableListAdapter {


    private LayoutInflater inflater;
    MediaController md;
    private ArrayList<Parent> mParent;
	private Context context;

    public MyCustomAdapter(Context context, ArrayList<Parent> parent){
    	this.context=context;
        mParent = parent;
        inflater = LayoutInflater.from(context);
    }


    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return mParent.get(i).getArrayChildren().size();
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getTitle();
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).getArrayChildren().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.parent, viewGroup,false);
        }


        TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);
     //   Button btn=(Button) view.findViewById(R.id.button);
      //  btn.setText("Success");
        //"i" is the position of the parent/group in the list
        textView.setText(getGroup(i).toString());

        //return the entire view
        return view;
    }

    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
    	//tempChild = (ArrayList<String>) Childtem.get(groupPosition);
    			//TextView text = null;
    	String path = "http://192.168.203.54/bangoh/hot.mp3";
    	
    	VideoView video=null;
    			if (view == null) {
    				view = inflater.inflate(R.layout.childrow,viewGroup,false);
    			}
    			md=new MediaController(context);
    			
    			video=  (VideoView) view.findViewById(R.id.surface_view);
    		/*	getWindow().clearFlags(WindowManager
    					.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    					WindowManager.LayoutParams.FLAG_FULLSCREEN);

    				*/
    				video.setVideoURI(Uri.parse(path));
    				video.setMediaController(md);
    				video.requestFocus();
    				video.start();
    			
    			//text.setText(mParent.get(i).getArrayChildren().get(i1));
    			//text.setText(tempChild.get(childPosition));
    			/*convertView.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					Toast.makeText(activity, tempChild.get(childPosition),
    							Toast.LENGTH_SHORT).show();
    				}
    			});*/
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }
}
