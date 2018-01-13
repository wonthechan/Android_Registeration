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

        // 텍스트와 버튼의 변수 매칭
        final Button logingButton = (Button) findViewById(R.id.loginButton);
        // 로그인 버튼에 클릭 리스너를 달아 준다.
        logingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                finish(); // 현재 Activity(LoginActivity)는 종료한다.

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
