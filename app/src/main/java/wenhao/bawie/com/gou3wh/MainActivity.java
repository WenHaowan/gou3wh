package wenhao.bawie.com.gou3wh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import wenhao.bawie.com.gou3wh.adapter.CartAllCheckboxListener;
import wenhao.bawie.com.gou3wh.adapter.CartsAdapter;
import wenhao.bawie.com.gou3wh.bean.CartsBean;
import wenhao.bawie.com.gou3wh.mvp.presenter.CartsPresenter;
import wenhao.bawie.com.gou3wh.mvp.view.CartsView;

public class MainActivity extends AppCompatActivity implements CartsView,CartAllCheckboxListener{

    private RecyclerView recy_view;
    private String uid = "71";
    private CheckBox allCheckbox;
    private TextView totalPriceTv;
    private CartsAdapter cartsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recy_view = (RecyclerView) findViewById(R.id.recy_view);
        totalPriceTv = (TextView) findViewById(R.id.totalpriceTv);
        allCheckbox = (CheckBox) findViewById(R.id.allCheckbox);
        recy_view.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        CartsPresenter cartsPresenter = new CartsPresenter(this);
        cartsPresenter.getCarts(uid);
    }

    @Override
    public void success(final List<CartsBean.DataBean> data) {
        data.remove(0);
        cartsAdapter = new CartsAdapter(MainActivity.this, data);
        recy_view.setAdapter(cartsAdapter);
        cartsAdapter.notifyDataSetChanged();
        cartsAdapter.setCartAllCheckboxListener(this);
        //设置全选反选点击事件
        allCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allCheckbox.isChecked()){
                    if (data!=null&&data.size()>0){
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelected(true);
                            for (int j = 0; j < data.get(i).getList().size(); j++) {
                                data.get(i).getList().get(j).setSelected(true);
                            }
                        }
                    }
                }else {
                    if (data!=null&&data.size()>0){
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelected(false);
                            for (int j = 0; j < data.get(i).getList().size(); j++) {
                                data.get(i).getList().get(j).setSelected(false);
                            }
                        }
                    }
                }
                cartsAdapter.notifyDataSetChanged();//全部刷新
                totalPrice();
            }
        });
    }
    //价格
    private void totalPrice() {
        double totalPrice = 0;

        for (int i = 0; i < cartsAdapter.getCartList().size(); i++) {
            for (int j = 0; j < cartsAdapter.getCartList().get(i).getList().size(); j++) {
                //计算总价
                if (cartsAdapter.getCartList().get(i).getList().get(j).isSelected()){
                    CartsBean.DataBean.ListBean listBean = cartsAdapter.getCartList().get(i).getList().get(j);
                    totalPrice += listBean.getBargainPrice()*listBean.getTotalNum();
                }
            }
        }
        totalPriceTv.setText("合计：¥"+totalPrice);
    }

    @Override
    public void notifyAllCheckboxStatus() {
        StringBuilder stringBuilder = new StringBuilder();

        if (cartsAdapter!=null){
            for (int i = 0; i < cartsAdapter.getCartList().size(); i++) {
                stringBuilder.append(cartsAdapter.getCartList().get(i).isSelected());
                for (int j = 0; j < cartsAdapter.getCartList().get(i).getList().size(); j++) {
                    stringBuilder.append(cartsAdapter.getCartList().get(i).getList().get(j).isSelected());
                }
            }
        }

        if (stringBuilder.toString().contains("false")){
            allCheckbox.setChecked(false);
        }else {
            allCheckbox.setChecked(true);
        }
        totalPrice();//计算总价
    }
}
