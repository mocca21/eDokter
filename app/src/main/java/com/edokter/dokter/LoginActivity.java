package com.edokter.dokter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edokter.R;
import com.edokter.menu.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private TextView register,forgot;
    private FirebaseAuth auth;
    private Button btnLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MenuDokterActivity.class));
            finish();
        }

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading ....");
        progressDialog.setCancelable(true);

        register=(TextView) findViewById(R.id.tvRegister);
        forgot=(TextView) findViewById(R.id.tvForgotPassword);
        inputEmail = (EditText) findViewById(R.id.etEmail);
        inputPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        forgot.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            String email = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    Toast.makeText(LoginActivity.this, "Password Kurang dari 6 Karakter", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Cek Email dan Password anda, atau Daftar", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MenuDokterActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
        else if(view.getId()==R.id.tvRegister){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        }
        else if (view.getId()==R.id.tvForgotPassword){
            startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            finish();
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        finish();
    }
}

