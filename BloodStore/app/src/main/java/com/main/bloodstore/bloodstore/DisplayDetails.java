package com.main.bloodstore.bloodstore;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayDetails extends AppCompatActivity {
String number,email;
    TextView name_m,age_m,gender_m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
        Intent intent=getIntent();
        Person p=intent.getParcelableExtra("Person");
        number=p.getPhone();
        email=p.getEmail();
        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        name_m=(TextView)findViewById(R.id.name);
        age_m=(TextView)findViewById(R.id.age);
        gender_m=(TextView)findViewById(R.id.gender);
        name_m.setText(p.getName());
        age_m.setText(p.getAge());
        gender_m.setText(p.getGender());
    }

    public void doCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        startActivity(callIntent);
    }
    public void sendEmail(View view){
        //String[] TO = {"bloodstore.vnr@gmail.com"};

        Log.i("Send email", "");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Request For Blood...");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage(View view) {
        // The number on which you want to send SMS
       // startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        Intent sms=new Intent();
        sms.setAction(android.content.Intent.ACTION_VIEW);
        sms.setData(Uri.parse("smsto:"+number));
        startActivity(sms);
    }
}
