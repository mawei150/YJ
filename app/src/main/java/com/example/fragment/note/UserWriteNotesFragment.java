package com.example.fragment.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bean.note;
import com.example.fragment.adapter.NoteCategoryDiapplayAdapter;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.NoteTypeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author MW
 * @date 2019/3/29
 * <p>
 * 描述：添加笔记
 */

public class UserWriteNotesFragment extends Fragment {

    public static final String TAG = "UserWriteNotesFragment";

    @BindView(R.id.fb_add)
    FloatingActionButton mFbAdd;
    Unbinder unbinder;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;

    private int mAddNoteType;//1.文字 2.图片  3.视频   4.录音
    private NoteCategoryDiapplayAdapter  mAdapter;


    public UserWriteNotesFragment() {
        // Required empty public constructor
    }

    public static UserWriteNotesFragment newInstance() {
        UserWriteNotesFragment fragment = new UserWriteNotesFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_write_notes, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("今日笔记");
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtil.showToast(getContext(), "我是上拉刷新");
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter=new NoteCategoryDiapplayAdapter(R.layout.item_user_note,null);
        mRvList.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");

        String curDate = s.format(c.getTime());  //当前日期
        BmobQuery<note> eq1 = new BmobQuery<note>();

        eq1.addWhereLessThanOrEqualTo("updatedAt", "yyyy-MM-dd 00:00:00");//



//--and条件2
        BmobQuery<note> eq2 = new BmobQuery<note>();
        eq2.addWhereGreaterThanOrEqualTo("author", GlobalVariables.getUserObjectId());//


        List<BmobQuery<note>> andQuerys = new ArrayList<BmobQuery<note>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);

//查询符合整个and条件的人
        BmobQuery<note> query = new BmobQuery<note>();
        query.and(andQuerys);
        query.findObjects(new FindListener<note>() {
            @Override
            public void done(List<note> object, BmobException e) {
                if (e == null) {
                    //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                    ToastUtil.showToast(getContext(), "查询数量" + object.size());

                    mAdapter.setNewData(object);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    @OnClick({R.id.fb_add})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.fb_add:
                final NoteTypeDialog noteTypeDialog = new NoteTypeDialog(getContext());
                noteTypeDialog.setOnteTypeClickLister(new NoteTypeDialog.NoteTypeDialogClickLister() {
                    @Override
                    public void ImgOnClickLister() {
                        //ToastUtil.showToast(getContext(),"我是图片按钮");
                        noteTypeDialog.dismiss();
                        //ToastUtil.showToast(getContext(),"我是文字按钮");
                        Intent intent = new Intent(getContext(), UserCenterActivity.class);
                        intent.putExtra(Constant.FRAGMENT_ID, AddNoteFragment.TAG);
                        intent.putExtra(UserCenterActivity.NOTE_TYPE, 2);
                        startActivity(intent);
                    }

                    @Override
                    public void WordOnClickLister() {
                        noteTypeDialog.dismiss();
                        //ToastUtil.showToast(getContext(),"我是文字按钮");
                        Intent intent = new Intent(getContext(), UserCenterActivity.class);
                        intent.putExtra(Constant.FRAGMENT_ID, AddNoteFragment.TAG);
                        intent.putExtra(UserCenterActivity.NOTE_TYPE, 1);
                        startActivity(intent);
                    }

                    @Override
                    public void VideoOnClickLister() {
                        // ToastUtil.showToast(getContext(),"我是视频按钮");
                        noteTypeDialog.dismiss();
                        Intent intent = new Intent(getContext(), UserCenterActivity.class);
                        intent.putExtra(Constant.FRAGMENT_ID, AddNoteFragment.TAG);
                        intent.putExtra(UserCenterActivity.NOTE_TYPE, 3);
                        startActivity(intent);

                    }

                    @Override
                    public void SoundRecordOnClickLister() {
                        ToastUtil.showToast(getContext(), "我是录音按钮");
                    }

                    @Override
                    public void CancelOnClickLister() {
                        noteTypeDialog.dismiss();
                        ToastUtil.showToast(getContext(), "我是取消按钮");
                    }
                });
                noteTypeDialog.show();
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
