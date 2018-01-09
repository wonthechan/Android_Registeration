package io.github.wonthechan.registeration;

import android.support.v4.app.FragmentManager;
import android.net.sip.SipSession;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        noticeList.add(new Notice("공지사항입니다.", "임예찬", "2018-01-09")); // 예시 DATA
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList); // 어댑터에 위에서 작성한 noticeList가 차레대로 들어가게 된다. (매칭)
        noticeListView.setAdapter(adapter); // 어댑터에 들어가있는 모든 내용들이 각각의 view 형태로 리스트뷰에 보여진다.

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
    }
}
