package com.example.login.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.example.fragment.seting.SetActivity;
import com.example.fragment.seting.phone.BindPasswordFragment;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author MW
 * @date 2019/4/29
 * <p>
 * 描述：重置密码
 */

public class ResetPassword extends Fragment {

    public static final String TAG = "ResetPassword";

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


    public ResetPassword() {
        // Required empty public constructor
    }


    public static ResetPassword newInstance(String param1, String param2) {
        ResetPassword fragment = new ResetPassword();
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


    }

    //检查是否符合规则
    private boolean  getMessageCode() {
        String mUserName=mEdUserName.getText().toString().trim();//用户名
        String code = mEdMessage.getText().toString().trim();//短信验证码
        String mPhone = mEdTel.getText().toString().trim();//手机号码
        if(TextUtils.isEmpty(mUserName)){
            mEdUserName.setError("用户名不能为空");
            return false;
        }
        if (mPhone.length() != 11 || !mPhone.startsWith("1")) {
            mEdTel.setError("请输入正确的手机号码");
            return false;
        }
        if (!mPhone.equals(GlobalVariables.getUserPhone())) {
            mEdTel.setError("手机号码与已绑定手机号码不同");
            return false;
        }
        BmobQuery<BeanUserBase> user = new  BmobQuery<>();
        user.addWhereEqualTo("username",mUserName);
        user.findObjects(new FindListener<BeanUserBase>() {
            @Override
            public void done(List<BeanUserBase> object, BmobException e) {
                if (e == null) {
                    if(!object.get(0).getMobilePhoneNumberVerified()  || !object.get(0).getMobilePhoneNumber().equals(mPhone)){
                        ToastUtil.showToast(getContext(),"用户名和手机号码匹配失败");
                    }
                } else {
                    ToastUtil.showToast(getContext(),"用户名和手机号码匹配失败");
                }
            }
        });
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
