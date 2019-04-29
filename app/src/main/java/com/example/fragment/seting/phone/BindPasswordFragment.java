package com.example.fragment.seting.phone;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.fragment.seting.SetActivity;
import com.example.fragment.seting.setlist.SetListFragment;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author MW
 * @date 2019/4/27
 * <p>
 * 描述： 绑定/解绑手机号
 */

public class BindPasswordFragment extends Fragment {

    public static final String TAG = "BindPasswordFragment";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.iv_includeHeaderLeft)
    ImageView mIvIncludeHeaderLeft;
    @BindView(R.id.ed_tel)
    EditText mEdTel;
    @BindView(R.id.ed_message)
    EditText mEdMessage;
    @BindView(R.id.tv_message_code)
    TextView mTvMessageCode;
    @BindView(R.id.tv_submit)
    Button mTvSubmit;
    Unbinder unbinder;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.view_line_one)
    View mViewLineOne;
    @BindView(R.id.view_line_two)
    View mViewLineTwo;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.tv_phone_word)
    TextView mTvPhoneWord;

    private int mPhoneState;//账号电话状态  1.存在 2.不存在 3.显示绑定手机号
    private boolean mIsChecked = false;//获取短信验证码按钮  默认处于未点击状态


    public BindPasswordFragment() {
        // Required empty public constructor
    }

    public static BindPasswordFragment newInstance(int phoneState) {
        BindPasswordFragment fragment = new BindPasswordFragment();
        Bundle args = new Bundle();
        args.putInt(SetActivity.PHONE_STATE, phoneState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPhoneState = bundle.getInt(SetActivity.PHONE_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bind_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        switch (mPhoneState) {
            case 1://存在手机号 第二步 解绑
                mTvIncludeHeaderTitle.setText("解绑手机号");
                if (!TextUtils.isEmpty(GlobalVariables.USER_PHONE)) {
                    mEdTel.setText(GlobalVariables.getUserPhone());
                }
                mTvSubmit.setText("确认解绑");
                break;
            case 2://不存在手机号
                mTvIncludeHeaderTitle.setText("绑定手机号");
                break;
            case 3://存在手机号 展示首页
                mTvIncludeHeaderTitle.setText("已绑定手机号");
                mEdTel.setVisibility(View.GONE);//手机号
                mTvPhoneWord.setVisibility(View.GONE);
                mEdMessage.setVisibility(View.GONE);//验证码
                mTvMessageCode.setVisibility(View.GONE);//验证码按钮
                mViewLineOne.setVisibility(View.GONE);//第一道线
                mViewLineTwo.setVisibility(View.GONE);//第二道线
                mTvCode.setVisibility(View.GONE);
                mTvWord.setVisibility(View.VISIBLE);
                mTvPhone.setVisibility(View.VISIBLE);
                mTvPhone.setText(GlobalVariables.getUserPhone());
                mTvSubmit.setText("更换手机号");
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.tv_submit,R.id.tv_message_code})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                checkCondition();
                break;
            case R.id.tv_submit://提交按钮
                switch (mPhoneState) {
                    case 1://存在手机号 点击就是解绑
                        mTvIncludeHeaderTitle.setText("解绑手机号");
                        checkCondition();
                        break;
                    case 2://不存在手机号  点击是绑定
                        mTvIncludeHeaderTitle.setText("绑定手机号");
                        checkCondition();
                        break;
                    case 3://已存在手机号  点击就是解绑
                        intent = new Intent(getContext(), SetActivity.class);
                        intent.putExtra(Constant.FRAGMENT_ID, BindPasswordFragment.TAG);
                        intent.putExtra(SetActivity.PHONE_STATE, 1);
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.tv_message_code://获取短信验证码
                //mTvMessageCode.setEnabled(false);
                getMessageCode();
            default:
                break;
        }
    }


    //检查是否符合规则
    private void checkCondition() {
        String code = mEdMessage.getText().toString().trim();//短信验证码
        String mPhone = mEdTel.getText().toString().trim();//手机号码
        if (mPhone.length() != 11 || !mPhone.startsWith("1")) {
            mEdTel.setError("请输入正确的手机号码");
            return;
        }
        if (!mPhone.equals(GlobalVariables.getUserPhone())) {
            mEdTel.setError("手机号码与已绑定手机号码不同");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mEdMessage.setError("验证码不能为空");
            return;
        }
        //在满足条件后  开始进行解绑
        BmobSMS.verifySmsCode(mPhone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    BeanUserBase user = BmobUser.getCurrentUser(BeanUserBase.class);
                    user.setMobilePhoneNumber(mPhone);
                    if(mPhoneState==1){//解绑
                        user.setMobilePhoneNumberVerified(false);
                    }else{//否则为绑定
                        user.setMobilePhoneNumberVerified(true);
                    }
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //mTvInfo.append("绑定/解绑手机号码成功");
                                if(mPhoneState==1) {//解绑
                                    ToastUtil.showToast(getContext(), "手机号解绑成功");
                                    GlobalVariables.setUserPhone("");
                                }else{
                                    ToastUtil.showToast(getContext(), "手机号绑定成功");
                                    GlobalVariables.setUserPhone(mPhone);
                                }
                                getActivity().finish();
                                Intent intent=new Intent(getContext(), SetActivity.class);
                                intent.putExtra(Constant.FRAGMENT_ID, SetListFragment.TAG);
                                startActivity(intent);//跳到设置首页面
                            } else {
                                ToastUtil.showToast(getContext(), "手机号解绑失败");
                            }
                        }
                    });
                } else {
                    ToastUtil.showToast(getContext(), "验证码验证失败");
                }
            }
        });


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
                    mTvMessageCode.setText("重新获取验证码");
                }
            }.start();
        }
    }

    private boolean checkIsStandard() {
        String mPhone = mEdTel.getText().toString().trim();//手机号码
        if (mPhone.length() != 11 || !mPhone.startsWith("1")) {
            mEdTel.setError("请输入正确的手机号码");
            return false;
        }
        if (!mPhone.equals(GlobalVariables.getUserPhone())) {
            mEdTel.setError("手机号码与已绑定手机号码不同");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
