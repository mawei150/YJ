package com.example.fragment.notice.addnotice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author MW
 * @date 2019/4/10
 * <p>
 * 描述：  添加系统公告
 */

public class AddNoticeFragment extends Fragment {


    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.ed_title)
    EditText mEdTitle;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    Unbinder unbinder;

    public AddNoticeFragment() {
        // Required empty public constructor
    }

    public static AddNoticeFragment newInstance() {
        AddNoticeFragment fragment = new AddNoticeFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_notice, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
           mTvIncludeHeaderTitle.setText("添加公告");

    }



    @OnClick({R.id.li_includeHeaderLeft,R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.tv_submit://提交
                submit();
                break;
            default:
                break;
        }
    }

    //提交公告
    private void submit() {
        String title=mEdTitle.getText().toString().trim();//标题
        String content=mEtContent.getText().toString().trim();//内容

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
