package io.github.wonthechan.registeration;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 회원가입 버튼 눌렀을 때 intent를 통해 RegisterActivity 로 넘어간다.
        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        // 텍스트와 버튼의 변수 매칭
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button logingButton = (Button) findViewById(R.id.loginButton);
        // 로그인 버튼에 클릭 리스너를 달아 준다.
        logingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString(); // 변수화 한 후 final로 고정시킨 객체로 부터 string을 뽑아내 userID에 저장한다.
                String userPassword = passwordText.getText().toString();
                // LoginRequest를 통해 보낸 요청에 대한 반응(response)를 감지하는 리스너를 달아준다.
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success"); // success의 값은 true(DB상에 해당하는 ID를 발견) or false(DB상에서 해당하는 ID를 찾을 수 없음)
                            // 즉 success 가 true인 경우는 로그인에 정상적으로 성공한 경우
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공했습니다")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                // 로그인에 성공하였으므로 intent를 이용해 MainActivity로 넘어갈 수 있다.
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                finish(); // 현재 Activity(LoginActivity)는 종료한다.
                            }
                            // 로그인에 실패한 경우
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 실패했습니다")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        } catch(Exception e)
                        {
                            e.printStackTrace(); // 예외 처리까지 해줄것 (JSON을 사용하므로)
                        }
                    }
                };
                // Login 요청(Request)를 보낸다.(LoignRequest 객체를 만듬)
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        // 현재 dialog 가 켜져있을 경우에는 함부로 종료가 되지 않도록 함.
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null; // 사실 큰 의미는 없지만 프로그램의 완성도를 높이는 부분
        }
    }
}
