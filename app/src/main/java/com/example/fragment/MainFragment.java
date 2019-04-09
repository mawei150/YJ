package com.example.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bean.BeanUserBase;
import com.example.fragment.allnote.AllNoteActivity;
import com.example.fragment.note.TodayNotesFragment;
import com.example.fragment.shownote.ShowNoteActivity;
import com.example.fragment.shownote.show.ShowNoteFragment;
import com.example.fragment.usercenter.ModifyUserCenterFragment;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.BeanEventBus;
import com.example.util.advanced.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author MW
 * @date 2019/3/22
 * <p>
 * 描述：左滑显示的页面
 */

public class MainFragment extends Fragment {


    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    Unbinder unbinder;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.ll_take_notes)
    LinearLayout mLlTakeNotes;
    @BindView(R.id.ll_all_note)
    LinearLayout mLlAllNote;
    @BindView(R.id.ll_de_gemeenschap)
    LinearLayout mLlDeGemeenschap;


    private DrawerLayout drawer_layout;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        //注册EventBus
        EventBus.getDefault().register(MainFragment.this);
        initView();
        return view;
    }


    private void initView() {
        showPersonalInformation();
    }

    private String getImagePath(Uri uri, String content) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, content, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    @OnClick({R.id.iv_head, R.id.cl_personal_data, R.id.ll_take_notes,R.id.ll_all_note,R.id.ll_de_gemeenschap})
    public void onClickView(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_head:
            case R.id.cl_personal_data://更改个人信息
                intent = new Intent(getContext(), UserCenterActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, ModifyUserCenterFragment.TAG);
                startActivity(intent);
                break;
            case R.id.ll_take_notes://写笔记
                intent = new Intent(getContext(), UserCenterActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, TodayNotesFragment.TAG);
                startActivity(intent);
                break;
            case R.id.ll_all_note://所有笔记
                intent = new Intent(getContext(), AllNoteActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_de_gemeenschap://社区
                 intent=new Intent(getContext(), ShowNoteActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, ShowNoteFragment.TAG);
                 startActivity(intent);
            default:
                break;
        }
    }


    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.drawer_layout = drawer_layout;
    }


    public void showPersonalInformation() {
        BmobQuery<BeanUserBase> userQuery = new BmobQuery<>();
        userQuery.getObject(GlobalVariables.getUserObjectId(), new QueryListener<BeanUserBase>() {
            @Override
            public void done(BeanUserBase userBase, BmobException e) {
                if (e == null) {
                    mTvNickName.setText(GlobalVariables.getUsername());
                    if (userBase.getHeadimage() != null) {
                        /*File mFile = userBase.getImgage();
                        Bitmap bitmap = BitmapFactory.decodeFile(mFile.toString());//将文件路径解码为位图
                        mIvHead.setImageBitmap(bitmap);*/
                        Picasso.with(getContext()).load(userBase.getHeadimage()).into(mIvHead);
                    }
                } else {
                    ToastUtil.showToast(getContext(), "查询失败" + e.getMessage());
                }
            }
        });
    }


    //处理事件逻辑
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void receiveEventBus(BeanEventBus beanEventBus) {

        if (beanEventBus.getMsg().equals(Constant.HEADPORTRAIT)) {
            showPersonalInformation();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(MainFragment.this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
