package example.tanprasit.com.terminal_app.fragments;

import android.app.Fragment;

import java.util.ArrayList;

/**
 * Created by luketanprasit on 15/03/2016.
 */
public class FragmentList {
    public static ArrayList<Fragment> list = new ArrayList<>();

    public static void addFragment(Fragment fragment) {
        list.add(fragment);
    }

    public static int getSize() {
        return list.size();
    }
}
