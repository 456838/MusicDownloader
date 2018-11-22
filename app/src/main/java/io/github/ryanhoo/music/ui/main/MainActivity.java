package io.github.ryanhoo.music.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.github.ryanhoo.music.R;
import io.github.ryanhoo.music.ui.base.BaseActivity;
import io.github.ryanhoo.music.ui.base.BaseFragment;
import io.github.ryanhoo.music.ui.local.LocalFilesFragment;
import io.github.ryanhoo.music.ui.music.MusicPlayerFragment;
import io.github.ryanhoo.music.ui.playlist.PlayListFragment;
import io.github.ryanhoo.music.ui.recommend.RecommendFragment;
import io.github.ryanhoo.music.ui.settings.SettingsFragment;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    static final int DEFAULT_PAGE_INDEX = 2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindViews({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files, R.id.radio_button_settings})
    List<RadioButton> radioButtons;

    String[] mTitles;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initVariable(Bundle bundle) {

    }

    @Override
    public void initViewAndData() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        List<PermissionItem> permissionItems = new ArrayList<>();
        permissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE));
        permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE));
        permissionItems.add(new PermissionItem(Manifest.permission.INTERNET));
        HiPermission.create(this)
                .permissions(permissionItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        checkPermission();
                    }

                    @Override
                    public void onFinish() {
                        checkPermission();
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        Toast.makeText(getApplicationContext(), "请授予应用必要的权限以满足您的使用需求", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
    }

    private void checkPermission() {
        boolean hasReadStorgePermission = HiPermission.checkPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean hasWriteStorgePermission = HiPermission.checkPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean hasInternetPermission = HiPermission.checkPermission(getApplicationContext(), Manifest.permission.INTERNET);
        if (hasReadStorgePermission && hasWriteStorgePermission && hasInternetPermission) {
            loadData();
        } else {
            Toast.makeText(getApplicationContext(), "请授予应用必要的权限以满足您的使用需求", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void loadData() {

        // Main Controls' Titles
        mTitles = getResources().getStringArray(R.array.mp_main_titles);

        // Fragments
        Fragment[] fragments = new Fragment[mTitles.length];
        fragments[0] = new RecommendFragment();
        fragments[1] = new MusicPlayerFragment();
        fragments[2] = new LocalFilesFragment();
        fragments[3] = new SettingsFragment();

        // Inflate ViewPager
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), mTitles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.mp_margin_large));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Empty
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Empty
            }

            @Override
            public void onPageSelected(int position) {
                radioButtons.get(position).setChecked(true);
            }
        });

        radioButtons.get(DEFAULT_PAGE_INDEX).setChecked(true);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        moveTaskToBack(true);
    }

    @OnCheckedChanged({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files, R.id.radio_button_settings})
    public void onRadioButtonChecked(RadioButton button, boolean isChecked) {
        if (isChecked) {
            onItemChecked(radioButtons.indexOf(button));
        }
    }

    private void onItemChecked(int position) {
        viewPager.setCurrentItem(position);
        toolbar.setTitle(mTitles[position]);
    }

}
