package com.main.bloodstore.bloodstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText username;
    private TextView userbgroup;
    private Spinner spinner;
    private EditText userage;
    private EditText usermobile;
    private EditText userarea;
    private EditText usercity;
    private EditText useremail;
    private EditText userpassword;
    private EditText userpassword1;
    FirebaseAuth mAuth;
    Firebase mRef;
    String var,totalLink;
    Capital capital;
    String m_name,m_age,m_mobile,m_area,m_city,m_email,m_password,m_retype,m_blood;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        username=(EditText)findViewById(R.id.username);
        userage=(EditText)findViewById(R.id.userage);
        usermobile=(EditText)findViewById(R.id.usermobile);
        userarea=(EditText)findViewById(R.id.userarea);
        usercity=(EditText)findViewById(R.id.usercity);
        useremail=(EditText)findViewById(R.id.useremail);
        userpassword=(EditText)findViewById(R.id.userpassword);
        userpassword1=(EditText)findViewById(R.id.userpassword1);
        userbgroup=(TextView) findViewById(R.id.userbgroup);
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Select\t\t");
        arrayList.add("A plus");
        arrayList.add("A minus");
        arrayList.add("B plus");
        arrayList.add("B minus");
        arrayList.add("O plus");
        arrayList.add("O minus");
        arrayList.add("AB plus");
        arrayList.add("AB minus");
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayList);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                var=adapter.getItem(i);
                userbgroup.setText(var);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void doRegister(View view){
         m_name=username.getText().toString();
         m_age=userage.getText().toString();
        if(m_age==null){
            m_age=0+"";
        }
         m_mobile=usermobile.getText().toString();
         m_area=userarea.getText().toString();
         m_city=usercity.getText().toString();
         m_blood=userbgroup.getText().toString();
         m_email=useremail.getText().toString();
         m_password=userpassword.getText().toString();
         m_retype=userpassword1.getText().toString();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(m_email);

        if (matcher.find()) {
            //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            if (Integer.parseInt(m_age) > 18) {
                if (m_password.equals(m_retype)) {
                    if (m_password.length() >= 6) {
                        mAuth = FirebaseAuth.getInstance();

                        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(m_email, m_password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                            Toast.makeText(getBaseContext(), "Job Done..!", Toast.LENGTH_LONG).show();
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getBaseContext(), "Mail I'd already exist",
                                                    Toast.LENGTH_SHORT).show();
                                        }


                                        // ...
                                    }
                                });

                        storeDetails();
                        Intent intent = new Intent(this, Login.class);
                        Toast.makeText(this, "Successfully Registered..Thanks for Being a part of us..!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Minimum length of Password is 6 characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Minimum Age required is 18", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please give valid User Email", Toast.LENGTH_SHORT).show();
        }

    }
    public void storeDetails()
    {
        mRef= new Firebase("https://blood-store-50fa3.firebaseio.com/");
        RadioGroup radio=(RadioGroup)findViewById(R.id.usergender);
        RadioButton rb=(RadioButton)findViewById(radio.getCheckedRadioButtonId());
        String gender=rb.getText().toString();

        RadioGroup radioDecision=(RadioGroup)findViewById(R.id.userchoice);
        RadioButton decision=(RadioButton)findViewById(radioDecision.getCheckedRadioButtonId());
        String choice=decision.getText().toString();
        Firebase mRefUser=mRef.child("User");
        Firebase mBaseRef=mRefUser.push();
        Firebase mRefBlood=mBaseRef.child("blood");
        mRefBlood.setValue(capital.capitalizeString(m_blood).trim());
        Firebase mRefCity=mBaseRef.child("city");
        mRefCity.setValue(capital.capitalizeString(m_city).trim());
        Firebase mRefArea=mBaseRef.child("area");
        mRefArea.setValue(capital.capitalizeString(m_area).trim());
        Firebase mRefName=mBaseRef.child("name");
        mRefName.setValue(capital.capitalizeString(m_name).trim());
        Firebase mRefPhone=mBaseRef.child("phone");
        mRefPhone.setValue(m_mobile.trim());
        Firebase mRefAge=mBaseRef.child("age");
        mRefAge.setValue(m_age.trim());
        Firebase mRefEmail=mBaseRef.child("email");
        mRefEmail.setValue(m_email.trim());
        Firebase mRefGender=mBaseRef.child("gender");
        mRefGender.setValue(gender);
        Firebase mRefLink=mBaseRef.child("link");
        totalLink="https://blood-store-50fa3.firebaseio.com/Donor/"+capital.capitalizeString(m_blood)+"/"+capital.capitalizeString(m_city)+"/"+capital.capitalizeString(m_area.trim());
        mRefLink.setValue(totalLink.trim());
        /*Firebase mRefGroup=mRefUser.child(capital.capitalizeString(m_blood));
        Firebase mRefCity=mRefGroup.child(capital.capitalizeString(m_city));
        Firebase mRefArea=mRefCity.child(capital.capitalizeString(m_area));
        Firebase mReference=mRefArea.push();
        Firebase mRefName=mReference.child("name");
        mRefName.setValue(capital.capitalizeString(m_name));
        Firebase mRefPhone=mReference.child("phone");
        mRefPhone.setValue(m_mobile);
        Firebase mRefAge=mReference.child("age");
        mRefAge.setValue(m_age);
        Firebase mRefEmail=mReference.child("email");
        mRefEmail.setValue(m_email);
        Firebase mRefGender=mReference.child("gender");
        mRefGender.setValue(gender);*/

        if(choice.equalsIgnoreCase("Yes")){
            Firebase mRefUserD=mRef.child("Donor");
            Firebase mRefGroupD=mRefUserD.child(capital.capitalizeString(m_blood).trim());
            Firebase mRefCityD=mRefGroupD.child(capital.capitalizeString(m_city).trim());
            Firebase mRefAreaD=mRefCityD.child(capital.capitalizeString(m_area).trim());
            Firebase mReferenceD=mRefAreaD.push();
            Firebase mRefNameD=mReferenceD.child("name");
            mRefNameD.setValue(capital.capitalizeString(m_name).trim());
            Firebase mRefPhoneD=mReferenceD.child("phone");
            mRefPhoneD.setValue(m_mobile.trim());
            Firebase mRefAgeD=mReferenceD.child("age");
            mRefAgeD.setValue(m_age.trim());
            Firebase mRefEmailD=mReferenceD.child("email");
            mRefEmailD.setValue(m_email.trim());
            Firebase mRefGenderD=mReferenceD.child("gender");
            mRefGenderD.setValue(gender);
            Firebase mRefStatusD=mReferenceD.child("status");
            mRefStatusD.setValue("yes");
        }

    }

}
