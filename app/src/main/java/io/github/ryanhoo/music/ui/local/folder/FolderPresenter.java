package io.github.ryanhoo.music.ui.local.folder;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.github.ryanhoo.music.RxBus;
import io.github.ryanhoo.music.data.model.Folder;
import io.github.ryanhoo.music.data.model.PlayList;
import io.github.ryanhoo.music.data.model.Song;
import io.github.ryanhoo.music.data.source.AppRepository;
import io.github.ryanhoo.music.event.PlayListUpdatedEvent;
import io.github.ryanhoo.music.utils.FileUtils;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/10/16
 * Time: 11:38 PM
 * Desc: FolderPresenter
 */
public class FolderPresenter implements FolderContract.Presenter {

    private FolderContract.View mView;
    private AppRepository mRepository;
    private CompositeDisposable mSubscriptions;

    public FolderPresenter(AppRepository repository, FolderContract.View view) {
        mView = view;
        mRepository = repository;
        mSubscriptions = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadFolders();
    }

    @Override
    public void unsubscribe() {
        mView = null;
        mSubscriptions.clear();
    }

    @Override
    public void loadFolders() {
        Disposable subscription = mRepository.folders()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<List<Folder>>() {
                    @Override
                    public void accept(List<Folder> folders) throws Exception {
                        Collections.sort(folders, new Comparator<Folder>() {
                            @Override
                            public int compare(Folder f1, Folder f2) {
                                return f1.getName().compareToIgnoreCase(f2.getName());
                            }
                        });
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Folder>>() {
                    @Override
                    public void accept(List<Folder> folders) throws Exception {
                        mView.onFoldersLoaded(folders);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.handleError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void addFolders(List<File> folders, final List<Folder> existedFolders) {
        Disposable disposable = Observable.fromIterable(folders).filter(new Predicate<File>() {
            @Override
            public boolean test(File file) throws Exception {
                for (Folder folder : existedFolders) {
                    if (file.getAbsolutePath().equals(folder.getPath())) {
                        return false;
                    }
                }
                return true;
            }
        }).flatMap(new Function<File, ObservableSource<Folder>>() {
            @Override
            public ObservableSource<Folder> apply(File file) throws Exception {
                Folder folder = new Folder();
                folder.setName(file.getName());
                folder.setPath(file.getAbsolutePath());
                List<Song> musicFiles = FileUtils.musicFiles(file);
                folder.setSongs(musicFiles);
                folder.setNumOfSongs(musicFiles.size());
                return Observable.just(folder);
            }
        }).toList().flatMapObservable(new Function<List<Folder>, ObservableSource<List<Folder>>>() {
            @Override
            public ObservableSource<List<Folder>> apply(List<Folder> folders) throws Exception {
                return Observable.just(folders);
            }
        }).flatMap(new Function<List<Folder>, ObservableSource<List<Folder>>>() {
            @Override
            public ObservableSource<List<Folder>> apply(List<Folder> folders) throws Exception {
                return mRepository.create(folders);
            }
        }).doOnNext(new Consumer<List<Folder>>() {
            @Override
            public void accept(List<Folder> folders) throws Exception {
                Collections.sort(folders, new Comparator<Folder>() {
                    @Override
                    public int compare(Folder f1, Folder f2) {
                        return f1.getName().compareToIgnoreCase(f2.getName());
                    }
                });
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mView.showLoading();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Folder>>() {
                    @Override
                    public void accept(List<Folder> folders) throws Exception {
                        mView.onFoldersAdded(folders);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.handleError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                });

        mSubscriptions.add(disposable);
    }

    @Override
    public void refreshFolder(final Folder folder) {
        Disposable subscription = Observable.just(FileUtils.musicFiles(new File(folder.getPath())))
                .flatMap(new Function<List<Song>, ObservableSource<Folder>>() {
                    @Override
                    public ObservableSource<Folder> apply(List<Song> songs) throws Exception {
                        folder.setSongs(songs);
                        folder.setNumOfSongs(songs.size());
                        return mRepository.update(folder);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribe(new Consumer<Folder>() {
                    @Override
                    public void accept(Folder folder) throws Exception {
                        mView.onFolderUpdated(folder);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.handleError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void deleteFolder(Folder folder) {
        Disposable subscription = mRepository.delete(folder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribe(new Consumer<Folder>() {
                    @Override
                    public void accept(Folder folder) throws Exception {
                        mView.onFolderDeleted(folder);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.handleError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void createPlayList(PlayList playList) {
        Disposable subscription = mRepository
                .create(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribe(new Consumer<PlayList>() {
                    @Override
                    public void accept(PlayList playList) throws Exception {
                        mView.onPlayListCreated(playList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.handleError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void addFolderToPlayList(final Folder folder, PlayList playList) {
        if (folder.getSongs().isEmpty()) {
            return;
        }

        if (playList.isFavorite()) {
            for (Song song : folder.getSongs()) {
                song.setFavorite(true);
            }
        }
        playList.addSong(folder.getSongs(), 0);
        Disposable subscription = mRepository.update(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribe(new Consumer<PlayList>() {
                    @Override
                    public void accept(PlayList playList) throws Exception {
                        RxBus.getInstance().post(new PlayListUpdatedEvent(playList));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.handleError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.hideLoading();
                    }
                });
        mSubscriptions.add(subscription);
    }
}
