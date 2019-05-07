package com.example.fragment.shownote.reply;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.BeanUserBase;
import com.example.bean.ShowNote;
import com.example.bean.ShowNoteReply;
import com.example.fragment.adapter.AddShowReplyAdapter;
import com.example.main.R;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.CircleImageView;
import com.luck.picture.lib.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Comment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * @author MW
 * @date 2019/4/12
 * <p>
 * 描述：  添加评论回复  回复的格式只能是文本
 */


public class AddShowNoteReplyFragment extends Fragment {
    public static final String TAG = "AddShowNoteReplyFragment";


    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.rl_includeHeader)
    RelativeLayout mRlIncludeHeader;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.iv_send)
    TextView mIvSend;
    @BindView(R.id.fl_comment)
    FrameLayout mFlComment;
    Unbinder unbinder;


    private ShowNote mShowNote;
    private AddShowReplyAdapter mAdapter;

    public AddShowNoteReplyFragment() {
        // Required empty public constructor
    }

    public static AddShowNoteReplyFragment newInstance(ShowNote mShowNote) {
        AddShowNoteReplyFragment fragment = new AddShowNoteReplyFragment();
        Bundle args = new Bundle();
        args.putSerializable(ShowNote.class.getCanonicalName(), mShowNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShowNote = (ShowNote) bundle.getSerializable(ShowNote.class.getCanonicalName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_show_note_reply, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    //初始化  页面
    private void initView() {
        mTvIncludeHeaderTitle.setText("帖子回复");
        mRefresh.setColorSchemeColors(getResources().getColor(R.color.mainColor));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DisplayList();
                mRefresh.setRefreshing(false);
            }
        });
        mAdapter = new AddShowReplyAdapter(R.layout.item_show_content, null);
        mRvList.setAdapter(mAdapter);

        //这里面  少一个删除  一对多关系
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_trash://删除按钮
                       /* ShowNote category = new ShowNote();
                        category.delete(Objects.requireNonNull(mAdapter.getItem(position)).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.showToast(getContext(), "删除成功");
                                    DisplayList();
                                } else {
                                    ToastUtil.showToast(getContext(), "删除失败" + e.getErrorCode() + e.getMessage());
                                }
                            }
                        });*/
                        ShowNoteReply reply = new  ShowNoteReply();
                        reply.setObjectId(Objects.requireNonNull(mAdapter.getItem(position)).getObjectId());
                        //删除一对一关联，解除帖子和用户的关系
                        reply.remove("author");
                        reply.remove("post");
                        reply.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.showToast(getContext(), "删除评论成功");
                                    DisplayList();
                                } else {
                                    ToastUtil.showToast(getContext(), "删除评论失败");
                                }
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });



        if (mAdapter.getHeaderLayoutCount() == 0) {//mAdapter添加头
            View headView = LayoutInflater.from(getContext()).inflate(R.layout.header_add_show_note_reply, mRvList, false);
            ViewHolder viewHolder = new ViewHolder(headView);
            viewHolder.mTvDisplayName.setText(mShowNote.getAuthor().getNickname());//发帖者用户昵称
            viewHolder.mTvContent.setText(mShowNote.getContent());
            viewHolder.mTvDateCrate.setText(mShowNote.getCreatedAt());
            if (TextUtils.isEmpty(mShowNote.getAuthor().getHeadimage())) {//用户头像
                Picasso.with(getContext()).load(R.mipmap.ic_head1)
                        .into(viewHolder.mIvProfile);
            } else {
                Picasso.with(getContext()).load(mShowNote.getAuthor().getHeadimage())
                        .into(viewHolder.mIvProfile);
            }
            //分享的图片
            if (mShowNote.getPicture().size() != 0) {
                viewHolder.mLlFile.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(mShowNote.getPicture().get(0).getUrl()).into(viewHolder.mLlFile);
            } else {
                viewHolder.mLlFile.setVisibility(View.GONE);
            }
            mAdapter.addHeaderView(headView);
        }
        DisplayList();
    }

    //列表展示
    private void DisplayList() {
        BeanUserBase user = BeanUserBase.getCurrentUser(BeanUserBase.class);
        BmobQuery<ShowNoteReply> query = new BmobQuery<ShowNoteReply>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        ShowNote post = new ShowNote();
        post.setObjectId(mShowNote.getObjectId());
        query.addWhereEqualTo("post", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(new FindListener<ShowNoteReply>() {
            @Override
            public void done(List<ShowNoteReply> objects, BmobException e) {
                mAdapter.setNewData(objects);
            }
        });
    }


    @OnClick({R.id.li_includeHeaderLeft, R.id.iv_send})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.iv_send://发送
                Send();
                break;
            default:
                break;
        }
    }

    //发送消息
    private void Send() {
        if(GlobalVariables.getRole() ==1 && GlobalVariables.getUserPower()==3){
            ToastUtil.showToast(getContext(),"您已被禁止评论，请联系管理员解禁");
            return;
        }
        String content = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "发送信息不能为空");
            return;
        }
        BeanUserBase user = BmobUser.getCurrentUser(BeanUserBase.class);
        ShowNote post = new ShowNote();
        post.setObjectId(mShowNote.getObjectId());
        final ShowNoteReply reply = new ShowNoteReply();
        reply.setContent(content);
        reply.setPost(post);
        reply.setAuthor(user);
        reply.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(getContext(), "评论发表成功");
                    mEtContent.setText("");
                    DisplayList();
                } else {
                    ToastUtil.showToast(getContext(), "评论发表失败");
                }
            }

        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_profile)
        CircleImageView mIvProfile;
        @BindView(R.id.tv_displayName)
        TextView mTvDisplayName;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.ll_file)
        ImageView mLlFile;
        @BindView(R.id.tv_dateCrate)
        TextView mTvDateCrate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
