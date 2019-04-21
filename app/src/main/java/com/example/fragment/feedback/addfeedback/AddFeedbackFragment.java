package com.example.fragment.feedback.addfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.Feedback;
import com.example.bean.Notice;
import com.example.fragment.adapter.FileDisplayAdapter;
import com.example.fragment.shownote.addshow.AddShowNoteFragment;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.ToastUtil;
import com.example.util.advanced.BeanEventBus;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

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
 * @date 2019/4/21
 * <p>
 * 描述： 添加用户添加意见反馈
 */

public class AddFeedbackFragment extends Fragment {

    public static final String TAG = "AddFeedbackFragment";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.ed_real_name)
    EditText mEdRealName;
    @BindView(R.id.ed_phone)
    EditText mEdPhone;
    @BindView(R.id.et_content)
    EditText mEtContent;
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
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    Unbinder unbinder;

    private List<BmobFile> mFiles = new ArrayList<>();
    private List<String> mPathList = new ArrayList<>();
    private FileDisplayAdapter mAdapter;

    public AddFeedbackFragment() {
        // Required empty public constructor
    }

    public static AddFeedbackFragment newInstance() {
        AddFeedbackFragment fragment = new AddFeedbackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_feedback, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        mTvIncludeHeaderTitle.setText("意见反馈");
        mLlResources.setVisibility(View.VISIBLE);
        mTvImgHint.setVisibility(View.VISIBLE);
        mTvImgHint.setText("最多添加1张图片");

        mRvUpload.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mAdapter = new FileDisplayAdapter(R.layout.item_upload_view, mFiles);
        mRvUpload.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete://删除
                        mAdapter.remove(position);
                        //ToastUtil.showToast(getContext(),position+"");
                        //mFiles.remove(mFiles.get(position));
                        mPathList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.tv_submit, R.id.iv_add})
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.tv_submit://提交
                judgeAddFeedBack();
                break;
            case R.id.iv_add://添加图片
                if (mFiles.size() >= 1) {
                    ToastUtil.showToast(getContext(), "最多添加1张图片");
                } else {
                    PictureSelector.create(AddFeedbackFragment.this)
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
                        ToastUtil.showToast(getContext(), ("上传图片成功"));
                        mFiles = files;
                        mAdapter.setNewData(mFiles);
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    ToastUtil.showToast(getContext(), ("上传图片失败：" + statuscode + errormsg));
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

    private void judgeAddFeedBack() {
        String name = mEdRealName.getText().toString().trim();
        String phone = mEdPhone.getText().toString().trim();
        String content = mEtContent.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast(getContext(), "请输入个人真实姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(getContext(), "手机号码不能为空");
            return;
        }
        if (phone.length() != 11 || !phone.startsWith("1")) {
            ToastUtil.showToast(getContext(), "请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "反馈内容不能为空");
            return;
        }

        if (BmobUser.isLogin()) {
            Feedback feedback=new Feedback();
            feedback.setRealName(name);
            feedback.setPhone(phone);
            feedback.setContent(content);
            feedback.setPicture(mFiles);

            feedback.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        ToastUtil.showToast(getContext(), "添加反馈成功");
                        getActivity().finish();
                    } else {
                        ToastUtil.showToast(getContext(), "添加反馈失败" + e.getErrorCode() + e.getMessage());
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
