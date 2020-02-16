package club.crabglory.www.common.widget.micro;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import club.crabglory.www.common.R;
import club.crabglory.www.common.widget.micro.selector.FullWindowVideoView;

public class PlayerFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_player, container, false);
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        String path = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path)) {
            getActivity().finish();
        }
        FullWindowVideoView videoView = root.findViewById(R.id.videoView);
        videoView.setVideoPath(path);
        videoView.start();
//        videoView.setDataSource(path);
//        videoView.startPlay();
    }
}
