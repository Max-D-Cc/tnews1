package com.dophin.weichat_article.http;


import com.dophin.weichat_article.base.Constants;
import com.dophin.weichat_article.comment.bean.HotTalk;
import com.dophin.weichat_article.home.bean.BdId;
import com.dophin.weichat_article.home.bean.Comment;
import com.dophin.weichat_article.home.bean.CommentList;
import com.dophin.weichat_article.home.bean.FloorCommentList;
import com.dophin.weichat_article.home.bean.News;
import com.dophin.weichat_article.login.bean.PhoneLoginBean;
import com.dophin.weichat_article.login.bean.YunCode;
import com.dophin.weichat_article.main.bean.DownLoad;
import com.dophin.weichat_article.mine.bean.DayTr;
import com.dophin.weichat_article.mine.bean.ExchangeList;
import com.dophin.weichat_article.mine.bean.GuessList;
import com.dophin.weichat_article.mine.bean.GuessResult;
import com.dophin.weichat_article.mine.bean.GuessToday;
import com.dophin.weichat_article.mine.bean.GuessYesResult;
import com.dophin.weichat_article.mine.bean.Honor;
import com.dophin.weichat_article.mine.bean.IncomeList;
import com.dophin.weichat_article.mine.bean.OffLineList;
import com.dophin.weichat_article.mine.bean.RankList;
import com.dophin.weichat_article.mine.bean.RedPackage;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.mine.bean.TaskList;
import com.dophin.weichat_article.mine.bean.Tr;
import com.dophin.weichat_article.mine.bean.UserInfo;

import java.util.concurrent.TimeUnit;

import cn.yqzq.sharelib.S;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by invinjun on 2016/6/1.
 */

public class HttpMethods {

    public static final String BASE_URL = Constants.TNEWS;

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private MethodInterface methodInterface;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        methodInterface = retrofit.create(MethodInterface.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCODE() != 200) {
                throw new ApiException(httpResult.getMESSAGE());
            }
            return httpResult.getDATA();
        }
    }

    /**
     * 电话登录
     * @param subscriber
     * @param tel
     * @param password
     */

    public void getPhoneLogin(Subscriber<PhoneLoginBean> subscriber, String tel, String password){
        methodInterface.toPhoneLogin(tel, password)
                .map(new HttpResultFunc<PhoneLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 云平台验证码
     * @param subscriber
     * @param tel
     */
    public void getYunCode(Subscriber<YunCode> subscriber, String tel){
        methodInterface.toYunCode(tel)
                .map(new HttpResultFunc<YunCode>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 电话注册
     * @param subscriber
     * @param tel
     * @param password
     * @param yqm
     * @param smsMessageSid
     * @param checknum
     */
    public void getPhoneRegist(Subscriber<PhoneLoginBean> subscriber, String tel,String password,String yqm,String smsMessageSid,String checknum){
        methodInterface.toPhoneRegist(tel,password,yqm,smsMessageSid,checknum)
                .map(new HttpResultFunc<PhoneLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 微信登录
     * @param subscriber
     * @param unionid
     * @param screen_name
     * @param profile_image_url
     */
    public void getWxLogin(Subscriber<PhoneLoginBean> subscriber, String unionid, String screen_name,String profile_image_url){
        methodInterface.toWxLogin(unionid, screen_name,profile_image_url)
                .map(new HttpResultFunc<PhoneLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 忘记密码
     * @param subscriber
     * @param tel
     * @param password
     * @param checknum
     * @param smsMessageSid
     */
    public void getForgetPwd(Subscriber<PhoneLoginBean> subscriber, String tel,String password,String checknum,String smsMessageSid){
        methodInterface.toForgetPwd(tel,password,checknum,smsMessageSid)
                .map(new HttpResultFunc<PhoneLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 新闻推荐
     * @param subscriber
     * @param page
     * @param name
     */
    public void getNewsRcommend(Subscriber<News> subscriber, String page, String name){
        methodInterface.toNewsRcommend(page,name)
                .map(new HttpResultFunc<News>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

     /**
     * 热议
     * @param subscriber
     * @param page
     * @param
     */
    public void getNewsHotTalk(Subscriber<HotTalk> subscriber, String page){
        methodInterface.toNewsHotTalk(page)
                .map(new HttpResultFunc<HotTalk>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }





    /**
     * 新闻分类
     * @param subscriber
     * @param page
     * @param type
     */
    public void getNewsClassify(Subscriber<News> subscriber, String page, String type){
        methodInterface.toNewsClassify(page,type)
                .map(new HttpResultFunc<News>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 积分列表
     * @param subscriber
     * @param orderid
     * @param page
     */
    public void getIncomeLeft(Subscriber<IncomeList> subscriber, String orderid, String page){
        methodInterface.toIncomeLeft(orderid,page)
                .map(new HttpResultFunc<IncomeList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 现金列表
     * @param subscriber
     * @param orderid
     * @param page
     */
    public void getIncomeRight(Subscriber<IncomeList> subscriber, String orderid, String page){
        methodInterface.toIncomeRight(orderid,page)
                .map(new HttpResultFunc<IncomeList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 趣味才投注
     * @param subscriber
     * @param orderid
     * @param intid
     * @param
     * @param
     * @param
     */
    public void getGuess(Subscriber<Statue> subscriber, String orderid, String intid,String count_zheng,String count_fan){
        methodInterface.toGuess(orderid,intid,count_zheng,count_fan)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 趣味才偷看结果
     * @param subscriber
     * @param orderid
     * @param intid
     */
    public void getGuessResult(Subscriber<GuessResult> subscriber, String orderid, String intid){
        methodInterface.toGuessResute(orderid,intid)
                .map(new HttpResultFunc<GuessResult>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 趣味才昨日结果
     * @param subscriber
     * @param orderid
     */
    public void getGuessYesResult(Subscriber<GuessYesResult> subscriber, String orderid){
        methodInterface.toGuessYesResute(orderid)
                .map(new HttpResultFunc<GuessYesResult>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 趣味才今日话题
     * @param subscriber
     * @param orderid
     */
    public void getGuessToady(Subscriber<GuessToday> subscriber, String orderid){
        methodInterface.toGuessToday(orderid)
                .map(new HttpResultFunc<GuessToday>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 光荣榜
     * @param subscriber
     * @param type
     */
    public void getRank(Subscriber<RankList> subscriber, String type){
        methodInterface.toRank(type)
                .map(new HttpResultFunc<RankList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 下线列表
     * @param subscriber
     * @param userid
     * @param page
     * @param type
     * @param orderid
     */
    public void getOffLine(Subscriber<OffLineList> subscriber, String userid,String page,String type,String orderid){
        methodInterface.toOffLin(userid,page,type,orderid)
                .map(new HttpResultFunc<OffLineList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 勋章列表
     * @param subscriber
     * @param orderid
     */
    public void getHonor(Subscriber<Honor> subscriber, String orderid){
        methodInterface.toHonor(orderid)
                .map(new HttpResultFunc<Honor>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 领取勋章
     * @param subscriber
     * @param orderid
     * @param channel
     */
       public void getGetHonor(Subscriber<Statue> subscriber, String orderid,String channel){
        methodInterface.toGetHonor(orderid,channel)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 提现
     * @param subscriber
     * @param orderid
     * @param type
     * @param money
     * @param account
     */
    public void getExchange(Subscriber<Statue> subscriber, String orderid,String type,String money,String account){
        methodInterface.toExchange(orderid,type,money,account)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 兑换记录
     * @param subscriber
     * @param orderid
     */
    public void getExchangeList(Subscriber<ExchangeList> subscriber, String orderid){
        methodInterface.toExchangeList(orderid)
                .map(new HttpResultFunc<ExchangeList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 领取红包
     * @param subscriber
     * @param orderid
     */
    public void getRedPackage(Subscriber<RedPackage> subscriber, String orderid,String score,String time){
        methodInterface.toRedPackage(orderid,score,time)
                .map(new HttpResultFunc<RedPackage>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 用户信息
     * @param subscriber
     * @param orderid
     */
    public void getUserInfo(Subscriber<UserInfo> subscriber, String orderid){
        methodInterface.toUserInfo(orderid)
                .map(new HttpResultFunc<UserInfo>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 修改用户信息
     * @param subscriber
     * @param orderid
     * @param istel
     * @param checknum
     * @param smsMessageSid
     * @param nickname
     * @param tel
     * @param alipay_name
     * @param alipay
     * @param qq
     */
    public void getUpdateUserInfo(Subscriber<Statue> subscriber, String orderid, String istel, String checknum, String smsMessageSid, String nickname, String tel, String alipay_name, String alipay, String qq){
        methodInterface.toUpdateUserInfo(orderid,istel,checknum,smsMessageSid,nickname,tel,alipay_name,alipay,qq)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 评论列表
     * @param subscriber
     * @param type
     * @param uid
     * @param page
     */
    public void getCommnetList(Subscriber<CommentList> subscriber, String type, String uid, String page){
        methodInterface.toCommentList(type,uid,page)
                .map(new HttpResultFunc<CommentList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 评论
     * @param subscriber
     * @param type
     * @param uid
     * @param name
     * @param nickname
     * @param img
     * @param content
     * @param floor
     */
    public void getCommnet(Subscriber<Statue> subscriber, String type, String uid, String name, String nickname, String img, String content, String floor){
        methodInterface.toComment(type,uid,name,nickname,img,content,floor)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 楼中楼评论列表
     * @param subscriber
     * @param type
     * @param uid
     * @param page
     */
    public void getFloorCommnetList(Subscriber<FloorCommentList> subscriber, String type, String uid, String page, String floor){
        methodInterface.toFloorCommentList(type,uid,page,floor)
                .map(new HttpResultFunc<FloorCommentList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 楼中楼评论
     * @param subscriber
     * @param type
     * @param uid
     * @param name
     * @param nickname
     * @param img
     * @param content
     * @param floor
     * @param reply_name
     * @param reply_nickname
     * @param reply_img
     * @param reply_floor
     */
    public void getFloorCommnet(Subscriber<Statue> subscriber, String type, String uid, String name, String nickname, String img, String content, String floor,String reply_name,String reply_nickname,String reply_img,String reply_floor){
        methodInterface.toFloorComment(type,uid,name,nickname,img,content,floor,reply_name,reply_nickname,reply_img,reply_floor)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 评论点赞
     * @param subscriber
     * @param type
     * @param uid
     * @param name
     * @param floor
     */
    public void getCommnetLike(Subscriber<Statue> subscriber, String type, String uid, String name,String floor){
        methodInterface.toCommentLike(type,uid,name,floor)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 楼中楼点赞
     * @param subscriber
     * @param type
     * @param uid
     * @param name
     * @param floor
     * @param reply_floor
     */
    public void getFloorCommnetLike(Subscriber<Statue> subscriber, String type, String uid, String name,String floor,String reply_floor){
        methodInterface.toFloorCommentLike(type,uid,name,floor,reply_floor)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 查看右侧宝箱领取状态
     * @param subscriber
     * @param orderid
     */
    public void getTr(Subscriber<Tr> subscriber, String orderid){
        methodInterface.toTr(orderid)
                .map(new HttpResultFunc<Tr>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 查看右侧宝箱领取状态
     * @param subscriber
     * @param orderid
     */
    public void getGetTrRight(Subscriber<Statue> subscriber, String orderid, String channel){
        methodInterface.toGetRightTr(orderid,channel)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 查看左侧宝箱领取状态
     * @param subscriber
     * @param orderid
     */
    public void getDayTr(Subscriber<DayTr> subscriber, String orderid){
        methodInterface.toDayTr(orderid)
                .map(new HttpResultFunc<DayTr>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查看zuo侧宝箱领取状态
     * @param subscriber
     * @param orderid
     */
    public void getGetTrLeft(Subscriber<Statue> subscriber, String orderid, String channel){
        methodInterface.toGetLeftTr(orderid,channel)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 修改头像
     * @param subscriber
     * @param orderid
     */
    public void getUpdateImg(Subscriber<Statue> subscriber, String orderid, String img){
        methodInterface.toUpdateImg(orderid,img)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 添加积分
     * @param subscriber
     * @param orderid
     */
    public void getAddMoney(Subscriber<Statue> subscriber, String orderid){
        methodInterface.toAddMoney(orderid)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 下载更新
     * @param subscriber
     * @param orderid
     */
    public void getDownLoad(Subscriber<DownLoad> subscriber, String orderid){
        methodInterface.toDownLoad(orderid)
                .map(new HttpResultFunc<DownLoad>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 统计文章次数
     * @param subscriber
     * @param orderid
     * @param adtype
     */
    public void getTimes(Subscriber<Statue> subscriber, String orderid,String adtype){
        methodInterface.toTimes(orderid,adtype)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 文章 oooO ↘┏━┓ ↙ Oooo  ( 踩)→┃你┃ ←(死 )   \ ( →┃√┃ ← ) / 　 \_)↗┗━┛ ↖(_/
     * @param subscriber
     * @param type
     * @param uid
     */
    public void getDown(Subscriber<Statue> subscriber, String type,String uid){
        methodInterface.toDown(type,uid)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 文章 赞
     * @param subscriber
     * @param type
     * @param uid
     */
    public void getUp(Subscriber<Statue> subscriber, String type,String uid){
        methodInterface.toUp(type,uid)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 积分墙列表
     * @param subscriber
     */
    public void getTaskList(Subscriber<TaskList> subscriber){
        methodInterface.toTaskList()
                .map(new HttpResultFunc<TaskList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 修改密码
     * @param subscriber
     * @param tel
     * @param password
     * @param new_pwd
     */
    public void getUpdatePwd(Subscriber<Statue> subscriber,String tel,String password,String new_pwd){
        methodInterface.toUpdatePwd(tel,password,new_pwd)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 趣味才前十名
     * @param subscriber
     */
    public void getGuessList(Subscriber<GuessList> subscriber){
        methodInterface.toGuessList()
                .map(new HttpResultFunc<GuessList>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * bdid
     * @param subscriber
     */
    public void getBdid(Subscriber<BdId> subscriber){
        methodInterface.toBdId()
                .map(new HttpResultFunc<BdId>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * wx login
     * @param subscriber
     */
    public void getNewWx(Subscriber<PhoneLoginBean> subscriber, String unionid, String screen_name, String profile_image_url, String tel, String checknum, String smsMessageSid, String password){
        methodInterface.toNewWx(unionid,screen_name,profile_image_url,tel,checknum,smsMessageSid,password)
                .map(new HttpResultFunc<PhoneLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *  is wx
     * @param subscriber
     */
    public void getNewWxLogin(Subscriber<PhoneLoginBean> subscriber, String unionid, String screen_name, String profile_image_url){
        methodInterface.toNewWxLogin(unionid,screen_name,profile_image_url)
                .map(new HttpResultFunc<PhoneLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     *  tel bind wx
     * @param subscriber
     */
    public void getTelBindWx(Subscriber<Statue> subscriber, String unionid, String screen_name, String profile_image_url,String tel){
        methodInterface.toTelBindWx(unionid,screen_name,profile_image_url,tel)
                .map(new HttpResultFunc<Statue>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }







//    /**
//     * 获取超市分类
//     */
//    public void getGoods(Subscriber<Goods> subscriber, String loginId, String categoryId) {
//        String only = DateUtils.getDateTimeToOnly(System.currentTimeMillis());
//        methodInterface.getGoods(only, loginId, categoryId)
//                .map(new HttpResultFunc<Goods>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
}
