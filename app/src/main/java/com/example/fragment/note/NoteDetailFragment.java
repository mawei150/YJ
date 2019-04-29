package com.example.fragment.note;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.application.BaseApplication;
import com.example.bean.note;
import com.example.fragment.adapter.PictureDisplayAdapter;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.main.R;
import com.example.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author MW
 * @date 2019/4/4
 * <p>
 * 描述： 各种笔记详情
 */


public class NoteDetailFragment extends Fragment {

    public static final String TAG = "NoteDetailFragment";


    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_founder)
    TextView mTvFounder;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    Unbinder unbinder;
    @BindView(R.id.video_view)
    VideoView mVideoView;
    @BindView(R.id.iv_video)
    ImageView mIvVideo;
    @BindView(R.id.iv_video_play)
    ImageView mIvVideoPlay;
    @BindView(R.id.ed_title)
    EditText mEdTitle;
    @BindView(R.id.ed_content)
    EditText mEdContent;
    @BindView(R.id.tv_includeHeaderRight)
    TextView mTvIncludeHeaderRight;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;


    private note mNote;//笔记类
    private PictureDisplayAdapter mAdapter;
    private int mOperationType;//1.查看  2.修改

    public NoteDetailFragment() {
        // Required empty public constructor
    }


    public static NoteDetailFragment newInstance(note mnote, int operationType) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(note.class.getCanonicalName(), mnote);
        args.putInt(UserCenterActivity.OPERATION_TYPE, operationType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mNote = (note) bundle.getSerializable(note.class.getCanonicalName());
            mOperationType = bundle.getInt(UserCenterActivity.OPERATION_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        if (mOperationType == 2) {//修改操作
            mTvIncludeHeaderTitle.setText("修改笔记");
            mLiIncludeHeaderRight.setVisibility(View.VISIBLE);
            mTvIncludeHeaderRight.setVisibility(View.VISIBLE);
            mTvIncludeHeaderRight.setText("保存");
            mTvTitle.setVisibility(View.GONE);
            mTvContent.setVisibility(View.GONE);
            mEdContent.setVisibility(View.VISIBLE);
            mEdTitle.setVisibility(View.VISIBLE);
            mEdTitle.setText(mNote.getNoteTitle());
            if (TextUtils.isEmpty(mNote.getNoteWords())) {//内容为空
                mEdContent.setVisibility(View.GONE);
            } else {
                mEdContent.setVisibility(View.VISIBLE);
                mEdContent.setText(mNote.getNoteWords());
            }
        } else {//查看操作
            mTvIncludeHeaderTitle.setText("笔记详情");
            mEdContent.setVisibility(View.GONE);
            mEdTitle.setVisibility(View.GONE);
            mTvTitle.setVisibility(View.VISIBLE);
            mTvContent.setVisibility(View.VISIBLE);
            mTvTitle.setText("标题: " + mNote.getNoteTitle());
            if (TextUtils.isEmpty(mNote.getNoteWords())) {//内容为空
                mTvContent.setVisibility(View.GONE);
            } else {
                mTvContent.setVisibility(View.VISIBLE);
                mTvContent.setText(mNote.getNoteWords());
            }
        }
        mTvTime.setText("创建时间: " + mNote.getCreatedAt());
        switch (mNote.getNoteType()) {
            case 1:
                mTvType.setText("文字笔记");
                break;
            case 2:
                mTvType.setText("图片笔记");
                if (mNote.getNotePicture() != null) {
                    // List<BmobFile> files= (List<BmobFile>) mNote.getNotePicture();
                    mRvList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                    mAdapter = new PictureDisplayAdapter(R.layout.item_picture, mNote.getNotePicture());
                    mRvList.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            //PictureSelector.create(NoteDetailFragment.this).themeStyle(1).openExternalPreview(position, (List<LocalMedia>) mAdapter.getItem(position));
                            ImageView photoView = view.findViewById(R.id.iv_picture);
                            PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

                            Glide.with(BaseApplication.getContext()).load(mAdapter.getItem(position).getUrl()).apply(new RequestOptions().override(800, 800))
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            photoView.setImageDrawable(resource);
                                            attacher.update();
                                        }
                                    });
                        }
                    });
                }
                break;
            case 3:
                mTvType.setText("视频笔记");
                if (mNote.getNotePicture() != null) {
                    // List<BmobFile> files= (List<BmobFile>) mNote.getNotePicture();
                    /*mRvList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                    mAdapter = new PictureDisplayAdapter(R.layout.item_picture, mNote.getNotePicture());
                    mRvList.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            PictureSelector.create(NoteDetailFragment.this).themeStyle(1).openExternalPreview(position, (List<LocalMedia>) mAdapter.getItem(position));
                        }
                    });*/
                    mIvVideo.setVisibility(View.VISIBLE);
                    mIvVideoPlay.setVisibility(View.VISIBLE);
                    Glide.with(BaseApplication.getContext())
                            .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.default_coursecover)
                                    .placeholder(R.drawable.default_coursecover))
                            .setDefaultRequestOptions(new RequestOptions().frame(4000))//选择第四秒
                            .load(mNote.getNotePicture().get(0).getUrl()).into(mIvVideo);

                    mIvVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mIvVideo.setVisibility(View.GONE);
                            mIvVideoPlay.setVisibility(View.GONE);
                            mVideoView.setVisibility(View.VISIBLE);
                            mVideoView.setVideoPath(mNote.getNotePicture().get(0).getUrl());
                            MediaController mediaController = new MediaController(getContext());
                            //VideoView与MediaController建立关联
                            mVideoView.setMediaController(mediaController);
                            //让VideoView获取焦点
                            mVideoView.requestFocus();
                            mVideoView.start();
                        }
                    });
                }
                break;
            case 4:
                mTvType.setText("音频笔记");
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.li_includeHeaderLeft, R.id.li_includeHeaderRight})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.li_includeHeaderRight://保存按钮
                saveNote();
            default:
                break;
        }
    }

    //保存修改的笔记信息
    private void saveNote() {
        String title = mEdTitle.getText().toString().trim();
        String content = mEdContent.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            ToastUtil.showToast(getContext(), "标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(content) && mNote.getNoteType() == 1) {
            ToastUtil.showToast(getContext(), "笔记内容不能为空");
            return;
        }

        note mMNote = new note();
        mMNote.setNoteTitle(title);
        mMNote.setNoteWords(content);
        mMNote.update(mNote.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //Snackbar.make(mBtnUpdate, "更新成功", Snackbar.LENGTH_LONG).show();
                    ToastUtil.showToast(getContext(), "修改成功");
                    getActivity().finish();
                } else {
                    ToastUtil.showToast(getContext(), "修改失败" + e.getErrorCode() + e.getMessage());
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
