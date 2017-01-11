package com.phyder.paalan.activity.organization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.phyder.paalan.R;

/**
 * Created by Yashica on 5/1/17.
 * Company PhyderCmss
 */
public class OrgLoginActivity extends AppCompatActivity {

    TextView txtORGLOGIN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtORGLOGIN = (TextView) findViewById(R.id.txt_org_login);
        txtORGLOGIN.setVisibility(View.GONE);
    }
}
