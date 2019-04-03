package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.main.R;
import com.example.util.ToastUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author MW
 * @date 2019/3/19
 * <p>
 * 描述：注册界面
 */

public class RegisterActivity extends Activity {

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_message_code)
    TextView mTvMessageCode;
    @BindView(R.id.tv_register)
    Button mTvRegister;
    @BindView(R.id.ed_ueser_name)
    EditText mEdUserName;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.ed_repeat_password)
    EditText mEdRepeatPassword;
    @BindView(R.id.ed_tel)
    EditText mEdTel;
    @BindView(R.id.ed_message)
    EditText mEdMessage;

    private boolean mIsChecked = false;//默认处于未点击状态
    private boolean mIsSize = false;//全局变量  表示用户名是否存在  默认不存在

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("注册");
    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.tv_message_code, R.id.tv_register})
    public void onClickViewById(View view) {
        switch (view.getId()) {
            case R.id.li_includeHeaderLeft://返回
                onBackPressed();
                break;
            case R.id.tv_message_code://获取短信验证码
                //mTvMessageCode.setEnabled(false);
                getMessageCode();
                break;
            case R.id.tv_register://注册按钮
                makeRegister();
                break;
            default:
                break;
        }
    }

    //执行注册
    private void makeRegister() {
        String code = mEdMessage.getText().toString().trim();
        String phone = mEdTel.getText().toString().trim();//电话
        String pwd = mEdPassword.getText().toString().trim();//密码
        //String rePwd = mEdRepeatPassword.getText().toString().trim();//确认密码
        String name = mEdUserName.getText().toString().trim();//用户名

        if (TextUtils.isEmpty(code)) {
            mEdMessage.setError("验证码不能为空");
            return;
        }

        if (checkIsStandard()) {//
            BeanUserBase user = new BeanUserBase();
            //设置手机号码（必填）
            user.setMobilePhoneNumber(phone);
            //设置用户名，如果没有传用户名，则默认为手机号码
            user.setUsername(name);
            //设置用户密码
            user.setPassword(pwd);
            //设置额外信息：此处为年龄
            /* user.setAge(18);*/
            user.signOrLogin(code, new SaveListener<BeanUserBase>() {

                @Override
                public void done(BeanUserBase user, BmobException e) {
                    if (e == null) {
                       /* mTvInfo.append("短信注册或登录成功：" + user.getUsername());
                        startActivity(new Intent(UserSignUpOrLoginSmsActivity.this, UserMainActivity.class));*/
                        ToastUtil.showToast(getApplicationContext(), "注册成功");
                    } else {
                        /*mTvInfo.append("短信注册或登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");*/
                        ToastUtil.showToast(getApplicationContext(), "注册失败" + e.getErrorCode() + "-" + e.getMessage());
                    }
                }
            });

        }

    }

    //获取短信验证码
    private void getMessageCode() {
        //这里首先要检查前期输入是否正确
        if (checkIsStandard()) {

            //android自带的类
            CountDownTimer timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvMessageCode.setEnabled(false);
                    mTvMessageCode.setText("已发送(" + millisUntilFinished / 1000 + ")");
                    if (mIsChecked) {
                        return;
                    } else {//此时处于未点击状态
                        mIsChecked = true;
                        String phone = mEdTel.getText().toString().trim();//电话
                        //请求发送验证码接口
                        BmobSMS.requestSMSCode(phone, "", new QueryListener<Integer>() {
                            @Override
                            public void done(Integer smsId, BmobException e) {
                                if (e == null) {
                                    //mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                                    ToastUtil.showToast(getApplicationContext(), "短信发送成功");
                                } else {
                                    // mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                                    ToastUtil.showToast(getApplicationContext(), "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFinish() {
                    if (!mIsChecked) {
                        mIsChecked = true;
                    }
                    mTvMessageCode.setEnabled(true);
                    mTvMessageCode.setText("重新获取验证码");
                }
            }.start();
        }
    }


    //判断输入是否规范
    private boolean checkIsStandard() {

        String phone = mEdTel.getText().toString().trim();//电话
        String pwd = mEdPassword.getText().toString().trim();//密码
        String rePwd = mEdRepeatPassword.getText().toString().trim();//确认密码
        String name = mEdUserName.getText().toString().trim();//用户名

        if (TextUtils.isEmpty(name)) {
            mEdUserName.setError("请输入用户名");
            return false;
        }

        if (name.length() < 6) {
            mEdUserName.setError("用户名至少6位");
            return false;
        }

        if (TextUtils.isEmpty(pwd)) {
            mEdPassword.setError("请输入新密码");
            return false;
        }

        if (TextUtils.isEmpty(rePwd)) {
            mEdRepeatPassword.setError("请输入确认密码");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            mEdTel.setError("手机号码不能为空");
            return false;
        }
        if (phone.length() != 11 || !phone.startsWith("1")) {
            mEdTel.setError("请输入正确的手机号码");
            return false;
        }
       /* if (!isMatcherFinded("^(?![^a-zA-Z]+$)(?!\\D+$).{8,20}$", pwd)) {
            mEdPassword.setError("请输入包括数字和字母、长度8到20位的密码组合");
            return false;
        }
        if (!isMatcherFinded("^(?![^a-zA-Z]+$)(?!\\D+$).{8,20}$", rePwd)) {
            mEdRepeatPassword.setError("请输入包括数字和字母、长度8到20位的密码组合");
            return false;
        }*/
        if (!pwd.equals(rePwd)) {
            ToastUtil.showToast(getApplicationContext(), "确认密码和新密码不相同");
            return false;
        }

        BmobQuery<BeanUserBase> user = new BmobQuery<>();
        user.addWhereEqualTo("username", name);
        user.findObjects(new FindListener<BeanUserBase>() {
            //boolean mIsSize;//局部变量  表示用户名是否存在  默认不存在
            @Override
            public void done(List<BeanUserBase> object, BmobException e) {
                if (e == null) {
                    //Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                    if (object.size() != 0) {
                        mIsSize = true;
                        ToastUtil.showToast(getApplicationContext(), "存在相同的用户名，请重新输入");
                    }
                } else {
                    ToastUtil.showToast(getApplicationContext(), "错误" + e.toString());
                }
            }
        });

        if (mIsSize) {
            mIsSize = false;
            return false;
        }

        return true;

    }


    //密码规则
    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    /*@Override
    protected void onStart() {
        super.onStart();
        Log.d("11111","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("11111","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("11111","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("11111","onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("11111","onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("11111","onDestroy");
    }*/
}
