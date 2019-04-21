package com.example.fragment.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.BeanEventBus;
import com.example.util.advanced.CircleImageView;
import com.example.util.advanced.ImageDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;


/**
 * @author MW
 * @date 2019/3/25
 * <p>
 * 描述： 修改个人信息
 */

public class ModifyUserCenterFragment extends Fragment {

    public static final String TAG = "ModifyUserCenterFragment";

    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_includeHeaderRight)
    TextView mTvIncludeHeaderRight;
    @BindView(R.id.tv_alter_head)
    TextView mTvAlterHead;
    Unbinder unbinder;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.ed_nickname)
    EditText mEdNickname;


    public ModifyUserCenterFragment() {
        // Required empty public constructor
    }


    public static ModifyUserCenterFragment newInstance() {
        ModifyUserCenterFragment fragment = new ModifyUserCenterFragment();
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
        View view = inflater.inflate(R.layout.fragment_modify_user_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    //页面初始化
    private void initView() {
        mLiIncludeHeaderRight.setVisibility(View.VISIBLE);
        mTvIncludeHeaderTitle.setText("修改个人信息");
        mTvIncludeHeaderRight.setText("保存");

        BmobQuery<BeanUserBase> userQuery = new BmobQuery<>();
        userQuery.getObject(GlobalVariables.getUserObjectId(), new QueryListener<BeanUserBase>() {
            @Override
            public void done(BeanUserBase userBase, BmobException e) {
                if (e == null) {
                    if (userBase.getHeadimage() != null) {
                        Picasso.with(getContext()).load(userBase.getHeadimage()).into(mIvHead);
                        if(! TextUtils.isEmpty(userBase.getNickname())){
                            mEdNickname.setText(userBase.getNickname());
                        }else{
                            mEdNickname.setText("暂无昵称");
                        }
                    }
                } else {
                    ToastUtil.showToast(getContext(), "查询失败" + e.getMessage());
                }
            }
        });
    }


    @OnClick({R.id.iv_head,R.id.tv_includeHeaderRight,R.id.li_includeHeaderLeft})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                final ImageDialog imageDialog = new ImageDialog(getContext());
                imageDialog.setmImageDialogClickLister(new ImageDialog.OnImageDialogClickLister() {
                    @Override
                    public void OnCancelLister() {//取消
                        imageDialog.dismiss();
                    }

                    @Override
                    public void OnSelectCameraLister() {//拍照
                        imageDialog.dismiss();
                        PictureSelector.create(ModifyUserCenterFragment.this)
                                .openCamera(PictureMimeType.ofImage())
                                .isCamera(true)
                                .maxSelectNum(1)// 最大图片选择数量 int
                                .enableCrop(true)//是否裁剪
                                .circleDimmedLayer(true)//是否圆形裁剪
                                .compress(true)// 是否压缩 true or false
                                .forResult(PictureConfig.REQUEST_CAMERA);
                    }

                    @Override
                    public void OnSelectPhotoLister() {//获取相册  调用图库
                        imageDialog.dismiss();
                        PictureSelector.create(ModifyUserCenterFragment.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)// 最大图片选择数量 int
                                .enableCrop(true)//是否裁剪
                                .circleDimmedLayer(true)//是否圆形裁剪
                                .compress(true)// 是否压缩 true or false
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                    }
                });
                imageDialog.show();
                break;
            case R.id.tv_includeHeaderRight://保存个人信息
                submitNickName();
                break;
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    //修改个人信息
    private void submitNickName() {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String media;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.REQUEST_CAMERA://相机
                    List<LocalMedia> List = PictureSelector.obtainMultipleResult(data);
                    if (List.get(0).isCompressed()) {//判断是否压缩
                        media = List.get(0).getCompressPath();
                    } else {
                        media = List.get(0).getPath();
                    }
                    displayImage(media);
                    break;
                case PictureConfig.CHOOSE_REQUEST://相册
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.get(0).isCompressed()) {//判断是否压缩
                        media = selectList.get(0).getCompressPath();
                    } else {
                        media = selectList.get(0).getPath();
                    }
                    displayImage(media);
                    break;
                default:
                    break;
            }
        }
    }


    //上传文件
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            final BmobFile bmobFile = new BmobFile(new File(imagePath));
            bmobFile.uploadblock(new UploadFileListener() {
                //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        // ToastUtil.showToast(getContext(), "上传文件成功:" + bmobFile.getFileUrl());
                        //更新个人头像
                        updateImage(bmobFile.getFileUrl());
                    } else {
                        // Log.d("1111111",e.getErrorCode()+e.getMessage()+"11");
                        ToastUtil.showToast(getContext(), ("上传文件失败：" + e.getMessage()));
                    }
                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        } else {
            ToastUtil.showToast(getContext(), "图片路径为空");
        }
    }

    //更新个人头像
    private void updateImage(String fileUrl) {
        final BeanUserBase userBase = new BeanUserBase();

        // userBase.setImage(fileUrl);
        userBase.setHeadimage(fileUrl);
        userBase.update(GlobalVariables.getUserObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Picasso.with(getContext()).load(userBase.getHeadimage()).into(mIvHead);
                    BeanEventBus eventBus = new BeanEventBus(Constant.HEADPORTRAIT);
                    EventBus.getDefault().post(eventBus);
                } else {
                    ToastUtil.showToast(getContext(), ("更新失败：" + e.getErrorCode() + e.getMessage()));
                    Log.d("更新失败：", e.getErrorCode() + e.getMessage());
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
