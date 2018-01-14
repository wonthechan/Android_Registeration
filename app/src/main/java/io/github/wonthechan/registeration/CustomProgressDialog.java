package io.github.wonthechan.registeration;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by YeChan on 2018-01-13.
 */

public class CustomProgressDialog extends Dialog{
    public CustomProgressDialog(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 다이얼 로그 제목 날리기
        setContentView(R.layout.custom_dialog); // 다이얼로그에 박을 레이아웃
    }
}
