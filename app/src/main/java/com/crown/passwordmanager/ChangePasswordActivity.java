package com.crown.passwordmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private String URL = "https://glexas.com/Raj/Android/ChangePasswordPage.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText email,oldPassword,password,conformPassword;
        TextView textView1;
        ImageButton imageButton1,imageButton2,imageButton3;
        Button changePassword;
        email=findViewById(R.id.email);
        oldPassword=findViewById(R.id.oldPassword);
        password=findViewById(R.id.password);
        conformPassword=findViewById(R.id.conformPassword);
        textView1=findViewById(R.id.textView1);
        imageButton1=findViewById(R.id.imageButton1);
        imageButton2=findViewById(R.id.imageButton2);
        imageButton3=findViewById(R.id.imageButton3);
        changePassword=findViewById(R.id.changePassword);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    imageButton1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageButton1.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    imageButton2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageButton2.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conformPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    imageButton3.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    conformPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageButton3.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    conformPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String phoneNo= "[0-9]+";
                if(email.getText().toString().isEmpty()){
                    email.setHintTextColor(getResources().getColor(R.color.red));
                }

                else if(oldPassword.getText().toString().isEmpty()){
                    oldPassword.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(password.getText().toString().isEmpty()){
                    password.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(password.length() < 6){
                    textView1.setText("Your password must be have atleast 6 char long");
                }
                else if(conformPassword.getText().toString().isEmpty()){
                    conformPassword.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(email.getText().toString().trim().matches(emailPattern) || email.getText().toString().trim().matches(phoneNo) && email.getText().toString().length()<13){
                    if(conformPassword.getText().toString().equals(password.getText().toString())) {


                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Verify email or password", Toast.LENGTH_SHORT).show();
                                } else {
                                    textView1.setText("");
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("email", email.getText().toString());
                                map.put("oldPassword", oldPassword.getText().toString());
                                map.put("password", password.getText().toString());

                                return map;
                            }
                        };
                        queue.add(request);
                    }
                    else {
                        textView1.setText("Password is not matching");
                    }
                }
                else {
                    textView1.setText("phone No. and E-mail only");
                }

            }
        });

    }
}