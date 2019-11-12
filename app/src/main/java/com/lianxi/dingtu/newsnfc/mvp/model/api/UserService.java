package com.lianxi.dingtu.newsnfc.mvp.model.api;

import com.lianxi.dingtu.newsnfc.mvp.model.entity.AggregateTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.BaseResponse;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.CardTypeTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepartmentTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.DepositTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseReportTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetDetailList;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.GetEMGoods;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.KeySwitchTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.MoneyParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRDepositParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.QRReadTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.ReadCardTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.RegisterParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseParam;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SimpleExpenseTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.SubsidyTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.UserInfoTo;
import com.lianxi.dingtu.newsnfc.mvp.model.entity.VersionTo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("Api/Token/GetToken") Observable<BaseResponse<UserInfoTo>> login(@Query("account") String account, @Query("password") String password, @Query("oldAccessToken") String oldAccessToken);

    @GET("Api/Token/GetToken") Call<BaseResponse<UserInfoTo>> synclogin(@Query("account") String account, @Query("password") String password, @Query("oldAccessToken") String oldAccessToken);

    @GET("Api/VersionRepository/Highest") Call<BaseResponse<VersionTo>> syncUpdate(@Query("operatingSystem") String operatingSystem,@Query("type") String type,@Query("extensionName") String extensionName);

    @GET("Api/Config/GetCardPassword") Observable<BaseResponse<String>> getCardPassword();

    @GET("Api/User/GetByNumber") Observable<BaseResponse<CardInfoTo>> getByNumber(@Query("number") int number);

    @POST("Api/Expense/SimpleExpense") Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(@Body SimpleExpenseParam param);

    @GET("Api/Config/GetConfig") Observable<BaseResponse<String>> getPayKeySwitch(@Query("key") String key);

    @GET("Api/ReportCenter/Deposit/Paging") Observable<BaseResponse<DepositTo>> getDepositReport(@Query("pager.pageIndex") int pageIndex, @Query("pager.pageSize") int pageSize, @Query("pager.orderColumn") String orderColumn, @Query("pager.orderPattern") String orderPattern);

    @GET("Api/ReportCenter/Expense/Paging") Observable<BaseResponse<ExpenseTo>> getExpenseReport(@Query("pager.pageIndex") int pageIndex, @Query("pager.pageSize") int pageSize, @Query("pager.orderColumn") String orderColumn, @Query("pager.orderPattern") String orderPattern);

    @GET("Api/Subsidy/SubsidyLeve/GetList") Observable<BaseResponse<List<SubsidyTo>>> getSubsidyLeve();

    @GET("Api/CardType/GetList") Observable<BaseResponse<List<CardTypeTo>>> getCardType();

    @GET("Api/Department/GetList") Observable<BaseResponse<List<DepartmentTo>>> getDepartment();

    @GET("Api/MoneyExchange/Donate/CalculateDonateAmount") Observable<BaseResponse<Double>> getDonate(@Query("cardType") int cardType, @Query("amount") double amount);

    @GET("Api/User/Card/GetNextNumber") Observable<BaseResponse<Integer>> getNextNumber();

    @POST("Api/User/Register") Observable<BaseResponse> addRegister(@Body RegisterParam param);

    @POST("Api/MoneyExchange/Deposit") Observable<BaseResponse> addDeposit(@Body MoneyParam param);

    @POST("Api/MoneyExchange/Refund") Observable<BaseResponse> addRefund(@Body MoneyParam param);

    @POST("Api/User/Deregister") Observable<BaseResponse> addDeregister(@Body MoneyParam param);

    @GET("Api/User/Card/Read") Observable<BaseResponse<ReadCardTo>> addReadCard(@Query("companyCode") int companyCode, @Query("deviceID") int deviceID, @Query("number") int number);

    @GET("Api/User/Card/ObtainByNumber") Observable<BaseResponse> addObtainByNumber(@Query("number") int number);

    @GET("Api/ReportCenter/AggregateDepositAndExpenseReport") Observable<BaseResponse<List<AggregateTo>>> getAggregateTo(@Query("startTime") String startTime, @Query("endTime") String endTime);

    @GET("Api/User/QRCode/QRRead") Observable<BaseResponse<QRReadTo>> getQRRead(@Query("qrCodeContent") String qrCodeContent, @Query("deviceID") int deviceID);

    @POST("Api/Expense/QRExpense") Observable<BaseResponse<QRExpenseTo>> addQRExpense(@Body QRExpenseParam param);

    @POST("Api/MoneyExchange/ThirdParty/QRDeposit") Observable<BaseResponse> getQRDeposit(@Body QRDepositParam param);

    @GET("Api/Device/Get") Observable<BaseResponse<KeySwitchTo>> getEMDevice(@Query("id") int id);

    @GET("Api/EMGoodsType/GetList") Observable<GetEMGoods> getEMGoods(@Query("state") String state);

    @GET("Api/EMGoods/GetPage") Observable<GetDetailList> getEMGoodsDetail(@Query ("index") Integer index,@Query ("count") Integer count,@Query ("goodsType") String goodsType);
}
