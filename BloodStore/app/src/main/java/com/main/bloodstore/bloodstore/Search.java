package com.main.bloodstore.bloodstore;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText e_group,e_city,e_area;
    Spinner s_group,s_city,s_area;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        drawerLayout=(DrawerLayout)findViewById(R.id.myDrawer);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nv=(NavigationView)findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                openActivity(item.getTitle().toString());
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        e_group=(EditText)findViewById(R.id.group_search);
        e_city=(EditText)findViewById(R.id.city_search);
        e_area=(EditText)findViewById(R.id.area_search);
        s_group=(Spinner)findViewById(R.id.spin_group);
        s_city=(Spinner)findViewById(R.id.spin_city);
        s_area=(Spinner)findViewById(R.id.spin_area);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        //Toast.makeText(this, reference.toString(), Toast.LENGTH_SHORT).show();
        final ArrayList<String> bloodGroupsList=new ArrayList<>();
        bloodGroupsList.add("Blood Group");
        bloodGroupsList.add("A Plus");
        bloodGroupsList.add("A Minus");
        bloodGroupsList.add("B Plus");
        bloodGroupsList.add("B Minus");
        bloodGroupsList.add("O Plus");
        bloodGroupsList.add("O Minus");
        bloodGroupsList.add("AB Plus");
        bloodGroupsList.add("AB Minus");
        //9885998656
        final ArrayAdapter<String> arrayBloodAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bloodGroupsList);
        s_group.setAdapter(arrayBloodAdapter);
        s_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String var=arrayBloodAdapter.getItem(position);
                e_group.setText(var);
                e_city.setText("");
                e_area.setText("");
                if(!var.equalsIgnoreCase("Blood group")){
                    assignCities(var);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void assignCities(final String blood){
        String bloodLink="https://blood-store-50fa3.firebaseio.com/Donor/"+blood;
        Firebase mRefLink=new Firebase(bloodLink);
        final ArrayList<String> citiesList=new ArrayList<>();
        citiesList.clear();
        //citiesList.add("City");
        final ArrayAdapter<String> arrayCitiesAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,citiesList);
        mRefLink.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getKey().toString();
                if(value==null){
                    Toast.makeText(getBaseContext(),"No Data Present",Toast.LENGTH_LONG).show();
                }
                else {
                    citiesList.add(value);
                    arrayCitiesAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        s_city.setAdapter(arrayCitiesAdapter);
        s_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city=arrayCitiesAdapter.getItem(position);
                e_city.setText(city);
                e_area.setText("");
                if(!city.equalsIgnoreCase("")){
                    assignAreas(city,blood);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void assignAreas(String city,String blood)
    {
        String cityLink="https://blood-store-50fa3.firebaseio.com/Donor/"+blood+"/"+city;
        Firebase mRefAreas=new Firebase(cityLink);
        final ArrayList<String> areasList=new ArrayList<>();
        areasList.clear();
        final ArrayAdapter<String> arrayAreasAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,areasList);
        mRefAreas.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getKey().toString();
                if(value==null){
                    Toast.makeText(getBaseContext(),"No Data Present",Toast.LENGTH_LONG).show();
                }
                else {
                    areasList.add(value);
                    arrayAreasAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        s_area.setAdapter(arrayAreasAdapter);
        s_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city=arrayAreasAdapter.getItem(position);
                e_area.setText(city);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void searchData(View view) {
        if(!TextUtils.isEmpty(e_city.getText()) && !TextUtils.isEmpty(e_area.getText()) && !e_group.getText().toString().equalsIgnoreCase("Blood Group") ){
            Intent intentSearch=new Intent(this,DisplayList.class);
            intentSearch.putExtra("Group",e_group.getText().toString());
            intentSearch.putExtra("City",e_city.getText().toString());
            intentSearch.putExtra("Area",e_area.getText().toString());
            startActivity(intentSearch);
        }
        else
            Toast.makeText(this, "Enter Missing Fields", Toast.LENGTH_SHORT).show();
    }

    public void editDetails(View view) {
        startActivity(new Intent(this,Edit.class));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Method execute", Toast.LENGTH_SHORT).show();
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openActivity(String selectedOption){
        if(selectedOption.equalsIgnoreCase("edit my status")){
           // startActivity(new Intent(this,EditDetails.class));
            startActivity(new Intent(this,Edit.class));
        }
        else if (selectedOption.equalsIgnoreCase("logout")){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getBaseContext(),"Logged out Successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Login.class));
        }
        else if(selectedOption.equalsIgnoreCase("Contact Us"))
        {
            startActivity(new Intent(this,Contact_Us.class));
        }
        else if(selectedOption.equalsIgnoreCase("tips"))
        {
            startActivity(new Intent(this,Tips.class));
        }
        else if(selectedOption.equalsIgnoreCase("edit my profile")) {
            startActivity(new Intent(this,EditPersonalDetails.class));
        }

    }

    @Override
    public void onBackPressed() {

    }

    public void openMaps(View view) {
        if(isLocationEnabled(getBaseContext())) {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + "hospitals");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else {
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }
}
