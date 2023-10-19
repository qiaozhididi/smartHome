package iot.app.smarthome.ui.device;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import iot.app.smarthome.R;
import iot.app.smarthome.model.device.TempVo;

public class ThermometerActivity extends AppCompatActivity {

    private final static class TempValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float v) {
            return (int) v + "\u2103";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermometer);
        LineChart lineChart = findViewById(R.id.lineChart);
        List<Entry> list = new ArrayList<>();
        //其中两个数字对应的分别是 X轴 Y轴
//        list.add(new Entry(7.0F, 26F));
////        list.add(new Entry(8.0F, 29F));
////        list.add(new Entry(9.0F, 30F));
////        list.add(new Entry(10.0F, 31F));
////        list.add(new Entry(11.0F, 32F));

        //TODO： tempvo获取数据并使用litepal保存到sqlite，保存完毕之后在获取
//        LitePal.deleteAll(TempVo.class);
//        List<TempVo> tempVoList = new ArrayList<>();
//        for (Entry entry : list) {
//            // 将Entry的X轴值（时间）转换为整数并保存到tempVo的小时字段
//            float x = entry.getX();
//            int hour = Math.round(x);
//            TempVo tempVo = new TempVo();
//            tempVo.setDateHour(hour);
//
//            // 将Entry的Y轴值（温度）保存到tempVo的温度字段
//            float temperature = entry.getY();
//            tempVo.setDegree(temperature);
//            tempVoList.add(tempVo);
//        }
//        //循环环保存tempVoList中的数据
//        for (TempVo tempVo : tempVoList) {
//            tempVo.save();
//        }

        //从本地的sqlite数据库中获取数据
        List<TempVo> tempVoList = LitePal.findAll(TempVo.class);
        for (TempVo tempVo : tempVoList) {
            list.add(new Entry(tempVo.getDateHour(), tempVo.getDegree()));//将数据保存到list中，以便于绘制图表。
        }

        //设置图表的数据
        LineDataSet lineDataSet = new LineDataSet(list, "最近气温变化");
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new TempValueFormatter());
        lineChart.setData(lineData);
        //设置图表描述
        Description description = new Description();
        description.setText("最近气温变化");
        lineChart.setDescription(description);
        //获取 X 轴
        XAxis xAxis = lineChart.getXAxis();
        //设置 X 轴在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(list.size(), false);// 强制设置标签个数
        xAxis.setGranularity(1f);//设置X轴刻度间距

        //设置 X 轴的格式化显示内容
        xAxis.setValueFormatter(new ValueFormatter() {   //X轴自定义坐标
            @Override
            public String getFormattedValue(float v) {
                //TODO:请完善此处代码，返回时间格式 h:mm
                //将list里面的x值转化成时间
                double time = v;
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String formattedTime = decimalFormat.format(time);
                String[] parts = formattedTime.split("\\.");
                //将时间转化成字符串
                int hours = Integer.parseInt(parts[0]);
                int min = Integer.parseInt(parts[1]);
                String minutes = String.format("%02d", min);
                String timeStr = hours + ":" + minutes;
                return timeStr;

            }
        });
        //获取左 Y 轴
        YAxis yLeftAxis = lineChart.getAxisLeft();
        //获取左 Y 轴格式化显示内容
        yLeftAxis.setValueFormatter(new TempValueFormatter());
        yLeftAxis.setAxisMinimum(23f);//设置最小值
        yLeftAxis.setGranularity(2f);//设置Y轴刻度间距
        yLeftAxis.setLabelCount(list.size(), false); // 强制设置标签个数
        //获取右边的Y轴
        YAxis yRightAxis = lineChart.getAxisRight();
        yRightAxis.setEnabled(true);
        yRightAxis.setValueFormatter(new TempValueFormatter());
        yRightAxis.setAxisMinimum(23f);//设置最小值
        yRightAxis.setGranularity(2f);//设置Y轴刻度间距
        yRightAxis.setLabelCount(list.size(), false); // 强制设置标签个数

    }
}