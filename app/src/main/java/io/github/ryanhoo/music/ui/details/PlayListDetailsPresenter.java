package io.github.ryanhoo.music.ui.details;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.github.ryanhoo.music.RxBus;
import io.github.ryanhoo.music.data.model.PlayList;
import io.github.ryanhoo.music.data.model.Song;
import io.github.ryanhoo.music.data.source.AppRepository;
import io.github.ryanhoo.music.event.PlayListUpdatedEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/14/16
 * Time: 2:35 AM
 * Desc: PlayListDetailsPresenter
 */
public class PlayListDetailsPresenter implements PlayListDetailsContract.Presenter {

    private PlayListDetailsContract.View mView;
    private AppRepository mRepository;
    private CompositeDisposable mSubscriptions;

    public PlayListDetailsPresenter(AppRepository repository, PlayListDetailsContract.View view) {
        mView = view;
        mRepository = repository;
        mSubscriptions = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        // Nothing to do
    }

    @Override
    public void unsubscribe() {
        mView = null;
        mSubscriptions.clear();
    }

    @Override
    public void addSongToPlayList(Song song, PlayList playList) {
        if (playList.isFavorite()) {
            song.setFavorite(true);
        }
        playList.addSong(song, 0);
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

    @Override
    public void delete(final Song song, PlayList playList) {
        playList.removeSong(song);
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
                        mView.onSongDeleted(song);
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
