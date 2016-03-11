package example.tanprasit.com.terminal_app.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import example.tanprasit.com.terminal_app.R;
import example.tanprasit.com.terminal_app.fragments.WebFragment;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WebFragment webFragment = new WebFragment();

        fragmentTransaction.add(R.id.fragment_web, webFragment);

        fragmentTransaction.commit();

    }
}
