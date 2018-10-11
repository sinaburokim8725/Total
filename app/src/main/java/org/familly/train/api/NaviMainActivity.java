package org.familly.train.api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.familly.train.api.db.Db01Activity;

public class NaviMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_main);

        button_db1 = (Button) findViewById(R.id.button_db1);
        button_db1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_db1:

                Intent intent = new Intent(this , Db01Activity.class);
                startActivity(intent);

                break;
            default:
        }
    }
}
