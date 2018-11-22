package io.github.ryanhoo.music.ui.local.filesystem;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ryanhoo.music.R;
import io.github.ryanhoo.music.RxBus;
import io.github.ryanhoo.music.event.AddFolderEvent;
import io.github.ryanhoo.music.ui.base.BaseActivity;
import io.github.ryanhoo.music.ui.base.adapter.OnItemClickListener;
import io.github.ryanhoo.music.ui.base.adapter.OnItemLongClickListener;
import io.github.ryanhoo.music.ui.common.DefaultDividerDecoration;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.util.*;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/3/16
 * Time: 11:31 PM
 * Desc: FileSystemActivity
 */
public class FileSystemActivity extends BaseActivity {

    private static final String TAG = "SystemFileActivity";

    final File SDCARD = Environment.getExternalStorageDirectory();
    String DEFAULT_SDCARD_NAME;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.text_view_empty)
    View emptyView;

    FileSystemAdapter mAdapter;
    FileTreeStack mFileTreeStack;

    File mFileParent;
    List<FileWrapper> mFiles;
    List<File> mSelectedFiles = new ArrayList<>();

    ActionModeCallback mActionModeCallback;


    @Override
    public int getLayout() {
        return R.layout.activity_file_system;
    }

    @Override
    public void initVariable(Bundle bundle) {

    }

    @Override
    public void initViewAndData() {
        ButterKnife.bind(this);
        supportActionBar(toolbar);

        DEFAULT_SDCARD_NAME = getString(R.string.mp_activity_title_file_system);

        mFileTreeStack = new FileTreeStack();
        mAdapter = new FileSystemAdapter(this, null);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FileWrapper fileWrapper = mAdapter.getItem(position);
                if (isInActionMode()) {
                    toggleItemView(fileWrapper, position, !fileWrapper.selected);
                } else {
                    File file = fileWrapper.file;
                    if (file.isDirectory()) {
                        storeSnapshot();
                        toolbar.setTitle(getToolbarTitle(file));
                        loadFiles(file);
                    }
                }
            }
        });
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemClick(int position) {
                FileWrapper fileWrapper = mAdapter.getItem(position);
                if (fileWrapper.file.isDirectory()) {
                    if (mActionModeCallback == null || !mActionModeCallback.isShowing()) {
                        startActionMode();
                    }
                    toggleItemView(fileWrapper, position, !fileWrapper.selected);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DefaultDividerDecoration());

        loadFiles(SDCARD);
    }

    // Options Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_system, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_done) {
            AddFolderEvent event = new AddFolderEvent(mFileParent);
            RxBus.getInstance().post(event);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (mFileTreeStack.size() == 0) {
            super.onBackPressed();
        } else {
            restoreSnapshot(mFileTreeStack.pop());
        }
    }

    // FileTreeSnapshot

    private void storeSnapshot() {
        FileTreeStack.FileTreeSnapshot snapshot = new FileTreeStack.FileTreeSnapshot();
        snapshot.parent = mFileParent;
        snapshot.files = mFiles;
        snapshot.scrollOffset = recyclerView.computeVerticalScrollOffset();
        mFileTreeStack.push(snapshot);
    }

    private void restoreSnapshot(FileTreeStack.FileTreeSnapshot snapshot) {
        final File parent = snapshot.parent;
        final List<FileWrapper> files = snapshot.files;
        final int scrollOffset = snapshot.scrollOffset;

        mFileParent = parent;
        mFiles = files;

        final int oldScrollOffset = recyclerView.computeVerticalScrollOffset();

        toolbar.setTitle(getToolbarTitle(parent));
        mAdapter.setData(files);
        mAdapter.notifyDataSetChanged();
        toggleEmptyViewVisibility();

        recyclerView.scrollBy(0, scrollOffset - oldScrollOffset);
    }

    private String getToolbarTitle(File parent) {
        return parent.getAbsolutePath().equals(SDCARD.getAbsolutePath()) ? DEFAULT_SDCARD_NAME : parent.getName();
    }

    // Load files

    private void loadFiles(final File parent) {
        Disposable subscription = io.reactivex.Observable.just(parent)
                .flatMap(new Function<File, ObservableSource<List<FileWrapper>>>() {
                    @Override
                    public ObservableSource<List<FileWrapper>> apply(File temp) throws Exception {
                        List<File> files = Arrays.asList(parent.listFiles(SystemFileFilter.DEFAULT_INSTANCE));
                        Collections.sort(files, new Comparator<File>() {
                            @Override
                            public int compare(File f1, File f2) {
                                if (f1.isDirectory() && f2.isFile()) {
                                    return -1;
                                }
                                if (f2.isDirectory() && f1.isFile()) {
                                    return 1;
                                }
                                return f1.getName().compareToIgnoreCase(f2.getName());
                            }
                        });
                        // Wrap files
                        List<FileWrapper> fileWrappers = new ArrayList<>(files.size());
                        for (File file : files) {
                            fileWrappers.add(new FileWrapper(file));
                        }
                        return io.reactivex.Observable.just(fileWrappers);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FileWrapper>>() {
                    @Override
                    public void accept(List<FileWrapper> fileWrappers) throws Exception {
                        onFilesLoaded(parent, fileWrappers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "onError: ", throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        toggleEmptyViewVisibility();
                    }
                });
        addSubscription(subscription);
    }

    private void onFilesLoaded(File parent, List<FileWrapper> files) {
        mFileParent = parent;
        mFiles = files;
        mAdapter.setData(files);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollTo(0, 0);
    }

    private void toggleEmptyViewVisibility() {
        emptyView.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    // ActionMode

    private boolean isInActionMode() {
        return mActionModeCallback != null && mActionModeCallback.isShowing();
    }

    private void startActionMode() {
        if (mActionModeCallback == null) {
            mActionModeCallback = new ActionModeCallback(this, new ActionModeCallback.ActionListener() {
                @Override
                public void onDismissAction() {
                    clearSelections();
                }

                @Override
                public void onDoneAction() {
                    if (mSelectedFiles.size() > 0) {
                        AddFolderEvent event = new AddFolderEvent(mSelectedFiles);
                        RxBus.getInstance().post(event);
                        finish();
                    } else {
                        mActionModeCallback.dismiss();
                    }
                }
            });
        }
        startSupportActionMode(mActionModeCallback);
        mActionModeCallback.setShowing(true);
    }

    private void toggleItemView(FileWrapper fileWrapper, int adapterPosition, boolean selected) {
        File file = fileWrapper.file;
        fileWrapper.selected = selected;
        if (selected) {
            mSelectedFiles.add(file);
        } else {
            if (mSelectedFiles.indexOf(file) != -1) {
                mSelectedFiles.remove(file);
            }
        }
        mAdapter.notifyItemChanged(adapterPosition);
        mActionModeCallback.updateSelectedItemCount(mSelectedFiles.size());
    }

    private void clearSelections() {
        boolean needsRefresh = mSelectedFiles.size() > 0;
        mSelectedFiles.clear();
        if (needsRefresh) {
            mAdapter.clearSelections();
            mAdapter.notifyDataSetChanged();
        }
    }
}
