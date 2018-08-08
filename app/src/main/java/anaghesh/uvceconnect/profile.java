package anaghesh.uvceconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    private TextView profileName, profileemail, profileUSN, profileBranch,profileYear;
    private Button logout;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
     signup sn = new signup();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Info");
        progressDialog.show();
        setupUI();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Userprofile userprofile = dataSnapshot.getValue(Userprofile.class);
                profileName.setText(userprofile.getprofileName());
                profileemail.setText(userprofile.getprofileEmailid());
                profileUSN.setText(userprofile.getprofileUSN());
                profileBranch.setText(userprofile.getProfileBranch());
                profileYear.setText(userprofile.getProfileYear());
                Log.e("branch",""+signup.branch1);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(profile.this, MainActivity.class));
            }
        });
    }
    void setupUI(){
        profileName = findViewById(R.id.profilename);
        profileUSN = findViewById(R.id.profileUSN);
        profileemail = findViewById(R.id.profileemail);
        profileBranch = findViewById(R.id.profilebranch);
        logout = findViewById(R.id.button);
        profileYear = findViewById(R.id.year);
    }
}
