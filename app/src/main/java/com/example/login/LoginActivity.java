package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.fragment.note.TodayNotesFragment;
import com.example.fragment.seting.SetActivity;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.login.password.ResetPasswordFragment;
import com.example.main.MainActivity;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author MW
 * @date 2019/3/19
 * <p>
 * 描述：登录
 */

public class LoginActivity extends Activity {

    @BindView(R.id.ed_user_name)
    EditText mEdUserName;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    @BindView(R.id.tv_reset_password)
    TextView mTvResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();//页面初始化
    }

    private void initView() {

        SharedPreferences state = getSharedPreferences("state", MODE_PRIVATE);
        boolean mIsState = state.getBoolean("passwordState", false);
        mCheckBox.setChecked(mIsState);
        if (mIsState) {//判断 此时有没有记住密码
            SharedPreferences sp = getSharedPreferences("rememberPassword", MODE_PRIVATE);
            //SharedPreferences.Editor ed=sp.edit();
            mEdUserName.setText(sp.getString("username", "") + "");
            mEdPassword.setText(sp.getString("password", "") + "");
        }


    }

    @OnClick({R.id.bt_login, R.id.tv_register,R.id.tv_reset_password})
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.bt_login://登录
                checkLogin();
                break;
            case R.id.tv_register://注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_reset_password://忘记密码
                Intent intent=new Intent(LoginActivity.this, UserCenterActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, ResetPasswordFragment.TAG);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //登录操作
    private void checkLogin() {
        String account = mEdUserName.getText().toString().trim();//用户名
        String password = mEdPassword.getText().toString().trim();//用户密码

        if (TextUtils.isEmpty(account)) {
            ToastUtil.showToast(getApplicationContext(), "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(getApplicationContext(), "密码不能为空");
            return;
        }

        final BeanUserBase user = new BeanUserBase();
        user.setUsername(account);
        user.setPassword(password);

        user.login(new SaveListener<BeanUserBase>() {

            @Override
            public void done(BeanUserBase beanUserBase, BmobException e) {
                if (e == null) {
                    //GlobalVariables.setUsername(account);
                    checkPersonalInformation();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "登录失败" + e.toString());
                }
            }
        });

    }

    //查个人信息
    private void checkPersonalInformation() {
        final String account = mEdUserName.getText().toString().trim();//用户名
        String password = mEdPassword.getText().toString().trim();//用户密码
        BmobQuery<BeanUserBase> userBmobQuery = new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("username", account);
        userBmobQuery.setLimit(1).findObjects(new FindListener<BeanUserBase>() {
            @Override
            public void done(List<BeanUserBase> object, BmobException e) {
                if (e == null) {
                    //ToastUtil.showToast(getApplicationContext(), object.get(0).getObjectId() + "");
                    if(object.get(0).getPower() !=2 ) {
                        ToastUtil.showToast(getApplicationContext(), "登录成功");
                        GlobalVariables.setUsername(account);//存用户名
                        GlobalVariables.setUserObjectId(object.get(0).getObjectId());//存ObjectId
                        GlobalVariables.setUserNickName(object.get(0).getNickname());//用户昵称
                        GlobalVariables.setUserPower(object.get(0).getPower());
                        GlobalVariables.setUserPassword(password);//密码
                        if (!TextUtils.isEmpty(object.get(0).getMobilePhoneNumber()) && object.get(0).getMobilePhoneNumberVerified()) {
                            GlobalVariables.setUserPhone(object.get(0).getMobilePhoneNumber());
                        } else {
                            GlobalVariables.setUserPhone("");
                        }
                        if (object.get(0).getRole() == 2) {
                            GlobalVariables.setRole(2);
                        } else {
                            GlobalVariables.setRole(1);
                        }
                        //跳主页面
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{//拉黑人员
                        ToastUtil.showToast(getApplicationContext(), "你已被禁止登陆，请联系管理员解禁");
                    }
                } else {
                    ToastUtil.showToast(getApplicationContext(), "失败" + e.getMessage());
                }
            }
        });

    }


    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences state = getSharedPreferences("state", MODE_PRIVATE);
        SharedPreferences.Editor st = state.edit();
        st.putBoolean("passwordState", mCheckBox.isChecked());
        st.commit();

        SharedPreferences sp = getSharedPreferences("rememberPassword", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        if (mCheckBox.isChecked()) {//此时记住密码
            ed.putString("username", mEdUserName.getText().toString().trim());
            ed.putString("password", mEdPassword.getText().toString().trim());
            ed.commit();
        } else {//此时不记住密码
            ed.clear();
        }
    }
}
