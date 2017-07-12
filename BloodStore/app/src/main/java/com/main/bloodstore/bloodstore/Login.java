package com.main.bloodstore.bloodstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private TextView textViewSignUp;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        buttonSignIn=(Button)findViewById(R.id.buttonSignIn);
        textViewSignUp=(TextView)findViewById(R.id.textViewSignUp);
        SharedPreferences sp=getSharedPreferences("loginData",Context.MODE_PRIVATE);
        String mail=sp.getString("UserName","");
        String psd=sp.getString("Password","");
        editTextEmail.setText(mail);
        editTextPassword.setText(psd);

    }
    public void doLogin(View view){
        final String emailId=editTextEmail.getText().toString();
        String pwd=editTextPassword.getText().toString();
        CheckBox checkBox=(CheckBox)findViewById(R.id.rememberMe);
        if(checkBox.isChecked()){
            SharedPreferences shared=getSharedPreferences("loginData",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=shared.edit();
            editor.putString("UserName",emailId);
            editor.putString("Password",pwd);
            editor.commit();
        }
        final Intent intent=new Intent(this,Search.class);
        final ProgressDialog progressDialog=new ProgressDialog(Login.this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Retreiving Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!TextUtils.isEmpty(emailId)){
            if(!TextUtils.isEmpty(pwd)){


                mAuth.signInWithEmailAndPassword(emailId, pwd)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    progressDialog.cancel();
                                    Toast.makeText(Login.this, "Login Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else if(task.isSuccessful()){
                                    Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();
                                    editor.putString("mail",emailId);
                                    editor.apply();
                                    editor.commit();
                                    progressDialog.cancel();
                                    startActivity(intent);
                                }

                                // ...
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
        }
    }
    public void doRegister(View view){
        startActivity(new Intent(this,Register.class));
    }
}
