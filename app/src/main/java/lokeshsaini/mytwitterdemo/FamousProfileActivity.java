package lokeshsaini.mytwitterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class FamousProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_profile);

        String profile = getIntent().getExtras().getString("profile");

        // Shows tweet timeline only if username is received
        if (profile != null) {
            int maxNumberOfTweets = 10;
            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(profile)
                    .maxItemsPerRequest(maxNumberOfTweets)
                    .includeReplies(false)
                    .includeRetweets(false)
                    .build();
            final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                    .setTimeline(userTimeline)
                    .build();
            ListView listView = (ListView) findViewById(R.id.list);
            View emptyView = findViewById(R.id.empty);
            listView.setEmptyView(emptyView);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
