package com.edokter.dokter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edokter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.isEmpty;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail;
    private Button btnSubmit;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading .....");
        progressDialog.setCancelable(true);


        etEmail=(EditText)findViewById(R.id.etEmail);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnSubmit){
            String email=etEmail.getText().toString();
            if (isEmpty(email)){
                Toast.makeText(this, "Alamat email harus di isi", Toast.LENGTH_SHORT).show();
            }
            progressDialog.show();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                        finish();
                        Toast.makeText(ForgotPasswordActivity.this, "Silahkan cek emal anda", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ForgotPasswordActivity.this, "email tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
        finish();
    }
}
