package io.github.ryanhoo.music;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.liulishuo.okdownload.DownloadMonitor;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.breakpoint.RemitStoreOnSQLite;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.salton123.base.ApplicationBase;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/31/16
 * Time: 9:32 PM
 * Desc: MusicPlayerApplication
 */
public class MusicPlayerApplication extends ApplicationBase implements DownloadMonitor {

    @Override
    public void onCreate() {
        super.onCreate();
        initDownloadConfig();
    }


    private void initDownloadConfig() {
        OkDownload.with().setMonitor(this);

        DownloadDispatcher.setMaxParallelRunningCount(3);

        // RemitStoreOnSQLite.setRemitToDBDelayMillis(3000);

    }

    @Override
    public boolean lowPriority() {
        // initDownloadConfig();
        return super.lowPriority();
    }

    @Override
    public void taskStart(DownloadTask task) {

    }

    @Override
    public void taskDownloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {

    }

    @Override
    public void taskDownloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @Nullable ResumeFailedCause cause) {

    }

    @Override
    public void taskEnd(DownloadTask task, EndCause cause, @Nullable Exception realCause) {

    }
}
