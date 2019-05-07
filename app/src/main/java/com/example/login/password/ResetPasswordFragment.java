package com.example.login.password;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.main.R;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author MW
 * @date 2019/4/29
 * <p>
 * 描述：重置密码
 */

public class ResetPasswordFragment extends Fragment {

    public static final String TAG = "ResetPasswordFragment";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.ed_tel)
    EditText mEdTel;
    @BindView(R.id.ed_message)
    EditText mEdMessage;
    @BindView(R.id.tv_message_code)
    TextView mTvMessageCode;
    @BindView(R.id.tv_submit)
    Button mTvSubmit;
    @BindView(R.id.ed_user_name)
    EditText mEdUserName;
    Unbinder unbinder;

    private boolean mIsChecked = false;//短信验证码按钮  默认处于未点击状态
    private boolean mIsCorrect = true;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    public static ResetPasswordFragment newInstance() {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    //重置密码
    private void initView() {
        mTvIncludeHeaderTitle.setText("重置密码");

    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.tv_submit, R.id.tv_message_code})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.tv_submit://提交按钮
                submit();
                break;
            case R.id.tv_message_code://获取短信验证码
                //mTvMessageCode.setEnabled(false);
                getMessageCode();
            default:
                break;
        }
    }

    private void submit() {
        if (checkCondition()) {
            String code = mEdMessage.getText().toString().trim();//短信验证码
            String mPhone = mEdTel.getText().toString().trim();//手机号码
            if (TextUtils.isEmpty(code)) {
                mEdMessage.setError("短信验证码不能为空");
                return;
            }
            BmobUser.resetPasswordBySMSCode(code, "666666", new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ToastUtil.showToast(getContext(), "重置成功");
                        getActivity().finish();
                    } else {
                        ToastUtil.showToast(getContext(), "重置失败：" + e.getErrorCode() + "-" + e.getMessage());
                    }
                }
            });
        }
    }

    //检查是否符合规则
    private boolean checkCondition() {
        // mIsCorrect = true;
        String mUserName = mEdUserName.getText().toString().trim();//用户名
        String mPhone = mEdTel.getText().toString().trim();//手机号码
        if (TextUtils.isEmpty(mUserName)) {
            mEdUserName.setError("用户名不能为空");
            return false;
        } else if (mPhone.length() != 11 || !mPhone.startsWith("1")) {
            mEdTel.setError("请输入正确的手机号码");
            return false;
        } else {
            return true;
        }
    }


    private boolean checkUserName() {
        mIsCorrect = true;
        String mUserName = mEdUserName.getText().toString().trim();//用户名
        String mPhone = mEdTel.getText().toString().trim();//手机号码
        BmobQuery<BeanUserBase> user = new BmobQuery<>();
        user.addWhereEqualTo("username", mUserName);
        user.findObjects(new FindListener<BeanUserBase>() {
            @Override
            public void done(List<BeanUserBase> object, BmobException e) {
                if (e == null) {
                    if (!object.get(0).getMobilePhoneNumberVerified() || !object.get(0).getMobilePhoneNumber().equals(mPhone)) {
                        ToastUtil.showToast(getContext(), "该用户名下绑定不是该手机号");
                        mIsCorrect = false;
                    }
                } else {
                    mIsCorrect = false;
                    ToastUtil.showToast(getContext(), "用户名和手机号码匹配失败");
                }
            }
        });
        if (!mIsCorrect) {
            return false;
        }
        return true;
    }


    //检查是否符合规则
    private void getMessageCode() {
        if (checkCondition()) {
            //mIsCorrect = true;
            String mUserName = mEdUserName.getText().toString().trim();//用户名
            String mPhone = mEdTel.getText().toString().trim();//手机号码
            BmobQuery<BeanUserBase> user = new BmobQuery<>();
            user.addWhereEqualTo("username", mUserName);
            user.findObjects(new FindListener<BeanUserBase>() {
                @Override
                public void done(List<BeanUserBase> object, BmobException e) {
                    if (e == null) {
                        if (!object.get(0).getMobilePhoneNumberVerified() || !object.get(0).getMobilePhoneNumber().equals(mPhone)) {
                            ToastUtil.showToast(getContext(), "该用户名下绑定不是该手机号");
                            mIsCorrect = false;
                        } else {
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
                                                    ToastUtil.showToast(getContext(), "短信发送成功");
                                                } else {
                                                    ToastUtil.showToast(getContext(), "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
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
                                    mTvMessageCode.setText("重新获取");
                                }
                            }.start();

                        }
                    } else {
                        mIsCorrect = false;
                        ToastUtil.showToast(getContext(), "用户名和手机号码匹配失败");
                    }
                }
            });
           /* if (!mIsCorrect) {
                return ;
            }*/


        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
