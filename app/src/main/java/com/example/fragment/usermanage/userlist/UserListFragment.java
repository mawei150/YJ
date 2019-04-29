package com.example.fragment.usermanage.userlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.example.bean.BeanUserBase;
import com.example.fragment.adapter.NoticeListAdapter;
import com.example.fragment.adapter.UserListAdapter;
import com.example.main.R;
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
 * @date 2019/4/25
 * <p>
 * 描述： 用户列表展示
 */

public class UserListFragment extends Fragment {

    public static final String TAG = "UserListFragment";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;

    private UserListAdapter mAdapter;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("用户管理");

        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DisplayList();
                mRefresh.setRefreshing(false);
            }
        });
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mAdapter = new UserListAdapter(R.layout.item_user_list, null);
        mRvList.setAdapter(mAdapter);

        DisplayList();
    }


    private void DisplayList() {

        BmobQuery<BeanUserBase> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<BeanUserBase>() {
            @Override
            public void done(List<BeanUserBase> object, BmobException e) {
                if (e == null) {
                    mAdapter.setNewData(object);
                } else {
                    ToastUtil.showToast(getContext(),"查询失败");
                }
            }
        });
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
