package vidyoconnector.vidyo.com.tablayouttutorial.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vidyoconnector.vidyo.com.tablayouttutorial.R;

public class DynamicFragment extends Fragment {
    View view;

    public static DynamicFragment newInstance(int val) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

    int val;
    TextView c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        val = getArguments().getInt("someInt", 0);
        c = view.findViewById(R.id.txtDynamic);
        c.setText("" + val);
        return view;
    }
}
