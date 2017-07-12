package com.main.bloodstore.bloodstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {

    FirebaseAuth mAuth;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        SharedPreferences shared=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        email=shared.getString("mail","");
        //Toast.makeText(this,email, Toast.LENGTH_SHORT).show();
    }

    public void setOn(View view) {
        Toast.makeText(this, "Thanks for coming back...", Toast.LENGTH_SHORT).show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        com.google.firebase.database.Query query = reference.child("User").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("status","yes");
                Toast.makeText(Edit.this, path, Toast.LENGTH_SHORT).show();
                reference.child(path).updateChildren(result);
                findName(reference.child(path).getKey(),"yes");
                //Toast.makeText(Edit.this,reference.child(path).getKey(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            //    Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
            // 90006 52688
            }
        });
    }


    public void setOff(View view) {
        Toast.makeText(this, "Hope you donated blood and saved a Life...", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Thank You..!", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "We will remind you back in 3 months...", Toast.LENGTH_LONG).show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        com.google.firebase.database.Query query = reference.child("User").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("status","no");
                //Toast.makeText(Edit.this, name, Toast.LENGTH_SHORT).show();
                reference.child(path).updateChildren(result);
                findName(reference.child(path).getKey(),"no");
                //Toast.makeText(Edit.this,reference.child(path).getKey(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //    Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
                // 90006 52688
            }
        });
    }

    public void findName(String base,final String datalocal){
        String link="https://blood-store-50fa3.firebaseio.com/User/"+base;
        Firebase editLink=new Firebase(link);
        editLink.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Map<String,String> myMap = dataSnapshot.getValue(Map.class);
                String name=(String)myMap.get("name");
               // Toast.makeText(Edit.this, name, Toast.LENGTH_SHORT).show();
                String link=(String)myMap.get("link");
                String blood=(String)myMap.get("blood");
                String city=(String)myMap.get("city");
                String area=(String)myMap.get("area");
                //Toast.makeText(Edit.this, link, Toast.LENGTH_SHORT).show();
                editMain(blood,city,area,link,datalocal);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    public void editMain(String blood, String city, String area, String link, final String data) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        //Query query = reference.child("Donor").child(blood).child(city).child(area).orderByChild("email").equalTo(email);
        DatabaseReference ref1=reference.child("Donor");
        DatabaseReference ref2=ref1.child(blood);
        final DatabaseReference ref3=ref2.child(city);
        Query query=ref3.child(area).orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("status",data);
                //Toast.makeText(Edit.this, name, Toast.LENGTH_SHORT).show();
                ref3.child(path).updateChildren(result);
                //findName(ref3.child(path).getKey());
                //Toast.makeText(Edit.this,reference.child(path).getKey(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //    Logger.error(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
                // 90006 52688
            }
        });
        /*Toast.makeText(this, "Link  " + link, Toast.LENGTH_SHORT).show();
        Firebase mRef = new Firebase(link);
        Query query = mRef.orderByChild("email").equalTo(email);*/

    }
}
    /*DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
    String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
    String path = "/" + dataSnapshot.getKey() + "/" + key;
    HashMap<String, Object> result = new HashMap<>();
result.put("status","no");*/
