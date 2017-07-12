package com.main.bloodstore.bloodstore;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Tips extends AppCompatActivity {
    private EditText mTipField;
    private ImageView mTipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        mTipField=(EditText)findViewById(R.id.tipField);
        mTipBtn=(ImageView)findViewById(R.id.imageView);


        mTipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mTipField.getText().toString();

                sendTip();
            }
        });
    }

    private void sendTip() {


        Log.i("Send email", "");
        String[] TO = {"bloodstore.vnr@gmail.com"};
        String[] CC = {"suggulasaiteja@gmail.com","nsranjith123@gmail.com","suryakonda897@gmail.com","rajkishore55555@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tips for Blood Store");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mTipField.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Tips.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
