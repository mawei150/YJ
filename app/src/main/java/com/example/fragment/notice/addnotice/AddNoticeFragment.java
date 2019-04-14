package com.example.fragment.notice.addnotice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.bean.Notice;
import com.example.bean.ShowNote;
import com.example.bean.UserNotice;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.ToastUtil;
import com.example.util.advanced.BeanEventBus;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author MW
 * @date 2019/4/10
 * <p>
 * 描述：  添加系统公告
 */

public class AddNoticeFragment extends Fragment {

     public static final String TAG="AddNoticeFragment";

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


    @OnClick({R.id.li_includeHeaderLeft, R.id.tv_submit})
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
        String title = mEdTitle.getText().toString().trim();//标题
        String content = mEtContent.getText().toString().trim();//内容

        if (TextUtils.isEmpty(title)) {
            ToastUtil.showToast(getContext(), "标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "内容不能为空");
            return;
        }
        if (BmobUser.isLogin()) {
            Notice mNotice = new Notice();
            mNotice.setContent(content);
            mNotice.setTitle(title);
            //mNotice.setAuthor(UserNotice.class);

            mNotice.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtil.showToast(getContext(), "添加成功");
                        BeanEventBus eventBus = new BeanEventBus(Constant.NOTICE_ISREAD);
                        EventBus.getDefault().post(eventBus);
                        getActivity().onBackPressed();
                    } else {
                        ToastUtil.showToast(getContext(), "添加失败" + e.getErrorCode() + e.getMessage());
                    }
                }
            });
        } else {
            ToastUtil.showToast(getContext(), "请先登录账号");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
