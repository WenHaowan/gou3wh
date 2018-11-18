package wenhao.bawie.com.gou3wh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import wenhao.bawie.com.gou3wh.R;
import wenhao.bawie.com.gou3wh.bean.CartsBean;
import wenhao.bawie.com.gou3wh.widget.MyJiaJianView;

/**
 * 原创：温浩
 * 2018/11/17
 */

public class CartsListAdapter extends RecyclerView.Adapter<CartsListAdapter.ListViewHolder>{
    private Context context;
    private List<CartsBean.DataBean.ListBean> list;
    private CartCheckListener cartCheckListener;

    public CartsListAdapter(Context context, List<CartsBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CartsListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.carts_list_adapter_layout, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(inflate);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartsListAdapter.ListViewHolder holder, final int position) {
        holder.price.setText("优惠价："+list.get(position).getBargainPrice());
        holder.title.setText(list.get(position).getTitle());
        String[] split = list.get(position).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.product_icon);

        holder.jiajianqi.setNumEt(list.get(position).getTotalNum());

        holder.checkBox.setChecked(list.get(position).isSelected());
        //加减器
        holder.jiajianqi.setJiaJianListener(new MyJiaJianView.JiaJianListener() {
            @Override
            public void getNum(int num) {
                list.get(position).setTotalNum(num);
                if (cartCheckListener!=null){
                    cartCheckListener.notifyParent();//通知一级列表适配器刷新
                }
            }
        });
        //子条目全选反选
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){//选中
                    list.get(position).setSelected(true);
                }else {//非选中
                    list.get(position).setSelected(false);
                }
                if (cartCheckListener!=null){
                    cartCheckListener.notifyParent();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setCheckListener(CartCheckListener cartCheckListener) {
        this.cartCheckListener = cartCheckListener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private MyJiaJianView jiajianqi;
        private CheckBox checkBox;
        private ImageView product_icon;
        private TextView price,title;

        public ListViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            title = (TextView) itemView.findViewById(R.id.title);
            product_icon = (ImageView) itemView.findViewById(R.id.product_icon);
            checkBox = (CheckBox) itemView.findViewById(R.id.productCheckbox);
            jiajianqi = (MyJiaJianView) itemView.findViewById(R.id.jiajianqi);
        }
    }
}
