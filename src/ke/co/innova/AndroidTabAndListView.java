package ke.co.innova;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabAndListView extends TabActivity {
	// TabSpec Names
	private static final String INBOX_SPEC = "All Songs";
	private static String HITS_SPEC = "Hits";
	private static final String PLAY_SPEC="Play";
	private static final String OUTBOX_SPEC = "Search";
	private static final String PROFILE_SPEC = "Help";
	public static TabHost tabHost;
	public static TabSpec inboxSpec,hitsSpec,playSpec,profileSpec,outboxSpec;
	public static Intent playVideoIntent,gospelIntent,zikiHitsIntent,newReleasesInten,playIntent,downloadIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        playVideoIntent=new Intent(this,Play_Video.class);
        gospelIntent=new Intent(this,Gospel_Hits.class);
        zikiHitsIntent=new Intent(this,Ziki_Hits.class);
        newReleasesInten=new Intent(this,New_Releases.class);
        downloadIntent=new Intent(this,Download.class);
        
        tabHost = getTabHost();
        
        // Inbox Tab
        inboxSpec = tabHost.newTabSpec(INBOX_SPEC);
        // Tab Icon
        inboxSpec.setIndicator(INBOX_SPEC, getResources().getDrawable(R.drawable.guitar));
        Intent inboxIntent = new Intent(this, Gospel.class);
        // Tab Content
        inboxSpec.setContent(inboxIntent);
        
        
         hitsSpec = tabHost.newTabSpec(HITS_SPEC);
         hitsSpec.setIndicator(HITS_SPEC, getResources().getDrawable(R.drawable.playlist));
        Intent hitsIntent = new Intent(this, MyHits.class);
        hitsSpec.setContent(hitsIntent);
        
        //Play Tab
        playSpec=tabHost.newTabSpec(PLAY_SPEC);
        playSpec.setIndicator(PLAY_SPEC,getResources().getDrawable(R.drawable.musicplayer));
        playIntent=new Intent(this,Play.class);
        playSpec.setContent(playIntent);
        
         // Outbox Tab
       outboxSpec = tabHost.newTabSpec(OUTBOX_SPEC);
        outboxSpec.setIndicator(OUTBOX_SPEC, getResources().getDrawable(R.drawable.search));
        Intent outboxIntent = new Intent(this, Search.class);
        outboxSpec.setContent(outboxIntent);
        
        
        // Profile Tab
        profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.help));
        Intent profileIntent = new Intent(this, Help.class);
        profileSpec.setContent(profileIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(hitsSpec);
        tabHost.addTab(playSpec);
        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
        
        tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE);
        tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE);
    }

	public void startDownloadActivity()
	{
		// downloadIntent=new Intent(this,Download.class);
		startActivity(downloadIntent);
	}
}