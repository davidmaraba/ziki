package ke.co.innova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.TextView;

public class Help2 extends ActivityGroup {
	// All xml labels
	TextView txtHelp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		txtHelp = (TextView) findViewById(R.id.help);
		txtHelp.setText("Help Stuff");
		
		final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	startActivity (new Intent(getApplicationContext(), Listview.class));
            	Intent activity3Intent = new Intent(v.getContext(),
						Search2.class);
				replaceContentView("activity3", activity3Intent);
            }
        });
        setContentView(button);
	}
	public void replaceContentView(String id, Intent newIntent) {
		View view = getLocalActivityManager().startActivity(id,
				newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView();
		this.setContentView(view);
	}
}
