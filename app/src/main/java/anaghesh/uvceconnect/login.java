package anaghesh.uvceconnect;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private EditText user,pass;
    private Button login;
    private TextView newu;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signup sn = new signup();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = firebaseAuth.getCurrentUser();
        if(muser!=null){
            finish();
            startActivity(new Intent(login.this, Drawer.class));
        }

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        newu = findViewById(R.id.newuser);
        login = findViewById(R.id.loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty())
                {
                    Toast.makeText(login.this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                validate(user.getText().toString().trim(),pass.getText().toString().trim());
            }
        });
        newu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, signup.class));
            }
        });


    }
    void validate(String user_name, String user_pass)
    {
        Log.e("validate","Inside Validate");
        progressDialog.setMessage("Logging you in!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(user_name,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    startActivity(new Intent(login.this, Drawer.class));
                    finish();
                    Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    
                }
                else {
                    Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
