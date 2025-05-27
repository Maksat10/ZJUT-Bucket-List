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
import java.util.List;

public class AddItemFragment extends Fragment {
    private EditText mName;
    private EditText mDescription;
    private EditText mLongitude;
    private EditText mLatitude;
    private DatePicker mDate;
    private Button mButton;
    private TextView mError;
    private List<BucketList> mBucketList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bucket_ToDo bucketToDo = Bucket_ToDo.get(getActivity());
        mBucketList = bucketToDo.getBucketList();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_item, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.add_item_appbar);
        toolbar.setTitle("Add Item");
        toolbar.setNavigationIcon(R.drawable.ic_back_nav);
        toolbar.setNavigationOnClickListener(vw -> {
            getActivity().finish();
        });
        toolbar.inflateMenu(R.menu.menu_holder);
        Menu menu = toolbar.getMenu();
        mName = (EditText) v.findViewById(R.id.add_name);
        mDescription = (EditText) v.findViewById(R.id.add_description);
        mDate = (DatePicker) v.findViewById(R.id.add_date);
        mDate.setMinDate(System.currentTimeMillis() - 1000);
        mLatitude = (EditText) v.findViewById(R.id.add_latitude);
        mLongitude = (EditText) v.findViewById(R.id.add_longitude);
        mButton = (Button) v.findViewById(R.id.save_item);
        mError = (TextView) v.findViewById(R.id.error_box);

        mButton.setOnClickListener(view->{
            if(
                    !mName.getText().toString().matches("")
                    && !mDescription.getText().toString().matches("")
                    && !mLatitude.getText().toString().matches("")
                    && !mLongitude.getText().toString().matches("")) {
                BucketList item = new BucketList(mName.getText().toString(), mDescription.getText().toString(), Double.parseDouble(mLongitude.getText().toString()), Double.parseDouble(mLatitude.getText().toString()), getDateFromDatePicker(mDate));
                mBucketList.add(item);
                Intent data = new Intent();
                getActivity().setResult(getActivity().RESULT_OK, data);
                getActivity().finish();
            } else {
                mError.setText("Complete all blanks");
            }
        });
        return v;
    }

}
