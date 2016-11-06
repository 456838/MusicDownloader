package com.salton123.musicdownloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.filedownloaderfinal.FileDownloaderCallback;
import cn.finalteam.filedownloaderfinal.SimpleFileDownloader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    @BindView(R.id.transparent_view)
    View transparentView;
    @BindView(R.id.searchTextView)
    EditText searchTextView;
    @BindView(R.id.action_up_btn)
    ImageButton actionUpBtn;
    @BindView(R.id.action_voice_btn)
    ImageButton actionVoiceBtn;
    @BindView(R.id.action_empty_btn)
    ImageButton actionEmptyBtn;
    @BindView(R.id.search_top_bar)
    RelativeLayout searchTopBar;
    @BindView(R.id.suggestion_list)
    ListView suggestionList;
    @BindView(R.id.search_layout)
    FrameLayout searchLayout;
    ResultAdapter mResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ButterKnife.bind(this);
        mResultAdapter = new ResultAdapter(this);
        suggestionList.setAdapter(mResultAdapter);
        suggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SearchResult searchResult = (SearchResult) adapterView.getItemAtPosition(position);
                simpleDownloadFile(searchResult.getHqUrl());
            }
        });
        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(searchTextView.getText())) {       //如果输入的内容是空的话，显示语音的按钮
                    actionEmptyBtn.setVisibility(View.INVISIBLE);
                    actionVoiceBtn.setVisibility(View.VISIBLE);
                } else {
                    actionEmptyBtn.setVisibility(View.VISIBLE);
                    actionVoiceBtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                    startSearchMusic(searchTextView.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }

        });
        actionVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearchMusic(searchTextView.getText().toString());
            }
        });

        actionEmptyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchTextView.setText("");
                return false;
            }
        });

    }

    ProgressDialog pd;

    private void startSearchMusic(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(getApplicationContext(), "输入的内容为空，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            String tarUrl = getTargetUrl("wy", keyword);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(tarUrl)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    pd.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Looper.prepare();
                    String responseStr = response.body().string();
                    System.out.println(responseStr);
                    KLog.json(responseStr);

                    try {
                        List<SearchResult> resultList = new Gson().fromJson(responseStr, new TypeToken<List<SearchResult>>() {
                        }.getType());
                        mResultAdapter.AddAll(resultList);
                        pd.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "解析查询结果出错：\n" + e.getMessage(), Toast.LENGTH_SHORT);
                        e.printStackTrace();
                        pd.dismiss();
                    }
                }
            });

//        http://api.itwusun.com/music/search/wy/1?format=json&sign=a5cc0a8797539d3a1a4f7aeca5b695b9&keyword=%E5%A4%A7%E7%BE%8E%E5%A6%9E
        }
    }

    private String getTargetUrl(String source, String keyword) {
        String url = "http://api.itwusun.com/music/search/" + source + "/1?format=json&sign=a5cc0a8797539d3a1a4f7aeca5b695b9&keyword=" + keyword;
        return url;
    }

    class ResultAdapter extends AdapterBase<SearchResult> {

        public ResultAdapter(Context pContext) {
            super(pContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = GetLayoutInflater().inflate(R.layout.adapter_result, null, false);
            }

            TextView tv_songName = ViewHolder.get(convertView, R.id.tv_SongName);
            TextView tv_download_url_sq = ViewHolder.get(convertView, R.id.tv_download_url_sq);
            TextView tv_download_url_hq = ViewHolder.get(convertView, R.id.tv_download_url_hq);
            TextView tv_download_url_lq = ViewHolder.get(convertView, R.id.tv_download_url_lq);
            tv_songName.setText(getItem(position) == null ? "找不到歌曲名" : getItem(position).getSongName());
            if (getItem(position) != null && !TextUtils.isEmpty(getItem(position).getSqUrl())) {
                tv_download_url_sq.setText(getItem(position) == null ? "" : getItem(position).getSqUrl());
            }
            if (getItem(position) != null && !TextUtils.isEmpty(getItem(position).getHqUrl())) {
                tv_download_url_hq.setText(getItem(position) == null ? "" : getItem(position).getHqUrl());
            }
            if (getItem(position) != null && !TextUtils.isEmpty(getItem(position).getLqUrl())) {
                tv_download_url_lq.setText(getItem(position) == null ? "" : getItem(position).getLqUrl());
            }
//            tv_download_url_sq.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(),"tv_download_url_sq",Toast.LENGTH_SHORT).show();
//                }
//            });
//            tv_download_url_hq.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(),"tv_download_url_hq",Toast.LENGTH_SHORT).show();
//                }
//            });
//            tv_download_url_lq.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(),"tv_download_url_lq",Toast.LENGTH_SHORT).show();
//                }
//            });
            return convertView;
        }
    }

    private void simpleDownloadFile(String url) {
        SimpleFileDownloader.downloadFile(url, "/sdcard/",new MyDownloader());
    }

    class MyDownloader extends FileDownloaderCallback{
        /**
         * onPending 等待，已经进入下载队列	数据库中的soFarBytes与totalBytes
         * connected 已经连接上	ETag, 是否断点续传, soFarBytes, totalBytes
         * @param downloadId
         * @param soFarBytes
         * @param totalBytes
         * @param preProgress
         */
        public void onStart(int downloadId, long soFarBytes, long totalBytes, int preProgress) {

        }

        /**
         * 下载进度回调	soFarBytes
         * @param downloadId
         * @param soFarBytes
         * @param totalBytes
         * @param speed
         * @param progress
         */
        public void onProgress(int downloadId, long soFarBytes, long totalBytes, long speed, int progress) {

        }

        /**
         * 等待
         * @param downloadId
         */
        public void onWait(int downloadId) {
        }

        /**
         * paused	暂停下载	soFarBytes
         * error	下载出现错误	抛出的Throwable
         * @param downloadId
         * @param soFarBytes
         * @param totalBytes
         * @param progress
         */
        public void onStop(int downloadId, long soFarBytes, long totalBytes, int progress) {

        }

        /**
         * 完成整个下载过程
         * @param downloadId
         * @param path
         */
        public void onFinish(int downloadId, String path) {
            Toast.makeText(getApplicationContext(),"下载完毕",Toast.LENGTH_SHORT).show();
        }
    }

}
