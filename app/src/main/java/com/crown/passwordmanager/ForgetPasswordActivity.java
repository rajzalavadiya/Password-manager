package com.crown.passwordmanager;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.passwordmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {
    public String otp,userId;
    public String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public String phoneNo= "[0-9]+";
    private String verification ="filed";
    public FirebaseAuth auth;
    private String URL ="https://glexas.com/Raj/Android/ForgetPasswordPage.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        EditText email,otpChack,password,conformPassword;
        TextView textView1;
        Button sendOtp,verify,resetPassword;
        ImageButton imageButton1,imageButton2;
        email=findViewById(R.id.email);
        otpChack=findViewById(R.id.otp);
        password=findViewById(R.id.password);
        conformPassword=findViewById(R.id.conformPassword);
        sendOtp=findViewById(R.id.sendotp);
        textView1=findViewById(R.id.textView1);
        verify=findViewById(R.id.verify);
        resetPassword=findViewById(R.id.resetPassword);
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
                    imageButton2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    conformPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    imageButton2.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    conformPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                int number=rnd.nextInt(999999);
                otp = String.format("%06d",number);

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Random rnd = new Random();
                                int number=rnd.nextInt(999999);
                                otp = String.format("%06d",number);
                            }
                        },
                        120000
                );


                if(email.getText().toString().isEmpty()){
                    email.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(email.getText().toString().trim().matches(emailPattern)){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request= new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            textView1.setText("");
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            userId=email.getText().toString();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("email",email.getText().toString());
                            map.put("otp",otp);
                            return map;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(500000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(request);


                }
                else if( email.getText().toString().matches(phoneNo) && email.getText().toString().length()<13){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + email.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            ForgetPasswordActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    userId = email.getText().toString();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    userId = email.getText().toString();
                                    Toast.makeText(getApplicationContext(), "otp send sucessfully", Toast.LENGTH_SHORT).show();
                                    otp =s;
                                }
                            }
                    );
                }
                else{
                    textView1.setText("Invalid email address");
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().matches(phoneNo) && email.getText().toString().length()<13){
                    if(!otpChack.getText().toString().isEmpty()){
                        PhoneAuthCredential phoneAuthCredential =PhoneAuthProvider.getCredential(
                                otp,otpChack.getText().toString()
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    verification = "sucess";
                                    Toast.makeText(getApplicationContext(),"Verification has been sucessfully",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Wrong otp",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Enter otp",Toast.LENGTH_LONG).show();
                    }
                }
                else  if(email.getText().toString().trim().matches(emailPattern)){
                    if(otpChack.getText().toString().equals(otp)){
                        verification = "sucess";
                        Toast.makeText(getApplicationContext(),"Verification has been sucessfully",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong otp",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Email and Phone No. Only",Toast.LENGTH_SHORT).show();
                }

            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(password.getText().toString().isEmpty()){
                    password.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(conformPassword.getText().toString().isEmpty()){
                    conformPassword.setHintTextColor(getResources().getColor(R.color.red));
                }
                else if(conformPassword.length() < 6){
                    textView1.setText("Your password must be have atleast 6 char long");
                }
                else if(conformPassword.getText().toString().equals(password.getText().toString())){
                    if(verification =="sucess") {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.trim().isEmpty()) {
                                    Toast.makeText(getApplicationContext(),"Password has been not changed",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    textView1.setText("");
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);
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
                                map.put("email", userId);
                                map.put("password", password.getText().toString());
                                return map;
                            }
                        };
                        queue.add(request);
                    }

                    else{
                        Toast.makeText(getApplicationContext(),"first verify otp",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    textView1.setText("Password is not matching");
                }
            }
        });


    }
}