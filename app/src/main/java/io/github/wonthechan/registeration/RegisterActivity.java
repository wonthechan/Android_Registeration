package io.github.wonthechan.registeration;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userCampus;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major_seoul, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // EditText 변수화
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        // radioButton 변수화 (gender)
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        // radioButton 변수화 (campus)
        RadioGroup campusGroup = (RadioGroup) findViewById(R.id.campusGroup);
        int campusGroupID = campusGroup.getCheckedRadioButtonId();
        userCampus = ((RadioButton) findViewById(campusGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString(); // RadioButton은 따로 빼서 변수로 저장한다.
            }
        });
        // 캠퍼스에 따라 보여지는 major spinner 가 달라짐
        campusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton campusButton = (RadioButton) findViewById(i);
                userCampus = campusButton.getText().toString(); // RadioButton은 따로 빼서 변수로 저장한다.

                if(userCampus.equals("글로벌캠퍼스"))
                {
                    adapter = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.major_global, android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
                else
                {
                    adapter = ArrayAdapter.createFromResource(RegisterActivity.this, R.array.major_seoul, android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }
        });

        // ID 중복체크 확인 버튼 기능 구현
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                // 들어온 ID값이 정상적이라면 중복체크 요청을 보내고 리스너로 감지 한다.
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            //Log.e("TAG", "JSONObject 들어옴");
                            boolean success = jsonResponse.getBoolean("success");
                            // success 가 true 인 경우, ID를 사용할 수 있으며 false인 경우에는 중복되어 사용할 수 없는 ID 이다.
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false); // 중복체크가 완료되면 ID값을 더이상 수정할 수 없도록 한다
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                return;
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });
        // 회원가입 버튼 구현
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();
                // 현재 중복체크가 되어있지 않으면 중복체크를 하라는 메세지를 띄운다.
                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요!")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return; // 함수 종료
                }

                // 하나라도 빈공간이 있을 경우 회원가입을 정상적으로 진행할 수 없다.
                if(userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userGender.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return; // 함수 종료
                }

                // 문제 없이 회원 정보를 입력하였다면 등록 요청을 보내고 리스너로 감지한다.
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            //Log.e("TAG", "JSONObject 들어옴");
                            boolean success = jsonResponse.getBoolean("success");
                            // success 가 true 인 경우, ID를 사용할 수 있으며 false인 경우에는 중복되어 사용할 수 없는 ID 이다.
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish(); // 회원가입이 완료되었으므로 해당 창을 닫는다.
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원등록에 실패 하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userCampus, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    // 회원등록이 이루어지고 회원등록창이 꺼지게 되면 실행이 되는 부분
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
