package com.example.zjut_bucket_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EditItemActivity extends Fragment {
    private EditText mName;
    private EditText mDescription;
    private EditText mLongitude;
    private EditText mLatitude;
    private DatePicker mDate;
    private Button mButton;
    private Button mDelete;
    private BucketList mBucketList;
    private TextView mError;
    private UUID id;

    public static EditItemActivity newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable("ITEM_ID", id);

        EditItemActivity fragment = new EditItemActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable("ITEM_ID");
        mBucketList = Bucket_ToDo.get(getActivity()).getBucket(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_info, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.edit_appbar);
        toolbar.setTitle("Edit Item");
        toolbar.setNavigationIcon(R.drawable.ic_back_nav);
        toolbar.setNavigationOnClickListener(vw -> {
            getActivity().finish();
        });
        toolbar.inflateMenu(R.menu.menu_holder);
        Menu menu = toolbar.getMenu();

        mName = (EditText) v.findViewById(R.id.edit_name);
        mDescription = (EditText) v.findViewById(R.id.edit_description);
        mDate = (DatePicker) v.findViewById(R.id.edit_date);
        mLatitude = (EditText) v.findViewById(R.id.edit_latitude);
        mLongitude = (EditText) v.findViewById(R.id.edit_longitude);
        mButton = (Button) v.findViewById(R.id.save_changes);
        mDelete = (Button) v.findViewById(R.id.delete_item);
        mError = (TextView) v.findViewById(R.id.edit_error_box);

        mName.setText(mBucketList.getName());
        mDescription.setText(mBucketList.getDescription());
        mLatitude.setText(Double.toString(mBucketList.getLatitude()));
        mLongitude.setText(Double.toString(mBucketList.getLongitude()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mBucketList.getDate());

        mDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            mDate.setMinDate(calendar.getTimeInMillis() - 1000);
        } else {
            mDate.setMinDate(System.currentTimeMillis() - 1000);
        }

        mButton.setOnClickListener(view->{
            BucketList editedBucketList = new BucketList(mName.getText().toString(), mDescription.getText().toString(), Double.parseDouble(mLongitude.getText().toString()), Double.parseDouble(mLatitude.getText().toString()), getDateFromDatePicker(mDate));
            if(
                    !mName.getText().toString().matches("")
                            && !mDescription.getText().toString().matches("")
                            && !mLatitude.getText().toString().matches("")
                            && !mLongitude.getText().toString().matches("")) {

                mBucketList.updateBucketList(editedBucketList);
                Intent data = new Intent();
                getActivity().setResult(getActivity().RESULT_OK, data);
                getActivity().finish();
            } else {
                mError.setText("Fill in all spaces");
            }
        });

        mDelete.setOnClickListener(view->{
            Bucket_ToDo.get(getActivity()).deleteItem(id);
            Intent data = new Intent();
            getActivity().setResult(getActivity().RESULT_OK, data);
            getActivity().finish();
        });

        return v;
    }

    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
