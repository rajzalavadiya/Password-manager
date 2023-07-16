package com.crown.passwordmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordActivity extends AppCompatActivity {
    private String URL ="https://glexas.com/Raj/Android/UpdateData.php";
    private String URLL ="https://glexas.com/Raj/Android/DeleteData.php";
    private TextView title,date,userName,password;
    private ImageView imageView,imageView2,imageView3;
    private Button button,button2;
    private Intent intent;
    private final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);


        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        imageView = findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);


            intent = getIntent();
            title.setText(intent.getStringExtra("title"));
            date.setText(intent.getStringExtra("date"));
            userName.setText(intent.getStringExtra("userName"));
            password.setText(intent.getStringExtra("password"));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ((ImageView) (view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePasswordActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdatePasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (date.getText().toString().isEmpty()) {
                    date.setHintTextColor(getResources().getColor(R.color.red));
                } else if (userName.getText().toString().isEmpty()) {
                    userName.setHintTextColor(getResources().getColor(R.color.red));
                } else if (password.getText().toString().isEmpty()) {
                    password.setHintTextColor(getResources().getColor(R.color.red));
                } else {

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.trim().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(UpdatePasswordActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                    ) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("title", title.getText().toString());
                            map.put("date", date.getText().toString());
                            map.put("user", userName.getText().toString());
                            map.put("password", password.getText().toString());
                            map.put("NO", intent.getStringExtra("NO"));
                            return map;
                        }
                    };
                    queue.add(request);
                }

            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, URLL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(UpdatePasswordActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("NO", intent.getStringExtra("NO"));
                        return map;
                    }
                };
                queue.add(request);
            }
        });
    }
    public  void onBackPressed(){
        startActivity(new Intent(UpdatePasswordActivity.this,MainActivity.class));
    }
}