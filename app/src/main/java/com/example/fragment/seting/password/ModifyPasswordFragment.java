package com.example.fragment.seting.password;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author MW
 * @date 2019/4/26
 * <p>
 * 描述：修改密码
 */

public class ModifyPasswordFragment extends Fragment {
    public static final String TAG = "ModifyPasswordFragment";
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.ed_old_password)
    EditText mEdOldPassword;
    @BindView(R.id.ed_repat_password)
    EditText mEdRepatPassword;
    @BindView(R.id.ed_new_password)
    EditText mEdNewPassword;
    Unbinder unbinder;
    @BindView(R.id.tv_submit)
    Button mTvSubmit;

    public ModifyPasswordFragment() {
        // Required empty public constructor
    }

    public static ModifyPasswordFragment newInstance() {
        ModifyPasswordFragment fragment = new ModifyPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_modify_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("更改密码");

    }

    @OnClick({R.id.li_includeHeaderLeft,R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.tv_submit://提交按钮
                submitAlter();
                break;
            default:
                break;
        }
    }

    //确认更改
    private void submitAlter() {
        String oldPassword=mEdOldPassword.getText().toString().trim();//当前密码
        String newPassword=mEdNewPassword.getText().toString().trim();//新密码
        String repatPassword=mEdRepatPassword.getText().toString().trim();//重复密码

        if(TextUtils.isEmpty(oldPassword)){
            ToastUtil.showToast(getContext(),"当前密码不能为空");
            return;
        }
        if(!oldPassword.equals(GlobalVariables.getUserPassword())){
            ToastUtil.showToast(getContext(),"当前密码不正确");
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            ToastUtil.showToast(getContext(),"新密码不能为空");
            return;
        }
        if(TextUtils.isEmpty(repatPassword)){
            ToastUtil.showToast(getContext(),"重复密码不能为空");
            return;
        }

        if (!newPassword.equals(repatPassword)) {
            ToastUtil.showToast(getContext(), "确认密码和新密码不相同");
            return;
        }
        if (newPassword.equals(oldPassword)) {
            ToastUtil.showToast(getContext(), "当前密码和新密码相同");
            return;
        }

        BeanUserBase beanUserBase = new BeanUserBase();
        beanUserBase.setPassword(newPassword);
        beanUserBase.update(GlobalVariables.getUserObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.showToast(getContext(), "更新成功");
                    GlobalVariables.setUserPassword(newPassword);
                }else{
                    ToastUtil.showToast(getContext(), "更新失败" +e.getErrorCode()+e.getMessage());
                }
            }

        });





    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
