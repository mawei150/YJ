package com.example.fragment.shownote.addshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.BeanUserBase;
import com.example.bean.ShowNote;
import com.example.bean.note;
import com.example.fragment.adapter.FileDisplayAdapter;
import com.example.main.R;
import com.example.util.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static android.app.Activity.RESULT_OK;


/**
 * @author MW
 * @date 2019/4/9
 * <p>
 * 描述： 添加个人  分享
 */


public class AddShowNoteFragment extends Fragment {

    public static final String TAG = "AddShowNoteFragment";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;
    @BindView(R.id.tv_limit_words)
    TextView mTvLimitWords;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.rv_upload)
    RecyclerView mRvUpload;
    @BindView(R.id.ll_resources)
    LinearLayout mLlResources;
    @BindView(R.id.tv_img_hint)
    TextView mTvImgHint;
    @BindView(R.id.li_release)
    LinearLayout mLiRelease;
    Unbinder unbinder;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.lock_screen)
    Switch mLockScreen;

    private List<BmobFile> mFiles = new ArrayList<>();
    private List<String> mPathList = new ArrayList<>();
    private boolean isVisable = false;//是否仅自己可见  默认false
    private FileDisplayAdapter mAdapter;

    public AddShowNoteFragment() {
        // Required empty public constructor
    }


    public static AddShowNoteFragment newInstance() {
        AddShowNoteFragment fragment = new AddShowNoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_show_note, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("添加分享");
        mLlResources.setVisibility(View.VISIBLE);
        mTvImgHint.setVisibility(View.VISIBLE);
        mTvImgHint.setText("最多添加1张图片");

        //实时监听输入内容  剩余字数
        mEtContent.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mTvLimitWords.setText("最多还可输入" + (100 - temp.length()) + "个字");
            }
        });

        mRvUpload.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mAdapter = new FileDisplayAdapter(R.layout.item_upload_view, mFiles);
        mRvUpload.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete://删除
                        mAdapter.remove(position);
                        //mFiles.remove(mFiles.get(position));
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        mLockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isVisable = true;
                } else {
                    isVisable = false;
                }
            }
        });
    }

    @OnClick({R.id.li_includeHeaderLeft,R.id.li_release, R.id.iv_add})
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.li_includeHeaderLeft://返回
                 getActivity().onBackPressed();
                break;
            case R.id.li_release://提交
                judgeAddNote();
                break;
            case R.id.iv_add:
                if (mFiles.size() >=1) {
                    ToastUtil.showToast(getContext(), "最多添加1张图片");
                } else {
                    PictureSelector.create(AddShowNoteFragment.this)
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)// 最大图片选择数量 int
                            .compress(true)// 是否压缩 true or false
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST://相册
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    for (LocalMedia localMedia : selectList) {
                        if (localMedia.isCompressed()) {
                            mPathList.add(localMedia.getCompressPath());
                        } else {
                            mPathList.add(localMedia.getPath());
                        }
                    }
                    displayImage(mPathList);
                    break;

                default:
                    break;
            }
        }
    }

    //上传文件
    private void displayImage(final List<String> mPathList) {
        if (mPathList.size() != 0) {
            final String[] characters = mPathList.toArray(new String[mPathList.size()]);
            BmobFile.uploadBatch(characters, new UploadBatchListener() {

                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if (urls.size() == characters.length) {//如果数量相等，则代表文件全部上传完成
                        //do something
                        mProgressBar.setVisibility(View.GONE);
                        mTvState.setVisibility(View.GONE);
                        ToastUtil.showToast(getContext(), ("上传全部文件成功"));
                        mFiles = files;
                        mAdapter.setNewData(mFiles);
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    ToastUtil.showToast(getContext(), ("上传文件失败：" + statuscode + errormsg));
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                    mProgressBar.setVisibility(View.VISIBLE);
                    mTvState.setVisibility(View.VISIBLE);
                }
            });

        } else {
            ToastUtil.showToast(getContext(), "图片路径为空");
        }

    }


    //判断添加笔记
    private void judgeAddNote() {

        String content = mEtContent.getText().toString().trim();//内容
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "内容不能为空");
            return;
        }

        if (BmobUser.isLogin()) {
            ShowNote mShowNote = new ShowNote();
            mShowNote.setContent(content);
            mShowNote.setPicture(mFiles);
            mShowNote.setSelfVisible(isVisable);

            mShowNote.setAuthor(BeanUserBase.getCurrentUser(BeanUserBase.class));
            mShowNote.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtil.showToast(getContext(), "添加成功");
                        getActivity().onBackPressed();
                    } else {
                        ToastUtil.showToast(getContext(), "添加失败" + e.getErrorCode() + e.getMessage());
                    }
                }
            });
        } else {
            ToastUtil.showToast(getContext(), "请先登录账号");
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
