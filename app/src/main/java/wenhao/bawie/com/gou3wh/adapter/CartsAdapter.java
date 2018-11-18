package wenhao.bawie.com.gou3wh.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import wenhao.bawie.com.gou3wh.R;
import wenhao.bawie.com.gou3wh.bean.CartsBean;

/**
 * 原创：温浩
 * 2018/11/17
 */

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.CartsViewHolder> implements CartCheckListener{
    private Context context;
    private List<CartsBean.DataBean> list;
    private CartAllCheckboxListener cartAllCheckboxListener;

    public void setCartAllCheckboxListener(CartAllCheckboxListener cartAllCheckboxListener) {
        this.cartAllCheckboxListener = cartAllCheckboxListener;
    }

    public CartsAdapter(Context context, List<CartsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CartsAdapter.CartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.carts_adapter_layout, parent, false);
        CartsViewHolder cartsViewHolder = new CartsViewHolder(inflate);
        return cartsViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartsAdapter.CartsViewHolder holder, final int position) {
        holder.show_name.setText(list.get(position).getSellerName());
        holder.checkBox.setChecked(list.get(position).isSelected());
        holder.recy_view_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        CartsListAdapter cartsListAdapter = new CartsListAdapter(context, list.get(position).getList());
        holder.recy_view_list.setAdapter(cartsListAdapter);

        cartsListAdapter.setCheckListener(this);
        //设置商家的checkbox点击事件，逻辑：勾选则子列表全部勾选，取消则全部取消
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    list.get(position).setSelected(true);
                    for (int i = 0; i < list.get(position).getList().size(); i++) {
                        list.get(position).getList().get(i).setSelected(true);
                    }
                }else {
                    list.get(position).setSelected(false);
                    for (int i = 0; i < list.get(position).getList().size(); i++) {
                        list.get(position).getList().get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
                if (cartAllCheckboxListener!=null){
                    cartAllCheckboxListener.notifyAllCheckboxStatus();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<CartsBean.DataBean> getCartList(){
        return list;
    }

    @Override
    public void notifyParent() {
        notifyDataSetChanged();
        if (cartAllCheckboxListener!=null){
            cartAllCheckboxListener.notifyAllCheckboxStatus();
        }
    }

    public class CartsViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private RecyclerView recy_view_list;
        private TextView show_name;

        public CartsViewHolder(View itemView) {
            super(itemView);
            show_name = (TextView) itemView.findViewById(R.id.show_name);
            recy_view_list = (RecyclerView) itemView.findViewById(R.id.recy_view_list);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
        }
    }
}
