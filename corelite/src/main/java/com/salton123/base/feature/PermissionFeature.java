package com.salton123.base.feature;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.widget.Toast;

/**
 * User: newSalton@outlook.com
 * Date: 2018/12/25 4:59 PM
 * ModifyTime: 4:59 PM
 * Description:
 */
@SuppressLint("ValidFragment")
public class PermissionFeature extends Fragment implements IFeature {

    private Activity mActivity;

    public PermissionFeature(Activity activity) {
        this.mActivity = activity;
    }

    private int REQUEST_CODE = 0x101;
    private String[] permissions = getPermissionArr();

    @Override
    public void onBind() {
        mActivity.getFragmentManager()
                .beginTransaction()
                .add(this, "PermissionFeature")
                .commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!isPermissionGrant(mActivity)) {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    public String[] getPermissionArr() {
        return new String[]{
                Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
    }

    @Override
    public void onUnBind() {

    }

    private boolean isPermissionGrant(Context context) {
        boolean result = false;
        for (String item : permissions) {
            result &= context.checkPermission(item, Process.myPid(),
                    Process.myUid()) == PackageManager.PERMISSION_GRANTED;
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mActivity,
                        "请授予全部权限",
                        Toast.LENGTH_LONG).show();
                mActivity.finish();
            }
        }
    }
}
