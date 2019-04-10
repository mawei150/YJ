package com.example.fragment.notice.noticelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.Notice;
import com.example.fragment.adapter.NoticeListAdapter;
import com.example.fragment.adapter.ShowNoteAdapter;
import com.example.fragment.notice.NoticeActivity;
import com.example.fragment.notice.noticedetail.NoticeDetailFragment;
import com.example.fragment.shownote.ShowNoteActivity;
import com.example.fragment.shownote.addshow.AddShowNoteFragment;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.BeanEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * @date 2019/4/10
 * <p>
 * 描述：系统公告
 */

public class NoticeListFragment extends Fragment {

    public static final String TAG = "NoticeListFragment";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_includeHeaderRight)
    TextView mTvIncludeHeaderRight;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;

    private NoticeListAdapter mAdapter;

    public NoticeListFragment() {
        // Required empty public constructor
    }


    public static NoticeListFragment newInstance() {
        NoticeListFragment fragment = new NoticeListFragment();
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
        View view = inflater.inflate(R.layout.fragment_show_note, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        EventBus.getDefault().register(NoticeListFragment.this);
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("系统公告");
        if (GlobalVariables.getRole() == 2) {//管理员  出现添加
            mLiIncludeHeaderRight.setVisibility(View.VISIBLE);
            mTvIncludeHeaderTitle.setText("新增");
        } else {
            mLiIncludeHeaderRight.setVisibility(View.GONE);
        }
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DisplayList();
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter = new NoticeListAdapter(R.layout.item_note_list, null);
        mRvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Notice notice = mAdapter.getItem(position);
                if (notice == null) {
                    return;
                }
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                intent.putExtra(Notice.class.getCanonicalName(), notice);
                intent.putExtra(Constant.FRAGMENT_ID, NoticeDetailFragment.TAG);
                startActivity(intent);
            }
        });
        DisplayList();
    }

    private void DisplayList() {
        BmobQuery<Notice> bmobQuery = new BmobQuery<Notice>();
        bmobQuery.findObjects(new FindListener<Notice>() {
            @Override
            public void done(List<Notice> object, BmobException e) {
                if (e == null) {
                    //注意：这里的Person对象中只有指定列的数据。
                    mAdapter.setNewData(object);
                } else {
                    ToastUtil.showToast(getContext(), "查询失败");
                }
            }
        });


    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.li_includeHeaderRight})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.li_includeHeaderRight://发帖
                /*Intent intent = new Intent(getContext(), ShowNoteActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, AddShowNoteFragment.TAG);
                startActivity(intent);*/

                break;
            default:
                break;
        }
    }

    //处理事件逻辑
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void receiveEventBus(BeanEventBus beanEventBus) {

        if (beanEventBus.getMsg().equals(Constant.NOTICE_ISREAD)) {
            DisplayList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(NoticeListFragment.this);
        unbinder.unbind();
    }
}
