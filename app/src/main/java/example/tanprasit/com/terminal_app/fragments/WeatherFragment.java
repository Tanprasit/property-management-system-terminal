package example.tanprasit.com.terminal_app.fragments;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.R;
import example.tanprasit.com.terminal_app.networks.URLBuilder;
import example.tanprasit.com.terminal_app.networks.WeatherBroadcastReceiver;
import example.tanprasit.com.terminal_app.services.GPSTracker;
import example.tanprasit.com.terminal_app.services.WeatherService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private WeatherBroadcastReceiver weatherReceiver;
    private boolean isHidden = false;
    private GPSTracker gpsTracker;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.gpsTracker = new GPSTracker(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GPSTracker gpsTracker = new GPSTracker(getActivity());
        String url = new URLBuilder().getWeatherUrl(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        Intent weatherIntent = new Intent(getActivity(), WeatherService.class);
        weatherIntent.putExtra(Constants.URL, url);
        getActivity().startService(weatherIntent);

        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register receiver
        IntentFilter intentFilter = new IntentFilter(Constants.WEATHER_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        this.weatherReceiver = new WeatherBroadcastReceiver();
        getActivity().registerReceiver(weatherReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.weatherReceiver);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // When notification screen switches out. Because we are using onHiddenChanged this will
        // be triggered twice, once on appearing and another when disappearing.
        if (isHidden) {
            if (isWeatherServiceRunning()) {
                String url = new URLBuilder().getWeatherUrl(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                Intent weatherIntent = new Intent(getActivity(), WeatherService.class);
                weatherIntent.putExtra(Constants.URL, url);
                getActivity().startService(weatherIntent);
            }
            this.isHidden = false;
        } else {
            this.isHidden = true;
        }
    }

    private boolean isWeatherServiceRunning() {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (WeatherService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
