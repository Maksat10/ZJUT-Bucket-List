package com.example.zjut_bucket_list;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

public class BucketListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BucketListAdapter mAdapter;

    private void update_UI() {
        Bucket_ToDo bucketToDo = Bucket_ToDo.get(getActivity());
        List<BucketList> bucketList = bucketToDo.getBucketList();
        bucketList.sort(new sortTaskByDate());
        if(mAdapter == null) {
            mAdapter = new BucketListAdapter(bucketList);
        } else mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class sortTaskByDate implements Comparator<BucketList>
    {
        @Override
        public int compare(BucketList bucketList, BucketList t1) {
            return bucketList.getDate().compareTo(t1.getDate());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        update_UI();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bucket_list, container, false);

        FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.add_item_btn);

        btn.setOnClickListener(v -> {
            Intent addActivityIntent = new Intent(getContext(), AddItemActivity.class);
            startActivity(addActivityIntent);
        });

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.list_appbar);
        toolbar.setTitle("ZJUT BucketList");
        toolbar.inflateMenu(R.menu.menu_holder);
        Menu menu = toolbar.getMenu();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.bucket_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        update_UI();

        return view;
    }

    private class BucketHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private BucketList mBucketList;
        private TextView title;
        private TextView subTitle;
        private CheckBox isDone;

        BucketHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.bucket_list_item, parent, false));

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            subTitle = (TextView) itemView.findViewById(R.id.subtitle);
            isDone = (CheckBox) itemView.findViewById(R.id.is_completed);

            isDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mBucketList.setCompleted(b);
                    if(b){
                        title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        title.setPaintFlags(title.getPaintFlags() & ~ Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });
        }

        public void bind(BucketList bucketList) {
            mBucketList = bucketList;
            title.setText(mBucketList.getName());
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            String date = sm.format(mBucketList.getDate());
            subTitle.setText(date);
            isDone.setChecked(mBucketList.isCompleted());
        }

        @Override
        public void onClick(View view) {
            Intent intent = BucketListPgActivity.newIntent(getContext(), mBucketList.getId());
            startActivity(intent);
        }
    }

    private class BucketListAdapter extends RecyclerView.Adapter<BucketHolder> {
        private List<BucketList> mBucketList;

        public BucketListAdapter(List<BucketList> bucketList) { mBucketList = bucketList; }

        @NonNull
        @Override
        public BucketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new BucketHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BucketHolder holder, int position) {
            BucketList bucketList = mBucketList.get(position);
            holder.bind(bucketList);
        }

        @Override
        public int getItemCount() {
            return mBucketList.size();
        }
    }
}
