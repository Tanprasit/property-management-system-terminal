package example.tanprasit.com.terminal_app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.R;
import example.tanprasit.com.terminal_app.activities.MainActivity;
import example.tanprasit.com.terminal_app.models.Device;
import example.tanprasit.com.terminal_app.models.Notification;
import example.tanprasit.com.terminal_app.network.URLBuilder;
import example.tanprasit.com.terminal_app.network.VollySingleton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Device device;

    private OnFragmentInteractionListener mListener;

    private WebView mWebView;

    private final Gson gson = new Gson();

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void displayNotification(final WebView webView, Notification notification) {

        // Display the first 500 characters of the response string.
        webView.loadDataWithBaseURL(null, notification.getData(), "text/html", "UTF-8", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mWebView = (WebView) view.findViewById(R.id.fragment_web_webview);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);

        String deviceString = sharedPreferences.getString(Constants.DEVICE_OBJECT, null);

        if (null != deviceString) {
            this.device = gson.fromJson(deviceString, Device.class);
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        List<Notification> notificationList = this.device.getNotificationsList();

        if (notificationList.size() > 0) {
            updateDeviceNotifications(device);
            displayNotification(this.mWebView, notificationList.get(0));
        } else {
            Toast.makeText(getActivity(), "No notifications to show", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void updateDeviceNotifications(Device device) {
        URLBuilder urlBuilder = new URLBuilder();
        String url = urlBuilder.getDeviceUrl(device.getId());

        // Instantiate the RequestQueue.
        RequestQueue queue = VollySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.clear();
                        editor.putString(Constants.DEVICE_OBJECT, response);
                        editor.apply();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error status code ", error.getMessage());
                Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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
}
