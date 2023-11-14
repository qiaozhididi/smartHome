package iot.app.smarthome.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String CREATE_CUR_USER="create table curuser ("
            +"userid text,username text,avatar text)";

    public final static String DBNAME="UserInfo.db";

    public static interface TABLE{
        String CURUSER="curuser";
    }
    private Context mContext;

    private static SQLiteHelper instance;

    public static SQLiteHelper getInstance(@Nullable Context context){
        if(instance==null){
            instance=new SQLiteHelper(context,DBNAME,null,1);
        }
        return instance;
    }

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CUR_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
