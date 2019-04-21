package com.example.fragment.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.bean.note;
import com.example.fragment.adapter.PictureDisplayAdapter;
import com.example.main.MainActivity;
import com.example.main.R;
import com.example.util.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.datatype.BmobFile;

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


    private note mNote;//笔记类
    private PictureDisplayAdapter mAdapter;


    public NoteDetailFragment() {
        // Required empty public constructor
    }


    public static NoteDetailFragment newInstance(note mnote) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(note.class.getCanonicalName(), mnote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mNote = (note) bundle.getSerializable(note.class.getCanonicalName());
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

        mTvIncludeHeaderTitle.setText("笔记详情");
        mTvTitle.setText("标题: " + mNote.getNoteTitle());
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
                            PictureSelector.create(NoteDetailFragment.this).themeStyle(1).openExternalPreview(position, (List<LocalMedia>) mAdapter.getItem(position));

                        }
                    });
                }
                break;
            case 3:
                mTvType.setText("视频笔记");
                if (mNote.getNotePicture() != null) {
                    // List<BmobFile> files= (List<BmobFile>) mNote.getNotePicture();
                    mRvList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                    mAdapter = new PictureDisplayAdapter(R.layout.item_picture, mNote.getNotePicture());
                    mRvList.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            PictureSelector.create(NoteDetailFragment.this).themeStyle(1).openExternalPreview(position, (List<LocalMedia>) mAdapter.getItem(position));
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

        if (TextUtils.isEmpty(mNote.getNoteWords())) {//内容为空
            mTvContent.setVisibility(View.GONE);
        } else {
            mTvContent.setVisibility(View.VISIBLE);
            mTvContent.setText(mNote.getNoteWords());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
