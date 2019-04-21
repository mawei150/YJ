package com.example.fragment.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.note;
import com.example.fragment.adapter.NoteCategoryDiapplayAdapter;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.LongPressPopView;
import com.example.util.advanced.NoteTypeDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author MW
 * @date 2019/3/29
 * <p>
 * 描述：添加笔记
 */

public class TodayNotesFragment extends Fragment implements BaseQuickAdapter.OnItemLongClickListener {

    public static final String TAG = "TodayNotesFragment";

    @BindView(R.id.fb_add)
    FloatingActionButton mFbAdd;
    Unbinder unbinder;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.iv_includeHeaderLeft)
    ImageView mIvIncludeHeaderLeft;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;

    private int mAddNoteType;//1.文字 2.图片  3.视频   4.录音
    private NoteCategoryDiapplayAdapter mAdapter;


    public TodayNotesFragment() {
        // Required empty public constructor
    }

    public static TodayNotesFragment newInstance() {
        TodayNotesFragment fragment = new TodayNotesFragment();
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
        mIvIncludeHeaderLeft.setImageDrawable(getResources().getDrawable(R.drawable.ic_svg_onclick));
        mTvIncludeHeaderTitle.setText("今日笔记");
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DisplayList();
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

        //长按和编辑
        mAdapter.setOnItemLongClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        DisplayList();
    }

    private void DisplayList() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date beginOfDate = cal.getTime();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String time=formatter.format(cal.getTime());
        BmobDate bmobCreatedAtDate = new BmobDate(cal.getTime());////0点0分0点


        BmobQuery<note> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("author", GlobalVariables.getUserObjectId());
        categoryBmobQuery.addWhereGreaterThan("createdAt", bmobCreatedAtDate);
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

    @OnClick({R.id.fb_add, R.id.li_includeHeaderLeft})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.fb_add:
                final NoteTypeDialog noteTypeDialog = new NoteTypeDialog(getContext());
                noteTypeDialog.setOnteTypeClickLister(new NoteTypeDialog.NoteTypeDialogClickLister() {
                    @Override
                    public void ImgOnClickLister() {
                        noteTypeDialog.dismiss();
                        Intent intent = new Intent(getContext(), UserCenterActivity.class);
                        intent.putExtra(Constant.FRAGMENT_ID, AddNoteFragment.TAG);
                        intent.putExtra(UserCenterActivity.NOTE_TYPE, 2);
                        startActivity(intent);
                    }

                    @Override
                    public void WordOnClickLister() {
                        noteTypeDialog.dismiss();
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
            case R.id.li_includeHeaderLeft:
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


    //长按点击事件
    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
        final LongPressPopView pressPopView = new LongPressPopView(getContext());
        pressPopView.setLongPressOnClickLister(new LongPressPopView.LongPressOnClickLister() {
            @Override
            public void cancel() {
                pressPopView.dismiss();
            }

            @Override
            public void Edit() {
                ToastUtil.showToast(getContext(), "我是弹出框啊");
            }

            @Override
            public void Delete() {
                //ToastUtil.showToast(getContext(),"我是删除框啊");
                note category = new note();
                category.delete(mAdapter.getItem(position).getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.showToast(getContext(), "删除成功");
                            DisplayList();
                        } else {
                            ToastUtil.showToast(getContext(), "删除失败" + e.getErrorCode() + e.getMessage());
                        }
                    }
                });
            }
        });
        Animation show = AnimationUtils.loadAnimation(getContext(), R.anim.ppw_select_photo_slide_bottom);
        show.setFillAfter(true);
        pressPopView.getmTvDelete().startAnimation(show);
        pressPopView.getmTvCancel().startAnimation(show);
        pressPopView.getmTvEdit().startAnimation(show);//仅仅是动画
        pressPopView.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        return true;
    }
}
