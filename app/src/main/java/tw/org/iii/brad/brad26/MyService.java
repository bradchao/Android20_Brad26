package tw.org.iii.brad.brad26;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private final Binder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int i = 0;

    public int lottery(){
        i++;
        try{
            Thread.sleep(1*3000);
        }catch (Exception e){}
        return (int)(Math.random()*49+1);
    }

}
