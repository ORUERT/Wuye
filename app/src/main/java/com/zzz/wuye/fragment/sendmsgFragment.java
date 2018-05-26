package com.zzz.wuye.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz.wuye.R;
import com.zzz.wuye.utils.Constants;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.logging.Logger;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.PushListener;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by oruret on 2018/4/19.
 */

public class sendmsgFragment extends Fragment implements View.OnClickListener{

    private EditText sendText;
    private Button sendButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_me, null);

        findViewById1(v);
        initView();
        return v;
    }
    public void findViewById1(View v) {
        sendText = v.findViewById(R.id.sendmsg_text);
        sendButton = v.findViewById(R.id.send_button);
    }

    public void initView() {
        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.send_button){
            BmobPushManager bmobPushManager = new BmobPushManager();

            bmobPushManager.pushMessageAll(sendText.getText().toString(), new PushListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        Toast.makeText(getContext(),"推送成功",Toast.LENGTH_SHORT);
                        Log.e("send","推送成功！");
                    }else {
                        Log.e("send","异常：" + e.getMessage());
                    }
                }
            });
        }
    }
}
