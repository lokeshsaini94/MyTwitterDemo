package lokeshsaini.mytwitterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ArrayList<String> ar1 = getIntent().getExtras().getStringArrayList("user");

        TextView name = (TextView) findViewById(R.id.user_profile_name);
        TextView description = (TextView) findViewById(R.id.user_profile_desc);
        ImageView profilePicture = (ImageView) findViewById(R.id.user_profile_photo);
        ImageView coverPicture = (ImageView) findViewById(R.id.header_cover_image);
        Button modi = (Button) findViewById(R.id.modi);
        Button elon = (Button) findViewById(R.id.elon);
        modi.setOnClickListener(this);
        elon.setOnClickListener(this);

        // Sets user name
        if (ar1 != null) {
            name.setText(ar1.get(1));
        }

        // Sets user bio
        if (ar1 != null) {
            description.setText(ar1.get(2));
        }

        // Sets user Profile picture
        if (ar1 != null) {
            Picasso.with(getApplicationContext()).load(ar1.get(3)).into(profilePicture);
        }

        // Sets user cover picture
        if (ar1 != null) {
            Picasso.with(getApplicationContext()).load(ar1.get(4)).into(coverPicture);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modi:
                Toast.makeText(getApplicationContext(), R.string.narendra_modi, Toast.LENGTH_LONG).show();
                startFamousProfileActivity("narendramodi");
                break;
            case R.id.elon:
                Toast.makeText(getApplicationContext(), R.string.elon_musk, Toast.LENGTH_LONG).show();
                startFamousProfileActivity("elonmusk");
                break;
        }

    }

    // Starts famousProfileActivity for the received profile name
    private void startFamousProfileActivity(String profile) {
        Intent i = new Intent(UserActivity.this, FamousProfileActivity.class);
        i.putExtra("profile", profile);
        startActivity(i);
    }
}
