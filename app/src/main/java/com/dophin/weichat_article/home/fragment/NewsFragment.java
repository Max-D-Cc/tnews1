package com.dophin.weichat_article.home.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ak.android.engine.nav.NativeAd;
import com.ak.android.engine.nav.NativeAdLoaderListener;
import com.ak.android.engine.navbase.NativeAdLoader;
import com.ak.android.shell.AKAD;
import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.feeds.NativeErrorCode;
import com.baidu.mobad.feeds.NativeResponse;
import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.CpuInfoManager;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.home.activity.ArticleActivity;
import com.dophin.weichat_article.home.adapter.NewsAdapter;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.home.bean.InnerNews;
import com.dophin.weichat_article.home.bean.Native360;
import com.dophin.weichat_article.home.bean.News;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.utils.EndLessOnScrollListener;
import com.dophin.weichat_article.utils.RecyclerItemClickListener;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {


    @BindView(R.id.newstv)
    RecyclerView newstv;
    @BindView(R.id.news_swipe)
    SwipeRefreshLayout newsSwipe;
    private String mTitle;
    private String orderId;
    private int page = 1;
    private int curPage = 0;
    private SubscriberOnNextListener<News> subscriberOnNextListener;
    private List<InnerNews> list = new ArrayList<>();
    private int statue;
    private int nativeStatue = 0;
    private int listSize = 0;
    private NewsAdapter adapter;
    boolean isLoading;
    private Handler handler = new Handler();


    public static NewsFragment getInstance(String title) {

        NewsFragment fft = new NewsFragment();
        fft.mTitle = title;
        return fft;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = View.inflate(mActivity, R.layout.fragment_news, null);
        ButterKnife.bind(this, inflate);
        orderId = (String) SPUtils.getParam(mActivity, "user", "orderid", "");
        list.clear();
        initView();
        return inflate;
    }

    private void initView() {
        adapter = new NewsAdapter(mActivity,list);
        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        newstv.setLayoutManager(manager);
        newstv.setItemAnimator(new DefaultItemAnimator());
        newstv.setAdapter(adapter);

        newsSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                statue = 1;
                page ++;
                listSize = list.size();
                Log.e("cai" ,"当前页数" + (curPage+page));
                if (mTitle.equals("推荐")){
                    HttpMethods.getInstance().getNewsRcommend(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),orderId);
                }else if(mTitle.equals("娱乐")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(1));
                }else if(mTitle.equals("新闻")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(2));
                }else if(mTitle.equals("搞笑")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(4));
                }else if(mTitle.equals("二次元")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(10));
                }else if(mTitle.equals("笑话")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(6));
                }else if(mTitle.equals("体育")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(7));
                }else if(mTitle.equals("美图")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(5));
                }else if(mTitle.equals("财经")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(3));
                }else if(mTitle.equals("手机")){
                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(8));
                }
//                page ++;

            }
        });

        newstv.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, newstv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(list.get(position).isBdNative){
                    list.get(position).bdNative.handleClick(view);
                }else{
                    Intent intent = new Intent(mActivity, ArticleActivity.class);
                    InnerNews innerNews = list.get(position);
                    intent.putExtra("url",innerNews.getURL());
                    intent.putExtra("id",innerNews.getUID());
                    intent.putExtra("title",innerNews.getTITLE());
                    intent.putExtra("type",innerNews.getADTYPE());
                    intent.putExtra("img",innerNews.getIMAGE1());
                    intent.putExtra("num",innerNews.getCOMMENTNUM());
                    intent.putExtra("ding",innerNews.getHCLICKZAN());
                    intent.putExtra("cai",innerNews.getCAINUM());
                    startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.right_center, R.anim.center_left);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        newstv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = newsSwipe.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
//                        getData();
                        listSize = list.size();
                        page++;
                        statue = 0;
                       handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                            Log.d("test", "load more completed");
                            isLoading = false;
                        }
                    }, 1000);
                    }
                }
            }
        });

//        newstv.setOnScrollListener(new EndLessOnScrollListener(manager) {
//            @Override
//            public void onLoadMore(int currentPage) {
////                currentPage = page;
//                curPage = currentPage;
//                listSize = list.size();
//                Log.e("cai" ,"当前页数" + (currentPage+page) + " ---" + currentPage);
//                if (mTitle.equals("推荐")){
//                    HttpMethods.getInstance().getNewsRcommend(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),orderId);
//                }else if(mTitle.equals("娱乐")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(1));
//                }else if(mTitle.equals("新闻")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(2));
//                }else if(mTitle.equals("搞笑")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(4));
//                }else if(mTitle.equals("二次元")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(10));
//                }else if(mTitle.equals("笑话")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(6));
//                }else if(mTitle.equals("体育")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(7));
//                }else if(mTitle.equals("美图")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(5));
//                }else if(mTitle.equals("财经")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(3));
//                }else if(mTitle.equals("手机")){
//                    HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(currentPage + page),String.valueOf(8));
//                }
//            }
//        });


        subscriberOnNextListener = new SubscriberOnNextListener<News>() {
            @Override
            public void onNext(News news) {
                if (news.getFINDBZYHTML_XGTJ() != null){
                    if (statue == 1){
//                        list.clear();
                        list.addAll(0,news.getFINDBZYHTML_XGTJ());
                    }else{
                        list.addAll(news.getFINDBZYHTML_XGTJ());
                    }
                    adapter.notifyDataSetChanged();
                    newsSwipe.setRefreshing(false);
                    adapter.notifyItemRemoved(adapter.getItemCount());
                    fetchAd(mActivity);
                    load360Native(mActivity);
                }
            }
        };


        if (mTitle.equals("推荐")){
            HttpMethods.getInstance().getNewsRcommend(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),orderId);
        }else if(mTitle.equals("娱乐")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(1));
        }else if(mTitle.equals("新闻")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(2));
        }else if(mTitle.equals("搞笑")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(4));
        }else if(mTitle.equals("二次元")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(10));
        }else if(mTitle.equals("笑话")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(6));
        }else if(mTitle.equals("体育")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(7));
        }else if(mTitle.equals("美图")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(5));
        }else if(mTitle.equals("财经")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(3));
        }else if(mTitle.equals("手机")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf("1"),String.valueOf(8));
        }
    }

    private  void getData(){

        if (mTitle.equals("推荐")){
            HttpMethods.getInstance().getNewsRcommend(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),orderId);
        }else if(mTitle.equals("娱乐")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(1));
        }else if(mTitle.equals("新闻")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(2));
        }else if(mTitle.equals("搞笑")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(4));
        }else if(mTitle.equals("二次元")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(10));
        }else if(mTitle.equals("笑话")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(6));
        }else if(mTitle.equals("体育")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(7));
        }else if(mTitle.equals("美图")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(5));
        }else if(mTitle.equals("财经")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(3));
        }else if(mTitle.equals("手机")){
            HttpMethods.getInstance().getNewsClassify(new ProgressSubscriber<News>(subscriberOnNextListener,mActivity,1),String.valueOf(page),String.valueOf(8));
        }
    }




    public void fetchAd(Activity activity) {
        /**
         * Step 1. 创建BaiduNative对象，参数分别为： 上下文context，广告位ID,
         * BaiduNativeNetworkListener监听（监听广告请求的成功与失败）
         * 注意：请将YOUR_AD_PALCE_ID替换为自己的广告位ID
         */
        String appid = SharePerUtils.getString(mActivity, "appid", "");
        String adid = SharePerUtils.getString(mActivity, "adid", "");

        BaiduNative.setAppSid(getActivity(), appid);
        BaiduNative baidu = new BaiduNative(activity,adid,
                new BaiduNative.BaiduNativeNetworkListener() {

                    @Override
                    public void onNativeFail(NativeErrorCode arg0) {
                        Log.w("ListViewActivity",
                                "onNativeFail reason:" + arg0.name());
                    }

                    @Override
                    public void onNativeLoad(List<NativeResponse> nativeList) {
                        // 一个广告只允许展现一次，多次展现、点击只会计入一次
//                        System.out.println(nativeList.size() + "size");
                        Log.e("cai","信息流： " +nativeList.size());
                        if (nativeList != null && nativeList.size() > 0) {
                            NativeResponse n1 = nativeList.get(0);
                            InnerNews innerNews1 = new InnerNews();
                            innerNews1.setTITLE(n1.getTitle().trim() + "  " + n1.getDesc().trim());
                            innerNews1.isBdNative = true;
                            innerNews1.bdNative = n1;
                            innerNews1.setTIME("");
                            if (TextUtils.isEmpty(n1.getIconUrl())){
                                innerNews1.setIMAGE1(n1.getImageUrl());
                            }else {
                                innerNews1.setIMAGE1(n1.getIconUrl());
                            }
                            innerNews1.setSTATUE(1);
//                            Log.e("cai", "logoUrl1: " + n1.getIconUrl());
//                            Log.e("cai", "imageUrl: " + n1.getImageUrl());

                            NativeResponse n2 = nativeList.get(1);
                            InnerNews innerNews2 = new InnerNews();
                            innerNews2.setTITLE(n2.getTitle().trim() + "  " + n2.getDesc().trim());
                            innerNews2.isBdNative = true;
                            innerNews2.bdNative = n2;
                            innerNews2.setTIME("");
                            if (TextUtils.isEmpty(n2.getIconUrl())){
                                innerNews2.setIMAGE1(n2.getImageUrl());
                            }else{
                                innerNews2.setIMAGE1(n2.getIconUrl());
                            }
                            innerNews2.setSTATUE(1);
//                            Log.e("cai", "logoUrl1: " + n2.getIconUrl());
//                            Log.e("cai", "imageUrl: " + n2.getImageUrl());

                            int size = list.size();
                            if (statue == 1) {
                                if (listSize < size - 2) {
                                    list.add(1, innerNews1);
                                }

                                if (listSize < size - 9) {
                                    list.add(8, innerNews2);
                                }

                            } else {
                                if (listSize < size - 2) {
                                    list.add(listSize + 1, innerNews1);
                                }

                                if (listSize < size - 9) {
                                    list.add(listSize + 8, innerNews2);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        /**
         * Step 2. 创建requestParameters对象，并将其传给baidu.makeRequest来请求广告
         */
        // 用户点击下载类广告时，是否弹出提示框让用户选择下载与否
        RequestParameters requestParameters = new RequestParameters.Builder()
                .downloadAppConfirmPolicy(
                        RequestParameters.DOWNLOAD_APP_CONFIRM_ALWAYS).build();

        baidu.makeRequest(requestParameters);
    }


    private NativeAdLoader mNativeLoader;

    private void load360Native(Context context) {
        mNativeLoader = AKAD.getNativeAdLoader(context, "kuub4p74ry",
                new NativeAdLoaderListener() {
                    @Override
                    public void onAdLoadSuccess(ArrayList<NativeAd> ads) {
                        // 广告请求成功,返回一组 NativeAd
//                        System.out.println("360广告" + ads.size());
                        Log.e("cai","请求成功：" + ads.size());
                        if(ads.size() > 0){
                            Gson gson = new Gson();
                            InnerNews innerNews = new InnerNews();
                            innerNews.setSTATUE(1);
                            innerNews.is360Native = true;
                            Native360 native360 = gson.fromJson(ads.get(0).getContent().toString(), Native360.class);
                            innerNews.native360 =native360;
                            innerNews.nativeAd360 = ads.get(0);
                            innerNews.setTITLE(native360.getTitle()+ "," + native360.getDesc());
                            innerNews.setIMAGE1(native360.getContentimg());

                            int size = list.size();
                            if (statue == 1) {
                                if (listSize < size - 5) {
                                    list.add(4, innerNews);
                                }

                            } else {
                                if (listSize < size - 5) {
                                    list.add(listSize + 4, innerNews);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onAdLoadFailed(int errorCode, String errorMsg) {
//                        System.out.println("360广告请求失败");
                        Log.e("cai","请求失败");
                        // 广告请求失败
                    }
                });
        // 加载广告
        if (mNativeLoader != null) {
            mNativeLoader.loadAds();
        }
    }

    @Override
    public void onDestroy() {
        if (mNativeLoader != null) {
            mNativeLoader.destroy();
        }
        super.onDestroy();
    }

}
