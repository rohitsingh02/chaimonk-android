package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(MainActivity.this,"You cant clear data of this app",Toast.LENGTH_LONG).show();
        finish();


    }

}
