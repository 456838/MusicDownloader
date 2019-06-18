package com.salton123.bmob.helper;


import com.salton123.log.XLog;

import java.io.File;
import java.util.List;

import cn.bmob.sdkdemo.bean.User;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/3 10:52
 * ModifyTime: 10:52
 * Description:
 */
public class BmobHelper {
    public static Observable<BmobFile> uploadFile(final String fileLocalPath, final UploadFileListener listener) {
        return Observable.create(new ObservableOnSubscribe<BmobFile>() {
            @Override
            public void subscribe(final ObservableEmitter<BmobFile> emitter) {
                File localFile = new File(fileLocalPath);
                final BmobFile bmobFile = new BmobFile(localFile);
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void onProgress(Integer value) {
                        if (listener != null) {
                            listener.onProgress(value);
                        }
                    }

                    @Override
                    public void onStart() {
                        if (listener != null) {
                            listener.onStart();
                        }
                    }

                    @Override
                    public void done(BmobException e) {
                        if (listener != null) {
                            listener.done(e);
                        }
                        if (e != null) {
                            emitter.onNext(bmobFile);
                        }
                    }

                    @Override
                    public void doneError(int code, String msg) {
                        if (listener != null) {
                            listener.doneError(code, msg);
                        }
                    }
                });
            }
        });
    }

    public static Observable<User> login(final String username, final String password) {
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(final ObservableEmitter emitter) throws Exception {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            emitter.onNext(user);
                        } else {
                            emitter.onError(e);
                        }
                    }
                });
            }
        });

    }

    public static Observable<List<BmobFile>> uploadBatch(
            final List<String> localFileList,
            final UploadBatchListener listener) {
        return Observable.create(new ObservableOnSubscribe<List<BmobFile>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<BmobFile>> emitter) throws Exception {
                BmobFile.uploadBatch(localFileList.toArray(new String[]{}),
                        new UploadBatchListener() {
                            @Override
                            public void onSuccess(List<BmobFile> list, List<String> list1) {
                                if (listener != null) {
                                    listener.onSuccess(list, list1);
                                }
                                for (BmobFile item : list) {
                                    if (item.getLocalFile().exists()) {
                                        item.getLocalFile().delete();
                                    }
                                }
                                if (list1.size() == localFileList.size()) {
                                    emitter.onNext(list);
                                }
                            }

                            @Override
                            public void onProgress(int i, int i1, int i2, int i3) {
                                if (listener != null) {
                                    listener.onProgress(i, i1, i2, i3);
                                }
                                XLog.i(BmobHelper.class, "i:" + i + ",i1:" + i1 + ",i2:" + i2 + ",i3:" + i3);
                            }

                            @Override
                            public void onError(int i, String s) {
                                if (listener != null) {
                                    listener.onError(i, s);
                                }
                                XLog.i(BmobHelper.class, "i:" + i + ",s:" + s);
                            }
                        });
            }
        });
    }

    public static Observable<Boolean> insertBatch(final List<BmobObject> files) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) {
                new BmobBatch().insertBatch(files).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {
                        if (e != null) {
                            XLog.i(BmobHelper.class, "e:" + e);
                        }
                        for (BatchResult item : list) {
                            emitter.onNext(item.isSuccess());
                        }
                    }
                });
            }
        });
    }
}
