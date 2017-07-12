package com.main.bloodstore.bloodstore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class DisplayList extends AppCompatActivity {
    ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        Intent intent2=getIntent();
        String blood=intent2.getStringExtra("Group");
        final Context context =this;
        String area=intent2.getStringExtra("Area");
        String city=intent2.getStringExtra("City");
        final ListView listView=(ListView)findViewById(R.id.listView);
        final String link="https://blood-store-50fa3.firebaseio.com/Donor/"+blood+"/"+city+"/"+area;
        //Toast.makeText(context, link, Toast.LENGTH_SHORT).show();
        Firebase mRef=new Firebase(link);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list=new ArrayList<Person>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                        Person person = postSnapshot.getValue(Person.class);

                    //Adding it to a string
                   // String string = "Name: "+person.getName()+"\nAddress: "+person.getPhone()+"\n\n";

                    SharedPreferences shared=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    String emailId=shared.getString("mail","");
                    if(!person.getEmail().equalsIgnoreCase(emailId)){
                        list.add(person);
                    }
                    PersonAdapter adapter=new PersonAdapter(context,list);
                    //Displaying it on textview
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person p=(Person) list.get(position);
                //Toast.makeText(context,p.getName()+"", Toast.LENGTH_SHORT).show();
                if(p.getStatus().equalsIgnoreCase("no")){
                    Toast.makeText(context, "User Unavailable", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(DisplayList.this, DisplayDetails.class);
                    intent.putExtra("Person", p);
                    startActivity(intent);
                }

            }
        });

    }
}
