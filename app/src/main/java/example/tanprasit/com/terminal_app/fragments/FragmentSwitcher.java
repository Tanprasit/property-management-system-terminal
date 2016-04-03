package example.tanprasit.com.terminal_app.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.CountDownTimer;

import example.tanprasit.com.terminal_app.R;

/**
 * Created by luketanprasit on 15/03/2016.
 */
public class FragmentSwitcher extends CountDownTimer {

    private int fragmentId;
    private Activity activity;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public FragmentSwitcher(long millisInFuture, long countDownInterval, int fragmentId, Context context) {
        super(millisInFuture, countDownInterval);
        this.fragmentId = fragmentId;
        this.activity = (Activity) context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
    }

    @Override
    public void onFinish() {
        FragmentManager fragmentManager = this.activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int totalFragmentCount = FragmentList.getSize();

        switch (fragmentId) {
            case 1:
                fragmentTransaction.show(fragmentManager.findFragmentById(R.id.fragment_web));
                fragmentTransaction.hide(fragmentManager.findFragmentById(R.id.fragment_weather));
                break;
            case 2:
                fragmentTransaction.hide(fragmentManager.findFragmentById(R.id.fragment_web));
                fragmentTransaction.show(fragmentManager.findFragmentById(R.id.fragment_weather));
                break;
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // If activity was already destroyed cancel the switch.
        if (this.activity.isDestroyed()) {
            this.cancel();
        } else {
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            if (fragmentId == totalFragmentCount) {
                fragmentId = 1;
            } else {
                fragmentId++;
            }

            new FragmentSwitcher(20000, 1000, fragmentId, activity).start();
        }
    }
}
