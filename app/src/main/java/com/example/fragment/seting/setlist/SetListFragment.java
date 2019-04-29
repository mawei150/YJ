package com.example.fragment.seting.setlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fragment.seting.SetActivity;
import com.example.fragment.seting.password.ModifyPasswordFragment;
import com.example.fragment.seting.phone.BindPasswordFragment;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author MW
 * @date 2019/4/26
 * <p>
 * 描述： 设置列表
 */

public class SetListFragment extends Fragment {
    public final static String TAG = "SetListFragment";
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_password)
    TextView mTvPassword;
    Unbinder unbinder;


    public SetListFragment() {
        // Required empty public constructor
    }


    public static SetListFragment newInstance() {
        SetListFragment fragment = new SetListFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("设置");

    }


    @OnClick({R.id.li_includeHeaderLeft, R.id.tv_password, R.id.tv_phone})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.tv_password://修改密码
                intent = new Intent(getContext(), SetActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, ModifyPasswordFragment.TAG);
                startActivity(intent);
                break;
            case R.id.tv_phone://手机号绑定与解绑
                //这一块的逻辑是  如果账号绑定手机号  走解绑界面  否则走绑定界面
                if (TextUtils.isEmpty(GlobalVariables.getUserPhone())) {//手机号为空
                    intent=new Intent(getContext(),SetActivity.class);
                    intent.putExtra(SetActivity.PHONE_STATE,2);
                    intent.putExtra(Constant.FRAGMENT_ID, BindPasswordFragment.TAG);
                    startActivity(intent);
                } else {//存在手机好
                    intent=new Intent(getContext(),SetActivity.class);
                    intent.putExtra(SetActivity.PHONE_STATE,3);
                    intent.putExtra(Constant.FRAGMENT_ID, BindPasswordFragment.TAG);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
