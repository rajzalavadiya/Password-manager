package com.crown.passwordmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddPasswordActivity extends AppCompatActivity {
    private String URL ="https://glexas.com/Raj/Android/AddPassword.php";

    private EditText title,date,user,password;
    private ImageView imageView1,imageView2,imageView3;
    private Button submit;
    private Calendar c =Calendar.getInstance();
    private static  String NAME ="Login";
    private static  String KEY_EMAIL ="email";
    private static  String KEY_PASS ="password";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        sharedPreferences=getSharedPreferences(NAME,MODE_PRIVATE);

        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        user=findViewById(R.id.user);
        password=findViewById(R.id.password);
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        submit=findViewById(R.id.submit);

        date.setText(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1) +"-" + c.get(Calendar.DATE));
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog= new DatePickerDialog(AddPasswordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year +"-" + (month+1) +"-"+ dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    imageView2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageView2.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPasswordActivity.this,MainActivity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().isEmpty()){
                    title.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(user.getText().toString().isEmpty()){
                    user.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(password.getText().toString().isEmpty()){
                    password.setHintTextColor(getResources().getColor(R.color.red));
                }

                else {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Not Submitted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                title.setText("");
                                date.setText(c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH));
                                user.setText("");
                                user.setHintTextColor(getResources().getColor(R.color.hint));
                                password.setText("");
                                password.setHintTextColor(getResources().getColor(R.color.hint));
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("email", sharedPreferences.getString(KEY_EMAIL, null));
                            map.put("title", title.getText().toString());
                            map.put("date", date.getText().toString());
                            map.put("user", user.getText().toString());
                            map.put("password", password.getText().toString());

                            return map;
                        }
                    };
                    queue.add(request);
                }
            }
        });

    }
    public  void onBackPressed(){
        startActivity(new Intent(AddPasswordActivity.this,MainActivity.class));
    }
}