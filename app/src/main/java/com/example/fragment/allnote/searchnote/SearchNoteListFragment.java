package com.example.fragment.allnote.searchnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.note;
import com.example.fragment.adapter.NoteCategoryDiapplayAdapter;
import com.example.fragment.note.NoteDetailFragment;
import com.example.fragment.usercenter.UserCenterActivity;
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
 * @date 2019/4/22
 * <p>
 * 描述： 查找笔记
 */

public class SearchNoteListFragment extends Fragment {

    public static final String TAG = "SearchNoteListFragment";
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.ed_content)
    EditText mEdContent;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;

    private NoteCategoryDiapplayAdapter mAdapter;

    public SearchNoteListFragment() {
        // Required empty public constructor
    }

    public static SearchNoteListFragment newInstance() {
        SearchNoteListFragment fragment = new SearchNoteListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_note_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    //视图展示
    private void initView() {
        mTvIncludeHeaderTitle.setText("查找笔记");
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter = new NoteCategoryDiapplayAdapter(R.layout.item_user_note, null);
        mRvList.setAdapter(mAdapter);
        //点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                note mNote = mAdapter.getItem(position);
                if (mNote == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(getContext(), UserCenterActivity.class);//换一种写法
                intent.putExtra(note.class.getCanonicalName(), mNote);
                intent.putExtra(Constant.FRAGMENT_ID, NoteDetailFragment.TAG);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.iv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.iv_search://搜索按钮
                searchNote();//查找笔记
                break;
            default:
                break;
        }
    }

    //查找笔记的条件
    private void searchNote() {
        String content = mEdContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "查询内容不能为空");
            return;
        }

        BmobQuery<note> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("author", GlobalVariables.getUserObjectId());
        categoryBmobQuery.addWhereEqualTo("noteTitle", content);
        categoryBmobQuery.findObjects(new FindListener<note>() {
            @Override
            public void done(List<note> object, BmobException e) {
                if (e == null) {
                    //ToastUtil.showToast(getContext(), "查询数量" + object.size());
                    if (object.size()==0) {
                        ToastUtil.showToast(getContext(), "没有该名称的笔记");
                    } else {
                        mAdapter.setNewData(object);
                    }
                } else {
                    Log.e("BMOB", e.toString());
                    ToastUtil.showToast(getContext(), "失败" + e.getErrorCode() + e.getMessage());
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
