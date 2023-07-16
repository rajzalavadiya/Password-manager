package com.crown.passwordmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.passwordmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String URL = "https://glexas.com/Raj/Android/ShowData.php";

    private String userName, title, password, date, NO;
    private ArrayList<DataModel> list = new ArrayList<>();

     private LinearLayout linearLayout;
     private ImageButton imageButton1, imageButton2, imageButton3;
     private FloatingActionButton addPassword;
     private EditText search ,firstDate,lastDate;
     private ImageButton close;
     private RecyclerView recyclerView;
     private Date date1,date2,date3;
     public TextView textView2;
     private boolean doubleBackToExitPressedonse=false;
    private static  String NAME ="Login";
    private static  String KEY_EMAIL ="email";
    private static  String KEY_PASS ="password";
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences =getSharedPreferences(NAME,MODE_PRIVATE);
        String email =sharedPreferences.getString(KEY_EMAIL,null);
        if(email==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    ArrayList<DataModel> list1 = new ArrayList<>();
                    JSONArray dataList = new JSONArray(response);

                    recyclerView.setAdapter(adapter);

                    for (int i = 0; i < dataList.length(); i++) {

                        JSONObject dataListObject = dataList.getJSONObject(i);
                        title = dataListObject.getString("title");
                        date = dataListObject.getString("date");
                        userName = dataListObject.getString("user");
                        password = dataListObject.getString("password");

                        NO = dataListObject.getString("NO");
                        list.add(new DataModel(title, date, userName, password, NO));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", email);
                return map;
            }
        };
        queue.add(request);



        linearLayout=findViewById(R.id.linearLayout);
        search=findViewById(R.id.search);
        close=findViewById(R.id.close);
        addPassword=findViewById(R.id.addPassword);
        imageButton1=findViewById(R.id.imageButton1);
        imageButton2=findViewById(R.id.imageButton2);
        imageButton3=findViewById(R.id.imageButton3);
        firstDate=findViewById(R.id.firstDate);
        lastDate=findViewById(R.id.lastDate);


        imageButton1.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String dateString = String.format("%02d-%02d-%d", year, (monthOfYear + 1), dayOfMonth);
                                firstDate.setText(dateString);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                if (mMonth < 10) {
                    mMonth = Integer.parseInt("0" + mMonth);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String dateString = String.format("%02d-%02d-%d", year, (monthOfYear + 1), dayOfMonth);
                                lastDate.setText(dateString);


                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();


            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                ArrayList<DataModel> filteredlist = new ArrayList<>();
                for (DataModel item : list) {

                    date2 = new Date();
                    date3 = new Date();
                    try {
                        date1 = format.parse(item.getDate());
                        date2 = format.parse(String.valueOf(firstDate.getText()));
                        date3 = format.parse(String.valueOf(lastDate.getText()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date2.before(date1) && date3.after(date1)) {
                        filteredlist.add(item);
                    }
                }
                if (filteredlist.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
                    RecyclerAdapter adapter = new RecyclerAdapter(filteredlist, MainActivity.this);
                    recyclerView.setAdapter(adapter);

                } else {
                    RecyclerAdapter adapter = new RecyclerAdapter(filteredlist, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
                RecyclerAdapter adapter = new RecyclerAdapter(list, MainActivity.this);
                recyclerView.setAdapter(adapter);
                search.setText("");
            }
        });


        addPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddPasswordActivity.class);
                startActivity(intent);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }
    private void filter(String text) {
        ArrayList<DataModel> filteredlist = new ArrayList<>();
        for (DataModel item : list) {

            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
                continue;
            }
            if (item.getDate().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
                continue;
            }
            if (item.getUserName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
                continue;
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
            RecyclerAdapter adapter = new RecyclerAdapter(filteredlist, MainActivity.this);
            recyclerView.setAdapter(adapter);
        } else {
            RecyclerAdapter adapter = new RecyclerAdapter(filteredlist, this);
            recyclerView.setAdapter(adapter);

        }
    }

    public void search(MenuItem v){
       search.setVisibility(View.VISIBLE);
       close.setVisibility(View.VISIBLE);
    }

    public void title(MenuItem v) {
        Collections.sort(list, new Comparator<DataModel>() {
            @Override
            public int compare(DataModel o1, DataModel o2) {
                return o1.title.compareToIgnoreCase(o2.title);
            }
        });
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    public void date(MenuItem v) {
        Collections.sort(list, new Comparator<DataModel>() {
            @Override
            public int compare(DataModel o1, DataModel o2) {
                return o1.date.compareToIgnoreCase(o2.date);
            }
        });
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    public void userName(MenuItem v) {
        Collections.sort(list, new Comparator<DataModel>() {
            @Override
            public int compare(DataModel o1, DataModel o2) {
                return o1.userName.compareToIgnoreCase(o2.userName);
            }
        });
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    public void filter(MenuItem v){
        linearLayout.setVisibility(View.VISIBLE);
    }

    public void contact(MenuItem v){
        Intent intent=new Intent(MainActivity.this,ContactUsActivity.class);
        startActivity(intent);
    }

    public void logout(MenuItem v){
       SharedPreferences.Editor editor=sharedPreferences.edit();
       editor.putString(KEY_EMAIL,null);
       editor.apply();
       startActivity(new Intent(MainActivity.this,LoginActivity.class));
       finish();
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedonse) {
            ActivityCompat.finishAffinity(this);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
        linearLayout.setVisibility(View.GONE);
        doubleBackToExitPressedonse = true;
    }


}
