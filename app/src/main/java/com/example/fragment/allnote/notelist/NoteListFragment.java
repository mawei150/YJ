package com.example.fragment.allnote.notelist;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.BeanUserBase;
import com.example.bean.note;
import com.example.fragment.adapter.AllNoteListAdaoter;
import com.example.fragment.adapter.NoteCategoryDiapplayAdapter;
import com.example.fragment.allnote.AllNoteActivity;
import com.example.fragment.note.NoteDetailFragment;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author MW
 * @date 2019/4/8
 * <p>
 * 描述：笔记种类描述
 */


public class NoteListFragment extends Fragment {


    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    Unbinder unbinder;

    private AllNoteListAdaoter mAdapter;
    private int type;//1:文字笔记  2.图片笔记  3.视频笔记

    public NoteListFragment() {
        // Required empty public constructor
    }


    public static NoteListFragment newInstance(int type) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        args.putInt(AllNoteActivity.ALL_NOTE_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(AllNoteActivity.ALL_NOTE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    //根据type的值  分别查 对应的笔记
    private void initView() {
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 DisplayList();
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter = new AllNoteListAdaoter(R.layout.item_note_list, null);
        mRvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                note mNote=mAdapter.getItem(position);
                if(mNote==null){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(getContext(), UserCenterActivity.class);//换一种写法
                intent.putExtra(note.class.getCanonicalName(),mNote);
                intent.putExtra(Constant.FRAGMENT_ID, NoteDetailFragment.TAG);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayList();
    }

    private void DisplayList() {

        BmobQuery<note> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("author", GlobalVariables.getUserObjectId());
        categoryBmobQuery.addWhereEqualTo("noteType",type);
        categoryBmobQuery.findObjects(new FindListener<note>() {
            @Override
            public void done(List<note> object, BmobException e) {
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




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
