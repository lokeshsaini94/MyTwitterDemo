package lokeshsaini.mytwitterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "YOUR_TWITTER_KEY";
    private static final String TWITTER_SECRET = "YOUR_TWITTE_SECRET";
    private TwitterLoginButton loginButton;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        showProgress(false);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                showProgress(true);

                final TwitterSession session = result.data;

                // To receive user data
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                Call<User> user = twitterApiClient.getAccountService().verifyCredentials(false, false);
                user.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {

                        // Initialise variables to pass in arrayList
                        String username = session.getUserName();
                        String name = result.data.name;
                        String description = result.data.description;
                        String profileurl = result.data.profileImageUrl;
                        String profilebannerurl = result.data.profileBannerUrl;

                        // ArrayList to pass data with Intent
                        ArrayList<String> ar = new ArrayList<>();
                        ar.add(username);
                        ar.add(name);
                        ar.add(description);
                        ar.add(profileurl);
                        ar.add(profilebannerurl);

                        Intent i = new Intent(MainActivity.this, UserActivity.class);
                        i.putStringArrayListExtra("user", ar);
                        startActivity(i);
                        finish();

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("TwitterKit", "Getting user details failure", exception);
                    }
                });
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    // Shows the progress bar and hides the login button.
    public void showProgress(final boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        loginButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

}
