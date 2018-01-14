package com.edokter.dokter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edokter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvlogin;
    private Button btnRegesiter;
    private EditText etemail,etpassword;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading .....");
        progressDialog.setCancelable(true);

        tvlogin=(TextView)findViewById(R.id.tvlogin);
        btnRegesiter=(Button) findViewById(R.id.btnRegister);
        etemail=(EditText)findViewById(R.id.etEmail);
        etpassword=(EditText)findViewById(R.id.etPassword);

        tvlogin.setOnClickListener(this);
        btnRegesiter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnRegister){
            String email=etemail.getText().toString();
            String pass=etpassword.getText().toString();

            if (isEmpty(email)&&isEmpty(pass)){
                Toast.makeText(this, "Masukan email dan password", Toast.LENGTH_SHORT).show();
            }
            if (pass.length()<6){
                Toast.makeText(this, "Password terlalu pendek, minimal 6 karakter", Toast.LENGTH_SHORT).show();
            }

            progressDialog.show();
            auth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                    if (!task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Akun gagal dibuat "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this,MenuDokterActivity.class));
                        finish();
                    }
                }
            });
        }
        else if (view.getId()==R.id.tvlogin){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
