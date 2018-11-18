package wenhao.bawie.com.gou3wh.mvp.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import wenhao.bawie.com.gou3wh.bean.CartsBean;

/**
 * 原创：温浩
 * 2018/11/17
 */

public interface Api {
    @GET("product/getCarts")
    Observable<CartsBean> getshow(@Query("uid") String uid);
}
