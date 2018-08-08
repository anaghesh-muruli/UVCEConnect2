package anaghesh.uvceconnect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class signup extends AppCompatActivity {
    private TextView already_reg,warn,invalid_em;
    private Button register;
    private EditText name,email,pword,usnnumber;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String uname,pass,emailid,usnid;
    public static String branch1, dispYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if(!isConnected(signup.this)) buildDialog(signup.this).show();
        else {

        }
        setupUIviews();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        already_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this,login.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                {
                    progressDialog.setMessage("Registering");
                    progressDialog.show();
                String user_email = email.getText().toString().trim();
                String user_pass = pword.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(signup.this ,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            sendData();
                            Log.e("Branch",""+branch1);
                            progressDialog.dismiss();
                            Toast.makeText(signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Log.e("Success","Registration Done");
                            firebaseAuth.signOut();
                            Intent intent = new Intent(signup.this,login.class);
                            startActivity(intent);
                            finish();
                        }
                            else {
                            progressDialog.dismiss();
                            Toast.makeText(signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }}
        });


    }
    private void setupUIviews(){

        already_reg = findViewById(R.id.already);
        register = findViewById(R.id.register);
        name = findViewById(R.id.username);
        warn = findViewById(R.id.warning);
        Log.e("name",""+R.id.username);
        email = findViewById(R.id.email);
        usnnumber = findViewById(R.id.usn);
        pword = findViewById(R.id.password);
        invalid_em = findViewById(R.id.invalid_email);

    }
    private Boolean validate(){
        Boolean result = false;
        boolean bool,bool1;
        Log.e("name1",""+name);
         uname = name.getText().toString().trim();
        Log.e("username",""+uname);

         pass = pword.getText().toString().trim();
        Log.e("password",""+pass);

         emailid = email.getText().toString().trim();
        CharSequence seq = "@";
        bool = emailid.contains(seq);
        CharSequence seq1 = ".";
        bool1 = emailid.contains(seq1);
         usnid = usnnumber.getText().toString().trim();
        if(uname.isEmpty() || pass.isEmpty() || emailid.isEmpty() || usnid.isEmpty()){
            Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(!(bool && bool1)){
            invalid_em.setVisibility(View.VISIBLE);
        }
        else if(pword.getText().toString().length() <=7)
        {
            warn.setVisibility(View.VISIBLE);
        }
        else
            result = true;
        return result;
    }

    private void sendData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());
        branchfinder();
        yearFinder();
        Userprofile userprofile = new Userprofile(uname,usnid,emailid,branch1, dispYear );
        myref.setValue(userprofile);
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
        else return false;
        } else
        return false;
    }
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to register. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    public void branchfinder(){

        String branch = usnid.substring(4,7);
        if(branch.equalsIgnoreCase("EI6")){
            branch1 = "Information Science and Engineering";
        }
        else if(branch.equalsIgnoreCase("EC9")){
            branch1 = "Computer Science and Engineering";
        }
        else if(branch.equalsIgnoreCase("ECV")){
            branch1 = "Civil Engineering";
        }
        else if(branch.equalsIgnoreCase("EE7")){
            branch1 = "Electrical and Electronics Engineering";
        }
        else if(branch.equalsIgnoreCase("EE8")){
            branch1 = "Electronics and Communications Engineering";
        }
        else if(branch.equalsIgnoreCase("EM9")){
            branch1 = "Mechanical Engineering";
        }
        else
            branch1 ="To be updated";


        }
        public  void yearFinder(){
            int year,usnYear;
            String usn = usnid.substring(1,3);
            usnYear = Integer.parseInt(usn);
            String date = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date());
            int current = Integer.parseInt(date);
            year = current - usnYear;

            if(year >=0 && year <100)
                dispYear = "1st year";
            else if(year >=100 && year <200)
                dispYear = "2st year";
            else if(year >=200 && year <300)
                dispYear = "3st year";
            else
                dispYear = "4th year";
    }
}

