package ke.co.innova;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
* The purpose of this Activity is to manage the activities in a tab.
* Note: Child Activities can handle Key Presses before they are seen here.
* @author Eric Harlow
*/
public class MultipleTabs extends ActivityGroup {

private ArrayList mIdList;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
if (mIdList == null) mIdList = new ArrayList();
}

/**
* This is called when a child activity of this one calls its finish method.
* This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
* and starts the previous activity.
* If the last child activity just called finish(),this activity (the parent),
* calls finish to finish the entire group.
*/
@Override
public void finishFromChild(Activity child) {
	LocalActivityManager manager = getLocalActivityManager();
	int index = mIdList.size()-1;
	int length = 0;
	if (index == 1) {
	
	Activity current = getLocalActivityManager().getActivity((String) mIdList.get(length-1));
	current.finish();
	}
}
}