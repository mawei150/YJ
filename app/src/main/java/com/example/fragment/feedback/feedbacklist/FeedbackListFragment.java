package com.example.fragment.feedback.feedbacklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.Feedback;
import com.example.bean.Notice;
import com.example.fragment.adapter.FeedbackListAdapter;
import com.example.fragment.feedback.FeedbackActivity;
import com.example.fragment.feedback.feedbackdetail.FeedbackDetailFragment;
import com.example.main.R;
import com.example.util.Constant;
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
 * @date 2019/4/21
 * <p>
 * 描述： 反馈列表
 */

public class FeedbackListFragment extends Fragment {

    public static final String TAG = "FeedbackListFragment";
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;

    private FeedbackListAdapter mAdapter;
    public FeedbackListFragment() {
        // Required empty public constructor
    }


    public static FeedbackListFragment newInstance() {
        FeedbackListFragment fragment = new FeedbackListFragment();
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
        View view = inflater.inflate(R.layout.fragment_feedback_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    //页面展示
    private void initView() {
        mTvIncludeHeaderTitle.setText("反馈列表");
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DisplayList();
                mRefresh.setRefreshing(false);
            }
        });
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mAdapter=new FeedbackListAdapter(R.layout.item_feedback_list,null);
        mRvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Feedback feedback=mAdapter.getItem(position);
                if(feedback ==null){
                    return;
                }
                Intent intent=new Intent(getContext(), FeedbackActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, FeedbackDetailFragment.TAG);
                intent.putExtra(Feedback.class.getCanonicalName(),feedback);
                startActivity(intent);
            }
        });

        DisplayList();
    }


    private void DisplayList() {
        BmobQuery<Feedback> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Feedback>() {
            @Override
            public void done(List<Feedback> object, BmobException e) {
                if (e == null) {
                    mAdapter.setNewData(object);
                } else {
                    ToastUtil.showToast(getContext(), "查询失败");
                }
            }
        });
    }




    @OnClick({R.id.li_includeHeaderLeft})
    protected void onClickView(View view) {
        switch (view.getId()) {
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
