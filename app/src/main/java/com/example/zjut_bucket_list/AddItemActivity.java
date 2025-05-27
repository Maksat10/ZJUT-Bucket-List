package com.example.zjut_bucket_list;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import java.util.UUID;

public class AddItemActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AddItemFragment();
    }
}
