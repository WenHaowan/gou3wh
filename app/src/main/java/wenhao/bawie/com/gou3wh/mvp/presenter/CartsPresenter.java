package wenhao.bawie.com.gou3wh.mvp.presenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wenhao.bawie.com.gou3wh.bean.CartsBean;
import wenhao.bawie.com.gou3wh.mvp.model.HttpUtils;
import wenhao.bawie.com.gou3wh.mvp.view.CartsView;

/**
 * 原创：温浩
 * 2018/11/17
 */

public class CartsPresenter implements IPresenter{
    private CartsView cartsView;

    public CartsPresenter(CartsView cartsView) {
        this.cartsView = cartsView;
    }

    public void getCarts(String uid){
        Observable<CartsBean> getshow = HttpUtils.getUtilsInstance().api.getshow(uid);
        getshow.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CartsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CartsBean cartsBean) {
                        cartsView.success(cartsBean.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        if (cartsView!=null){
            cartsView=null;
        }
    }
}
