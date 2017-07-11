package com.dophin.weichat_article.http;

import com.dophin.weichat_article.comment.bean.HotTalk;
import com.dophin.weichat_article.home.bean.BdId;
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

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by caiguoqing on 2017/2/21.
 */

public interface MethodInterface {

    /**
     * 电话登录接口
     */
    @GET("userController/login_tel")
    Observable<HttpResult<PhoneLoginBean>> toPhoneLogin(@Query("tel") String tel, @Query("password") String password);

    /**
     * yun平台验证码
     */
    @GET("rlController/sendMeg")
    Observable<HttpResult<YunCode>> toYunCode(@Query("tel") String tel);

    /**
     * 手机注册
     */
    @GET("userController/saveUser_tel")
    Observable<HttpResult<PhoneLoginBean>> toPhoneRegist(@Query("tel") String tel, @Query("password") String password, @Query("yqm") String yqm, @Query("smsMessageSid") String smsMessageSid, @Query("checknum") String checknum);

    /**
     * 微信登录
     */
    @GET("userController/saveUser_Wxin")
    Observable<HttpResult<PhoneLoginBean>> toWxLogin(@Query("unionid") String unionid, @Query("screen_name") String screen_name,@Query("profile_image_url") String profile_image_url);

    /**
     * 忘记密码 userController/find_pwd
     */
    @GET("userController/find_pwd")
    Observable<HttpResult<PhoneLoginBean>> toForgetPwd(@Query("tel") String tel, @Query("password") String password, @Query("checknum") String checknum, @Query("smsMessageSid") String smsMessageSid);

    /**
     * 首页推荐
     */
    @GET("htmlController/findBzyHtml_xgtj")
    Observable<HttpResult<News>> toNewsRcommend(@Query("page") String page, @Query("name") String name);

    /**
     * 分类新闻
     */
    @GET("htmlController/findBzyHtml")
    Observable<HttpResult<News>> toNewsClassify(@Query("page") String page, @Query("type") String type);

    /**
     * 热议
     */
    @GET("htmlController/find_reyi")
    Observable<HttpResult<HotTalk>> toNewsHotTalk(@Query("page") String page);

    /**
     * 积分列表
     */
    @GET("pointController/find_points")
    Observable<HttpResult<IncomeList>> toIncomeLeft(@Query("orderid") String orderid, @Query("page") String page);

    /**
     * 现金列表
     */
    @GET("pointController/find_zhuanxi")
    Observable<HttpResult<IncomeList>> toIncomeRight(@Query("orderid") String orderid, @Query("page") String page);

    /**
     * 趣味才投注
     */
    @GET("interestController/touZhu")
    Observable<HttpResult<Statue>> toGuess(@Query("orderid") String orderid, @Query("intid") String intid,@Query("count_zheng") String count_zheng,@Query("count_fan") String count_fan);

    /**
     * 趣味才偷看结果
     */
    @GET("interestController/see")
    Observable<HttpResult<GuessResult>> toGuessResute(@Query("orderid") String orderid, @Query("intid") String intid);

    /**
     * 趣味才偷看结果
     */
    @GET("interestController/result")
    Observable<HttpResult<GuessYesResult>> toGuessYesResute(@Query("orderid") String orderid);

    /**
     * 趣味才今日话题
     */
    @GET("interestController/find")
    Observable<HttpResult<GuessToday>> toGuessToday(@Query("orderid") String orderid);

    /**
     * 光荣榜排行
     */
    @GET("paihController/find_paiming")
    Observable<HttpResult<RankList>> toRank(@Query("type") String type);

    /**
     * 下线列表
     */
    @GET("userController/find_line")
    Observable<HttpResult<OffLineList>> toOffLin(@Query("userid") String userid,@Query("page") String page,@Query("type") String type,@Query("orderid") String orderid );

    /**
     * 勋章列表
     */
    @GET("xzController/find_xz")
    Observable<HttpResult<Honor>> toHonor(@Query("orderid") String orderid);

    /**
     * 勋章领取
     */
    @GET("xzController/lq_xunz")
    Observable<HttpResult<Statue>> toGetHonor(@Query("orderid") String orderid,@Query("channel") String channel);

    /**
     * 提现
     */
    @GET("pointController/recharge")
    Observable<HttpResult<Statue>> toExchange(@Query("orderid") String orderid,@Query("type") String type,@Query("money") String money,@Query("account") String account);


    /**
     * 提现列表
     */
    @GET("pointController/find_exchange")
    Observable<HttpResult<ExchangeList>> toExchangeList(@Query("orderid") String orderid);

    /**
     * 红包
     */
    @GET("pointController/red")
    Observable<HttpResult<RedPackage>> toRedPackage(@Query("orderid") String orderid,@Query("score") String score,@Query("time") String time);


    /**
     * 用户信息
     */
    @GET("userController/userInfo")
    Observable<HttpResult<UserInfo>> toUserInfo(@Query("orderid") String orderid);

    /**
     * 修改用户信息
     */
    @GET("userController/updateInf")
    Observable<HttpResult<Statue>> toUpdateUserInfo(@Query("orderid") String orderid,@Query("istel") String istel,@Query("checknum") String checknum,@Query("smsMessageSid") String smsMessageSid,@Query("nickname") String nickname,@Query("tel") String tel,@Query("alipay_name") String alipay_name,@Query("alipay") String alipay,@Query("qq") String qq);


    /**
     * 查看文章评论
     */
    @GET("htmlController/find_reply")
    Observable<HttpResult<CommentList>> toCommentList(@Query("type") String type,@Query("uid") String uid,@Query("page") String page);


    /**
     *评论
     */
    @GET("htmlController/add_reply")
    Observable<HttpResult<Statue>> toComment(@Query("type") String type,@Query("uid") String uid,@Query("name") String name,@Query("nickname") String nickname,@Query("img") String img,@Query("content") String content,@Query("floor") String floor);

    /**
     * 查看楼中楼评论
     */
    @GET("htmlController/find_replyfloor")
    Observable<HttpResult<FloorCommentList>> toFloorCommentList(@Query("type") String type,@Query("uid") String uid,@Query("page") String page,@Query("floor") String floor);

    /**
     *楼中楼评论
     */
    @GET("htmlController/add_replyfloor")
    Observable<HttpResult<Statue>> toFloorComment(@Query("type") String type,@Query("uid") String uid,@Query("name") String name,@Query("nickname") String nickname,@Query("img") String img,@Query("content") String content,@Query("floor") String floor,@Query("reply_name") String reply_name,@Query("reply_nickname") String reply_nickname,@Query("reply_img") String reply_img,@Query("reply_floor") String reply_floor);

    /**
     * 评论点赞
     */
    @GET("htmlController/add_replyzan")
    Observable<HttpResult<Statue>> toCommentLike(@Query("type") String type,@Query("uid") String uid,@Query("name") String name,@Query("floor") String floor);


    /**
     * 楼中楼点赞
     */
    @GET("htmlController/add_replyfloorzan")
    Observable<HttpResult<Statue>> toFloorCommentLike(@Query("type") String type,@Query("uid") String uid,@Query("name") String name,@Query("floor") String floor,@Query("reply_floor") String reply_floor);

    /**
     * 宝箱右侧
     */
    @GET("baoxController/find_bx")
    Observable<HttpResult<Tr>> toTr(@Query("orderid") String orderid);

    /**
     * 宝箱右侧领取 channel
     */
    @GET("baoxController/lq_baox")
    Observable<HttpResult<Statue>> toGetRightTr(@Query("orderid") String orderid,@Query("channel") String channel);


    /**
     * 宝箱左侧
     */
    @GET("baoxController/find_bxday")
    Observable<HttpResult<DayTr>> toDayTr(@Query("orderid") String orderid);

    /**
     * 修改头像
     */
    @POST("userController/update_img")
    Observable<HttpResult<Statue>> toUpdateImg(@Query("orderid") String orderid,@Query("img") String img);


    /**
     * 宝箱右侧领取 channel
     */
    @GET("baoxController/lq_baox_day")
    Observable<HttpResult<Statue>> toGetLeftTr(@Query("orderid") String orderid,@Query("channel") String channel);


    /**
     * add money
     */
    @POST("pointController/addPoints")
    Observable<HttpResult<Statue>> toAddMoney(@Query("orderid") String orderid);


    /**
     * 下载更新
     */
    @GET("userController/apk_update")
    Observable<HttpResult<DownLoad>> toDownLoad(@Query("orderid") String orderid);

    /**
     * 文章统计
     * @param orderid
     * @return
     */
    @GET("htmlController/tongj_BzyHtml")
    Observable<HttpResult<Statue>> toTimes(@Query("orderid") String orderid, @Query("adtype") String adtype);


    /**
     *  oooO ↘┏━┓ ↙ Oooo  ( 踩)→┃你┃ ←(死 )   \ ( →┃√┃ ← ) / 　 \_)↗┗━┛ ↖(_/
     * @param type
     * @param uid
     * @return
     */
    @GET("htmlController/add_updcai")
    Observable<HttpResult<Statue>> toDown(@Query("type") String type, @Query("uid") String uid);


    /**
     *  oooO ↘┏━┓ ↙ Oooo  ( 踩)→┃你┃ ←(死 )   \ ( →┃√┃ ← ) / 　 \_)↗┗━┛ ↖(_/
     * @param type
     * @param uid
     * @return
     */
    @GET("htmlController/add_updzan")
    Observable<HttpResult<Statue>> toUp(@Query("type") String type, @Query("uid") String uid);


    /**
     * 积分墙列表
     * @return
     */
    @GET("pointController/lmad")
    Observable<HttpResult<TaskList>> toTaskList();

    /**
     * 趣味才前十名
     * @return
     */
    @GET("interestController/result_md")
    Observable<HttpResult<GuessList>> toGuessList();

    /**
     * 修改密码
     * @return
     */
    @GET("userController/update_pwd")
    Observable<HttpResult<Statue>> toUpdatePwd(@Query("tel") String tel, @Query("password") String password,@Query("new_pwd") String new_pwd);


    /**
     * bdid
     * @return
     */
    @GET("bdidsController/bdid")
    Observable<HttpResult<BdId>> toBdId();


    /**
     *  login or regist of new version
     * @return
     */
    @GET("userController/is_register_wx")
    Observable<HttpResult<PhoneLoginBean>> toNewWxLogin(@Query("unionid") String unionid, @Query("screen_name") String screen_name,@Query("profile_image_url") String profile_image_url);


    /**
     *  regist wx
     */
    @GET("userController/saveUser_Wxin")
    Observable<HttpResult<PhoneLoginBean>> toNewWx(@Query("unionid") String unionid, @Query("screen_name") String screen_name,@Query("profile_image_url") String profile_image_url,@Query("tel") String tel,@Query("checknum") String checknum,@Query("smsMessageSid") String smsMessageSid,@Query("password") String password);

    /**
     *  tel bind wx
     * @return
     */
    @GET("userController/tel_bd_wx")
    Observable<HttpResult<Statue>> toTelBindWx(@Query("unionid") String unionid, @Query("screen_name") String screen_name,@Query("profile_image_url") String profile_image_url,@Query("tel") String tel);

}
