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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import iot.app.smarthome.R;

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
        list.add(new Entry(7.0F, 26F));
        list.add(new Entry(8.0F, 29F));
        list.add(new Entry(9.0F, 30F));
        list.add(new Entry(10.0F, 31F));
        list.add(new Entry(11.0F, 32F));

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm");
                LocalTime time = LocalTime.ofNanoOfDay((long) (v * 1000000000L));
                return formatter.format(time);
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