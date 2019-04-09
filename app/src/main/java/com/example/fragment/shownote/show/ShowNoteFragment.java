package com.example.fragment.shownote.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.ShowNote;
import com.example.fragment.adapter.ShowNoteAdapter;
import com.example.fragment.shownote.ShowNoteActivity;
import com.example.fragment.shownote.addshow.AddShowNoteFragment;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;

import java.util.ArrayList;
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
 * @date 2019/4/9
 * <p>
 * 描述： 社区详情
 */

public class ShowNoteFragment extends Fragment {

    public static final String TAG = "ShowNoteFragment";

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_includeHeaderRight)
    TextView mTvIncludeHeaderRight;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;
    Unbinder unbinder;

    private ShowNoteAdapter mAdapter;

    public ShowNoteFragment() {
        // Required empty public constructor
    }


    public static ShowNoteFragment newInstance() {
        ShowNoteFragment fragment = new ShowNoteFragment();
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
        //R.layout.fragment_show_note
        View view = inflater.inflate(R.layout.fragment_show_note, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        mTvIncludeHeaderTitle.setText("社区");
        mLiIncludeHeaderRight.setVisibility(View.VISIBLE);
        mTvIncludeHeaderRight.setText("发帖");
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter = new ShowNoteAdapter(R.layout.item_show_content, null);
        mRvList.setAdapter(mAdapter);

        DisplayList();
    }


    private void DisplayList() {

        //BmobQuery<ShowNote> categoryBmobQuery = new BmobQuery<>();
        //categoryBmobQuery.addWhereEqualTo("author", GlobalVariables.getUserObjectId());
        //逻辑是  我的 or 不是仅自己可见  都可以见到
        BmobQuery<ShowNote> mShowNote1 = new BmobQuery<ShowNote>();
        mShowNote1.addWhereNotEqualTo("isSelfVisible", false);//不是仅自己可见
        BmobQuery<ShowNote> mShowNote2 = new BmobQuery<ShowNote>();
        mShowNote2.addWhereEqualTo("author", GlobalVariables.getUserObjectId());//是我的
        List<BmobQuery<ShowNote>> queries = new ArrayList<BmobQuery<ShowNote>>();
        queries.add(mShowNote1);
        queries.add(mShowNote2);
        BmobQuery<ShowNote> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.or(queries);
        categoryBmobQuery.include("user,post.author");
        categoryBmobQuery.findObjects(new FindListener<ShowNote>() {
            @Override
            public void done(List<ShowNote> object, BmobException e) {
                if (e == null) {
                    //ToastUtil.showToast(getContext(), "查询数量" + object.size());
                    mAdapter.setNewData(object);

                } else {
                    Log.e("BMOB", e.toString());
                    ToastUtil.showToast(getContext(), "失败" + e.getErrorCode() + e.getMessage());
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
                Intent intent = new Intent(getContext(), ShowNoteActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, AddShowNoteFragment.TAG);
                startActivity(intent);
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
