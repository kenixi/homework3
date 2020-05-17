package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PlaceholderFragment extends Fragment implements MyAdapter.ListItemClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        final View view = inflater.inflate(R.layout.fragment_placeholder, container, false);


        //获取recyclerView控件
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.list_view);

        //为recyclerView设置布局管理器
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //为recycler设置数据适配器
        MyAdapter myAdapter = new MyAdapter(30, this);
        mRecyclerView.setAdapter(myAdapter);

        //滑动效果
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // 最后一个完全可见项的位置
            private int lastCompletelyVisibleItemPosition;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (visibleItemCount > 0 && lastCompletelyVisibleItemPosition >= totalItemCount - 1) {
                        Toast.makeText(view.getContext(), "已滑动到底部!,触发loadMore", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.loading_view),
                        "alpha", 1f, 0f);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(getActivity().findViewById(R.id.list_view),
                        "alpha", 0f, 1f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator1, animator2);
                animatorSet.start();
            }
        }, 5000);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(getActivity().getApplicationContext(), "这是item" + Integer.toString(clickedItemIndex), Toast.LENGTH_SHORT).show();
    }

}
