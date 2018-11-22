package io.github.ryanhoo.music.ui.playlist;

import java.util.List;

import io.github.ryanhoo.music.data.model.PlayList;
import io.github.ryanhoo.music.data.source.AppRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/11/16
 * Time: 1:28 AM
 * Desc: PlayListPresenter
 */
public class PlayListPresenter implements PlayListContract.Presenter {

    private PlayListContract.View mView;
    private AppRepository mRepository;
    private CompositeDisposable mSubscriptions;

    public PlayListPresenter(AppRepository repository, PlayListContract.View view) {
        mView = view;
        mRepository = repository;
        mSubscriptions = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadPlayLists();
    }

    @Override
    public void unsubscribe() {
        mView = null;
        mSubscriptions.clear();
    }

    @Override
    public void loadPlayLists() {
        Disposable subscription = mRepository.playLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .subscribe(new Consumer<List<PlayList>>() {
                    @Override
                    public void accept(List<PlayList> playLists) throws Exception {
                        mView.onPlayListsLoaded(playLists);
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
    public void editPlayList(PlayList playList) {
        Disposable subscription = mRepository
                .update(playList)
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
                        mView.onPlayListEdited(playList);
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
    public void deletePlayList(PlayList playList) {
        Disposable subscription = mRepository.delete(playList)
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
                        mView.onPlayListDeleted(playList);
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
