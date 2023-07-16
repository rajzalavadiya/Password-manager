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

public class SignUpActivity extends AppCompatActivity {
    private String URL ="https://glexas.com/Raj/Android/SignUpPage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText name,email,password,conformPassword;
        TextView signIn,textView1 ;
        Button signUp;
        ImageButton imageButton1,imageButton2;
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        conformPassword=findViewById(R.id.conformPassword);
        signIn=findViewById(R.id.signIn);
        textView1=findViewById(R.id.textView1);
        signUp=findViewById(R.id.signUp);
        imageButton1=findViewById(R.id.imageButton1);
        imageButton2=findViewById(R.id.imageButton2);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    imageButton1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageButton1.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conformPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    imageButton1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    conformPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageButton1.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    conformPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String phoneNo= "[0-9]+";
                if(name.getText().toString().isEmpty()){
                    name.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(email.getText().toString().isEmpty()){
                    email.setHintTextColor(getResources().getColor(R.color.red));
                }
//                else if(!email.getText().toString().trim().matches(emailPattern) ){
//                    textView1.setText("Invalid email address");
//                }
                else if(password.getText().toString().isEmpty()){
                    password.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(password.length() < 6){
                    textView1.setText("Your password must be have atleast 6 char long");
                }
                else if(conformPassword.getText().toString().isEmpty()){
                    conformPassword.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(email.getText().toString().trim().matches(emailPattern) || email.getText().toString().trim().matches(phoneNo)){
                    if(conformPassword.getText().toString().equals(password.getText().toString())) {


                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Already exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    textView1.setText("");
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
                                map.put("name", name.getText().toString());
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