package io.github.wonthechan.registeration;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.net.sip.SipSession;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView; // 해당클래스의 멤버변수
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList); // 어댑터에 위에서 작성한 noticeList가 차레대로 들어가게 된다. (매칭)
        //noticeListView.setAdapter(adapter); // 어댑터에 들어가있는 모든 내용들이 각각의 view 형태로 리스트뷰에 보여진다.

        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); // 공지사항이 보이지 않게됨
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark)); // 버튼을 클릭하고 나서 그 버튼을 약간 어두운색으로 바꿔준다
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 이외 버튼들의 색깔은 밝은 색으로 바꾼다
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 이외 버튼들의 색깔은 밝은 색으로 바꾼다
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment()); // 현재 나타나는 fragment가 CourseFragment일 수 있도록 한다.
                fragmentTransaction.commit(); // 마무리
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); // 공지사항이 보이지 않게됨
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 버튼을 클릭하고 나서 그 버튼을 약간 어두운색으로 바꿔준다
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark)); // 이외 버튼들의 색깔은 밝은 색으로 바꾼다
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 이외 버튼들의 색깔은 밝은 색으로 바꾼다
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); // 공지사항이 보이지 않게됨
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 버튼을 클릭하고 나서 그 버튼을 약간 어두운색으로 바꿔준다
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); // 이외 버튼들의 색깔은 밝은 색으로 바꾼다
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark)); // 이외 버튼들의 색깔은 밝은 색으로 바꾼다
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        new BackgroundTask().execute();
    }
    // 서브클래스 작성
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://211.252.53.222/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // 파싱
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate= object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    count++;
                }
                noticeListView.setAdapter(adapter); // 어댑터에 들어가있는 모든 내용들이 각각의 view 형태로 리스트뷰에 보여진다.
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed(){
        // Main 화면에서 뒤로가기 버튼을 한번 누른 후 1.5초 이내로 또 버튼을 누르면 종료한다.
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis(); // 버튼을 처음 한번 눌렀을때의 시간을 저장
    }
}
