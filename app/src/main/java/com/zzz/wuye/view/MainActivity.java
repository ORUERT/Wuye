package com.zzz.wuye.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.zzz.wuye.Message.MessageFragment;
import com.zzz.wuye.R;
import com.zzz.wuye.arrearage.arrearage_Fragment;
import com.zzz.wuye.enterPage.view.enterPage_fragment;
import com.zzz.wuye.fragment.FoundFragment;
import com.zzz.wuye.fragment.MeFragment;
import com.zzz.wuye.personPage.view.OrderFragment;
import com.zzz.wuye.fragment.TrainingFragment;
import com.zzz.wuye.fragment.test_fragment;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.AppManager;
import com.zzz.wuye.view.base.BaseActivity;
import com.zzz.wuye.view.test.VideoPlayer;

import org.litepal.tablemanager.Connector;

import cn.bmob.v3.BmobUser;

//import static com.zzz.wuye.utils.Constants.setStatusBarColor;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout tabTrain;
    private LinearLayout tabFound;
    private LinearLayout tabMe;

    private ImageView btnCheck;
    private ImageView btnAddNews;
    private ImageView icoTrain;
    private ImageView icoFound;
    private ImageView icoMe;

    private TextView txtTrain;
    private TextView txtFound;
    private TextView txtMe;
    private TextView txtTitle;

    private TrainingFragment trainingFragment;
    private FoundFragment foundFragment;
    private MeFragment meFragment;
    private OrderFragment orderFragment;
    private test_fragment test_fragment;
    private enterPage_fragment enterPage_fragment;
    private arrearage_Fragment arrearage_fragment;
    private MessageFragment message_fragment;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.blue_title));

        Connector.getDatabase();
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        tabTrain = $(R.id.bottom_train);
        tabFound = $(R.id.bottom_found);
        tabMe = $(R.id.bottom_me);
        btnCheck = $(R.id.up_btn_check);
        btnAddNews = $(R.id.found_new_add);

        icoTrain = $(R.id.bottom_ico_train);
        icoFound = $(R.id.bottom_ico_found);
        icoMe = $(R.id.bottom_ico_me);

        txtTrain = $(R.id.bottom_txt_train);
        txtFound = $(R.id.bottom_txt_found);
        txtMe = $(R.id.bottom_txt_me);
        txtTitle = $(R.id.titleText);
    }

    @Override
    protected void initView() {
        tabTrain.setOnClickListener(this);
        tabFound.setOnClickListener(this);
        tabMe.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnAddNews.setOnClickListener(this);

        trainingFragment = new TrainingFragment();
        foundFragment = new FoundFragment();
        meFragment = new MeFragment();
        orderFragment = new OrderFragment();
        test_fragment = new test_fragment();
        arrearage_fragment = new arrearage_Fragment();
        enterPage_fragment = new enterPage_fragment();
        message_fragment = new MessageFragment();
        refreashFragment(R.id.bottom_train);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_train:
                changeTabState(R.id.bottom_train);
                changeTitle(R.string.title_train);
                topButtonChange(R.id.up_btn_check);
                refreashFragment(R.id.bottom_train);
                break;
            case R.id.bottom_found:
                topButtonChange(R.id.found_new_add);
                changeTabState(R.id.bottom_found);
                changeTitle(R.string.title_found);
                refreashFragment(R.id.bottom_found);
                break;
            case R.id.bottom_me:
                topButtonChange(0);
                changeTabState(R.id.bottom_me);
                changeTitle(R.string.title_me);
                refreashFragment(R.id.bottom_me);
                break;
            case R.id.up_btn_check:
                openActivity(BeforeDateCheckActivity.class);
                break;
            case R.id.found_new_add:
//                openActivity(ReleaseNewsActivity.class);
                break;
        }
    }

    public void topButtonChange(int id) {
        if (id == R.id.up_btn_check) {
            btnCheck.setVisibility(View.VISIBLE);
            btnAddNews.setVisibility(View.GONE);
        } else if (id == R.id.found_new_add) {
            btnCheck.setVisibility(View.GONE);
            btnAddNews.setVisibility(View.VISIBLE);
        } else {
            btnCheck.setVisibility(View.VISIBLE);
            btnAddNews.setVisibility(View.GONE);
        }
    }

    private void changeTitle(int stringId) {
        // txtTitle.setText(getResources().getString(stringId));
    }

    /**
     * 切换Fragment
     * @param btnId
     */
    private void refreashFragment(int btnId) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (btnId) {
            case R.id.bottom_train:
                if(BmobUser.getCurrentUser(User.class).getType() == 1||BmobUser.getCurrentUser(User.class).getType() == 0){
                    transaction.replace(R.id.fragment_container,message_fragment);
                }else {

                }
                break;
            case R.id.bottom_found:
                transaction.replace(R.id.fragment_container,enterPage_fragment );
                break;
            case R.id.bottom_me:
                transaction.replace(R.id.fragment_container,meFragment);
                break;
        }
        transaction.commit();
    }

    private void changeTabState(int tabId) {
        if (tabId == R.id.bottom_train) {
            icoTrain.setImageResource(R.drawable.ic_question_answer_blue_24dp);
            txtTrain.setTextColor(getResources().getColor(R.color.blue_content));
        } else {
            icoTrain.setImageResource(R.drawable.ic_question_answer_gray_24dp);
            txtTrain.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_found) {
            icoFound.setImageResource(R.drawable.ic_home_blue_24dp);
            txtFound.setTextColor(getResources().getColor(R.color.blue_content));
        } else {
            icoFound.setImageResource(R.drawable.ic_home_gray_24dp);
            txtFound.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_me) {
            icoMe.setImageResource(R.drawable.ic_person_black_24dp);
            txtMe.setTextColor(getResources().getColor(R.color.blue_content));
        } else {
            icoMe.setImageResource(R.drawable.ic_person_gray_24dp);
            txtMe.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 800) {
                DisplayToast("再按一次退出");
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                AppManager.getInstance().killAllActivity();
                AppManager.getInstance().AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openTestVideo(View v) {

        openActivity(VideoPlayer.class);
    }
}
