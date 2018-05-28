package com.mimamori.mimaco;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import otoshimono.com.lost.mamorio.sdk.MamorioSDK;
import otoshimono.com.lost.mamorio.sdk.User;
import otoshimono.com.lost.mamorio.sdk.Error;


public class MimamorioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = MimamorioFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private KEYS keys = new KEYS();

    public MimamorioFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart(){
        super.onStart();
        Log.i("LifeCycle", "onStart");

        MamorioSDK.setUp(getActivity().getBaseContext(), "APP_TOKEN");
        User.signIn(keys.getUser(), keys.getPassword(), new User.UserCallback() {
            @Override
            public void onSuccess(User user) {
                //アカウント登録成功時の処理
                Log.d(TAG,"ユーザーのサインインに成功しました");
            }

            @Override
            public void onError(Error error) {
                //アカウント登録失敗時の処理
                Log.d(TAG,"ユーザー登録に失敗しました。エラーメッセージ：" + error.getMessage());
            }
        });
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
    public static MimamorioFragment newInstance(String param1, String param2) {
        MimamorioFragment fragment = new MimamorioFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mimamorio, container, false);
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
}
