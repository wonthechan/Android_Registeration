package io.github.wonthechan.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YeChan on 2018-01-09.
 */
// 회원 ID를 체크하여 회원가입이 가능한지 확인하는 class (ID 중복체크)
public class ValidateRequest extends StringRequest{

    final static private String URL = "http://211.252.53.222/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
