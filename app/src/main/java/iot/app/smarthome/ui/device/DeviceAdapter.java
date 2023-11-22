package iot.app.smarthome.ui.device;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iot.app.smarthome.R;
import iot.app.smarthome.model.device.DeviceListVo;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder>{

    private List<DeviceListVo> mDeviceList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View devView;
        ImageView devImg;
        TextView devName;
        TextView devDesc;

        public ViewHolder(View view) {
            super(view);
            devView = view;
            devImg = (ImageView) view.findViewById(R.id.deviceImg);
            devName = (TextView) view.findViewById(R.id.deviceName);
            devDesc = (TextView) view.findViewById(R.id.deviceDesc);
        }
    }

    public DeviceAdapter(List<DeviceListVo> deviceList) {
        mDeviceList = deviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.devView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检测到点击到第几个设备
                int position = holder.getAdapterPosition();
                //获取到设备信息
                DeviceListVo dev = mDeviceList.get(position);
                //TODO:实训4.2 跳转到相应的设备控制界面
                //点击设备同时把IoTID传给LightBulbActivity
                Intent intent = new Intent(v.getContext(), LightBulbActivity.class);
                intent.putExtra("iotid", dev.getIotId());
                v.getContext().startActivity(intent);

            }
        });
        holder.devImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DeviceListVo dev = mDeviceList.get(position);
                Toast.makeText(v.getContext(), "you clicked image " + dev.getDevName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeviceListVo device = mDeviceList.get(position);
        if(DeviceListVo.DEV_TYPE_LOCK.equals(device.getDevType())){
            holder.devImg.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.lock,null));
        }else if(DeviceListVo.DEV_TYPE_THERMOMETER.equals(device.getDevType())){
            holder.devImg.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.thermometer,null));
        }else if(DeviceListVo.DEV_TYPE_BULB.equals(device.getDevType())){
            holder.devImg.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.lightbulb,null));
        }
        holder.devName.setText(device.getDevName());
        holder.devDesc.setText(device.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

}