package com.example.fragment.notice.noticedetail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.Notice;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.BeanEventBus;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * @author MW
 * @date 2019/4/10
 * <p>
 * 描述：  公告详情
 */

public class NoticeDetailFragment extends Fragment {

    public static final String TAG = "NoticeDetailFragment";
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    Unbinder unbinder;

    private Notice mNotice;

    public NoticeDetailFragment() {
        // Required empty public constructor
    }


    public static NoticeDetailFragment newInstance(Notice notice) {
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Notice.class.getCanonicalName(), notice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mNotice = (Notice) bundle.getSerializable(Notice.class.getCanonicalName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("公告详情");
        mTvTitle.setText("标题: " + mNotice.getTitle());
        mTvTime.setText("创建时间: " + mNotice.getCreatedAt());
        mTvContent.setText(mNotice.getContent());

        
        if (GlobalVariables.getRole() != 2 && !mNotice.isRead()) {//如果  角色是管理员  且未读   走已读接口
            Notice category = new Notice();
            category.setRead(true);
            category.update(mNotice.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        BeanEventBus eventBus=new BeanEventBus(Constant.NOTICE_ISREAD);
                        EventBus.getDefault().post(eventBus);

                    } else {
                        ToastUtil.showToast(getContext(),"已读失败");
                    }
                }
            });

        }

    }

    @OnClick({R.id.li_includeHeaderLeft})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
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
