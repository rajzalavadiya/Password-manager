package com.crown.passwordmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.passwordmanager.R;

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {
    private String URL= "https://glexas.com/Raj/Android/ContactPage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        EditText email,name,subject,massage;
        TextView textView1;
        Button submit;
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        subject=findViewById(R.id.subject);
        massage=findViewById(R.id.massage);
        textView1=findViewById(R.id.textView1);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
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
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    textView1.setText("Invalid email address");
                }
                else if(massage.getText().toString().isEmpty()){
                    massage.setHintTextColor(getResources().getColor(R.color.red));
                }

                else{
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request= new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            subject.setText("");
                            massage.setText("");
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
                            map.put("name",name.getText().toString());
                            map.put("subject",subject.getText().toString());
                            map.put("massage",massage.getText().toString());
                            return map;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(request);
                }
            }
        });
    }
}