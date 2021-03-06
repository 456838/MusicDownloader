package io.github.ryanhoo.music.ui.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.github.ryanhoo.music.R;
import io.github.ryanhoo.music.ui.base.BaseFragment;
import io.github.ryanhoo.music.ui.local.all.AllLocalMusicFragment;
import io.github.ryanhoo.music.ui.local.folder.FolderFragment;
import io.github.ryanhoo.music.ui.playlist.PlayListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/1/16
 * Time: 9:58 PM
 * Desc: LocalFilesFragment
 */
public class LocalFilesFragment extends BaseFragment {

    // private static final String TAG = "LocalFilesFragment";

    static final int DEFAULT_SEGMENT_INDEX = 0;

    @BindViews({R.id.radio_button_all, R.id.radio_button_folder, R.id.radio_button_collection})
    List<RadioButton> segmentedControls;

    List<Fragment> mFragments = new ArrayList<>(3);

    final int[] FRAGMENT_CONTAINER_IDS = {
            R.id.layout_fragment_container_all, R.id.layout_fragment_container_folder, R.id.layout_fragment_container_collection
    };


    @Override
    public int getLayout() {
        return R.layout.fragment_local_files;
    }

    @Override
    public void initVariable(Bundle bundle) {

    }

    @Override
    public void initViewAndData() {
        ButterKnife.bind(this, getRootView());

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            fragmentTransaction.add(FRAGMENT_CONTAINER_IDS[i], fragment, fragment.getTag());
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();

        segmentedControls.get(DEFAULT_SEGMENT_INDEX).setChecked(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments.add(new AllLocalMusicFragment());
        mFragments.add(new FolderFragment());
        mFragments.add(new PlayListFragment());
    }


    @OnCheckedChanged({R.id.radio_button_all, R.id.radio_button_folder, R.id.radio_button_collection})
    public void onSegmentedChecked(RadioButton radioButton, boolean isChecked) {
        int index = segmentedControls.indexOf(radioButton);
        Fragment fragment = mFragments.get(index);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (isChecked) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }
}
