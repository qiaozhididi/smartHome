package iot.app.smarthome.model.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static File saveAvatar(Bitmap bitmap,String fileName){
        File avaterFile = new File("/data/data/iot.app.smarthome/avatar", fileName);
        if(!avaterFile.getParentFile().exists()){
            avaterFile.getParentFile().mkdirs();
        }
        if(avaterFile.exists()){
            avaterFile.delete();
        }
        try {
            avaterFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(avaterFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.d("FileUtil","保存图片到"+avaterFile.getAbsoluteFile());
        } catch (IOException e) {
            Log.e("FileUtil",e.getMessage());
        }
        return avaterFile;
    }

    public static Bitmap getAvatar(String path){
        Bitmap bitmap = null;
        try{
            File avaterFile = new File(path);
            if(avaterFile.exists()) {
                bitmap = BitmapFactory.decodeFile(avaterFile.getAbsolutePath());
            }
        } catch (Exception e) {
            Log.e("FileUtil",e.getMessage());
        }
        return bitmap;
    }
}

