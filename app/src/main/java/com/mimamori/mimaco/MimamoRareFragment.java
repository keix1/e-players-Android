package com.mimamori.mimaco;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import otoshimono.com.lost.mamorio.sdk.Error;
import otoshimono.com.lost.mamorio.sdk.Mamorio;
import otoshimono.com.lost.mamorio.sdk.MamorioSDK;
import otoshimono.com.lost.mamorio.sdk.PeripheralHistory;
import otoshimono.com.lost.mamorio.sdk.User;


public class MimamoRareFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = MimamoRareFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private KEYS keys = new KEYS();

    private String APP_TOKEN = "APP_TOKEN";
    private PointManager pointManager = new PointManager();
    private String MY_USERNAME = "grandpa";

    private View rootView;
    private GoogleMap mMap;
    private MapView mapView;

    public MimamoRareFragment() {
        // Required empty public constructor

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("LifeCycle", "onStart");

        // ACCESS_FINE_LOCATIONの許可(Android 6.0以上向け）
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            MamorioSDK.setUp(getActivity().getBaseContext(), APP_TOKEN); // 探索専用
            step1();
        }

        try {
//            Log.d("resultJSON:", pointManager.getAllUser().getJSONObject(0).getString("email"));
//            Log.d(TAG, pointManager.addPoint(String.valueOf(1), 1).getString("username"));
//            Log.d("getUser", pointManager.getUser(2).getString("username"));
//            Log.d(TAG, pointManager.findWatchedUser("1", 3333, 4444 ).getString("username"));
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MimamorioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MimamoRareFragment newInstance(String param1, String param2) {
        MimamoRareFragment fragment = new MimamoRareFragment();
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
    }


    void step1()
    {
        Log.d(TAG,"step1()");

        User.signIn(keys.getUser(), keys.getPassword(), new User.UserCallback() {
            @Override
            public void onSuccess(User user) {
                //アカウント登録成功時の処理
                Log.d(TAG,"ユーザーのサインインに成功しました");
                step2();
            }

            @Override
            public void onError(Error error) {
                //アカウント登録失敗時の処理
                Log.d(TAG,"ユーザー登録に失敗しました。エラーメッセージ：" + error.getMessage());
            }
        });

    }

    void step2()
    {
        Log.d(TAG,"step2()");

        MamorioSDK.getCurrentUser().refreshMamorios(
                new User.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("REFRESH","リフレッシュに成功しました");
                        for(Mamorio mamorio : MamorioSDK.getCurrentUser().getMamorios()) {
                            Log.d("REFRESH", mamorio.getName());

                        }

                    }

                    @Override
                    public void onError(Error error) {
                        Log.d("REFRESH","mamorioの情報の更新に失敗しました。エラーメッセージ：" + error.getMessage());
                    }
                }
        );
        MamorioSDK.rangingStart(
                new MamorioSDK.RangingInitializeCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG,"レンジングの開始に成功しました。");
                    }
                    @Override
                    public void onError(Error error) {
                        Log.d(TAG,"Sレンジングの開始に失敗しました。エラーメッセージ："+error.getMessage());
                    }
                },
                //MAMORIO発見時のコールバック（null可）
                new MamorioSDK.RangingCallbackEnter() {
                    @Override
                    public void onEnter(List<Mamorio> list) {
                        //list:発見したMAMORIOの一覧
                        //nullの場合コールバックしない
                        Log.d(TAG,"RangingCallbackEnter()");

                        for(int i = 0 ; i < list.size() ; i++) {
                            Mamorio dev = list.get(i);

                            String name = dev.getName();
                            if(name != null){
                                Log.d(TAG,"name=" + name);
                            }

                            int major = dev.getMajor();
                            int minor = dev.getMinor();
                            Log.d(TAG,major + "," + minor);

                            if(dev.isNotYours() == true) {
                                Log.d(TAG,"他人のMAMORIO");
                            } else {
                                Log.d(TAG,"自分のMAMORIO");
                                for(PeripheralHistory peripheralHistory : dev.getPeripheralHistories()) {
                                    Log.d("LocationMamorio", peripheralHistory.getDetectedAt().toString());
                                    Log.d("LocationMamorio", peripheralHistory.getLatitude() + "");
                                    Log.d("LocationMamorio", peripheralHistory.getLongitude() + "");
                                }
                            }
                        }
                    }
                },
                //MAMORIO紛失時のコールバック(null可)
                new MamorioSDK.RangingCallbackExit() {
                    @Override
                    public void onExit(List<Mamorio> list) {
                        //list:紛失した自分のMAMORIO一覧
                        Log.d(TAG,"RangingCallbackExit()");

                        for(int i = 0 ; i < list.size() ; i++) {
                            Mamorio dev = list.get(i);

                            String name = dev.getName();
                            if(name != null){
                                Log.d(TAG,"name=" + name);
                            }

                            int major = dev.getMajor();
                            int minor = dev.getMinor();
                            Log.d(TAG,major + "," + minor);

                            if(dev.isNotYours() == true) {
                                Log.d(TAG,"他人のMAMORIO");
                            } else {
                                Log.d(TAG,"自分のMAMORIO");
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop()");
        MamorioSDK.rangingStop();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_mimamo_rare, container, false);

        mapView = (MapView) rootView.findViewById(R.id.map_mimamo_rare);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
