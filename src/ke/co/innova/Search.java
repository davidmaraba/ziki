package ke.co.innova;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Search extends Activity {
	// All xml labels
	TextView txtHelp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		txtHelp = (TextView) findViewById(R.id.help);
		txtHelp.setText("Help Stuff");
	}
}
