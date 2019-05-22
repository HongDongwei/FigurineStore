package com.codvision.figurinestore.module.api;


import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGetById;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.module.bean.UserLogin;
import com.codvision.figurinestore.module.bean.UserSubmit;


import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sxy on 2019/5/8 13:48
 * todo
 */
public interface AllApi {

    /**
     * 登录
     */
    @POST(ApiAddress.USER_LOGIN)
    Observable<WrapperEntity<UserLogin>> userLogin(@Body Map<String, String> maps);

    /**
     * 注册
     */
    @POST(ApiAddress.USER_REGISTER)
    Observable<WrapperEntity<InsertId>> userRegister(@Body Map<String, String> maps);

    /**
     * 提交
     */
    @POST(ApiAddress.USER_SUBMIT)
    Observable<WrapperEntity<InsertId>> userSubmit(@Body UserSubmit userSubmit);

    /**
     * 获取商品
     */
    @POST(ApiAddress.COMMODITY_SELECT)
    Observable<WrapperEntity<ArrayList<Commodity>>> getCommodity(@Body CommodityGet commodityGet);
    /**
     * 获取商品
     */
    @POST(ApiAddress.COMMODITY_SELECT_BY_ID)
    Observable<WrapperEntity<Commodity>> getCommodityById(@Body CommodityGetById commodityGetById);
}
