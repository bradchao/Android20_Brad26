package tw.org.iii.brad.brad26;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mesg;
    private MyService myService;
    private boolean isBind;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.v("brad", "connected");
            MyService.LocalBinder binder = (MyService.LocalBinder)iBinder;
            myService = binder.getService();
            isBind = true;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.v("brad", "die");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.v("brad", "null binding");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v("brad", "disconnect");
            isBind = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = findViewById(R.id.mesg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("brad", "onStart");
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("brad", "onStop");
        if (isBind){
            Log.v("brad", "disconnecting....");
            unbindService(mConnection);
        }
    }

    public void test1(View view) {
        if (isBind && myService != null){
            int lottery = myService.lottery();
            Log.v("brad", "lottery = " + lottery);
            mesg.setText("lottery = " + myService.i + ":" + lottery);
        }
    }

    public void test2(View view) {
        if (isBind) {
            unbindService(mConnection);
            isBind = false;
        }
    }

    public void test3(View view){
        if (mConnection == null){
            Log.v("brad", "null conn");
        }
        if (myService == null){
            Log.v("brad", "null service");
        }
        Log.v("brad", " ==> " + myService.isBind);

    }

}
