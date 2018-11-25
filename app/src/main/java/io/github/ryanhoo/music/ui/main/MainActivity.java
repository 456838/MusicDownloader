package io.github.ryanhoo.music.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.salton123.base.FragmentDelegate;
import com.salton123.util.EventUtil;

import java.util.ArrayList;
import java.util.List;

import io.github.ryanhoo.music.R;
import io.github.ryanhoo.music.data.model.HotSong;
import io.github.ryanhoo.music.event.EventTags;
import io.github.ryanhoo.music.ui.base.BaseActivity;
import io.github.ryanhoo.music.ui.songlist.SongListFragment;
import io.github.ryanhoo.music.ui.songlist.TsComp;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.salton_fm_container;
    }

    @Override
    public void initVariable(Bundle bundle) {
        EventUtil.register(this);
    }

    @Override
    public void initViewAndData() {
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
        loadRootFragment(R.id.fl_container, FragmentDelegate.Companion.newInstance(MainComp.class));
    }

    @Subscribe(tags = {@Tag(EventTags.FRAGMENT_DELEGATE)})
    public void onFragmentDelegate(Object event) {
        ISupportFragment fragment = null;
        if (event instanceof HotSong) {
            Bundle bundle = new Bundle();
            bundle.putString(FragmentDelegate.ARG_ITEM, ((HotSong) event).getId());
            fragment = FragmentDelegate.Companion.newInstance(SongListFragment.class, bundle);
        }
        if (fragment != null) {
            start(fragment);
            // startWithPop(fragment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        moveTaskToBack(true);
    }

}
