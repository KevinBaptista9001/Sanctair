package com.baptista.kevin.sanctair;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShortTermPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShortTermPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShortTermPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShortTermPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShortTermPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ShortTermPage newInstance(String param1, String param2) {
        ShortTermPage fragment = new ShortTermPage();
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

        View rootView = inflater.inflate(R.layout.fragment_short_term_page, container, false);
        TabHost tabHost = (TabHost)rootView.findViewById(R.id.shortTermTabHost);
        tabHost.setup();
        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        TextView overlayCommonContent = (TextView)rootView.findViewById(R.id.commonContentShortTermOverlay);
        TextView markersCommonContent = (TextView)rootView.findViewById(R.id.commonContentShortTermMarkers);
        TextView overlayTitle = (TextView)rootView.findViewById(R.id.shortTermOverlayTitle);
        TextView markersTitle = (TextView)rootView.findViewById(R.id.shortTermMarkersTitle);
        String overlay = getArguments().getString("overlay");
        String markers = getArguments().getString("ratingOverlay");
        String tab = getArguments().getString("tab");

        if (overlay.equals("AIRS_CO_Total_Column_Day")){
            overlayTitle.setText(R.string.carbonMonoxide);
            overlayCommonContent.setText(R.string.coShortTerm);
        }

        else if (overlay.equals("MLS_O3_46hPa_Day")){
            overlayTitle.setText(R.string.ozone);
            overlayCommonContent.setText(R.string.ozoneShortTerm);
            overlayCommonContent.setMovementMethod(new ScrollingMovementMethod());
        }

        else if (overlay.equals("MODIS_Terra_Aerosol")){
            overlayTitle.setText(R.string.aerosol);
            overlayCommonContent.setText(R.string.particlesAerosolEffects);
        }

        else if (overlay.equals("AIRS_RelativeHumidity_400hPa_Day")){
            overlayTitle.setText(R.string.relativehumidity);
            overlayCommonContent.setText(R.string.humidityShortTerm);
        }

        else {
            overlayTitle.setText(R.string.noOvelaySelected);
            overlayCommonContent.setText(R.string.noOverlayContent);
        }

        if (markers.equals("usepa-aqi")){
            markersTitle.setText(R.string.compositeaqi);
            markersCommonContent.setText(R.string.compositeShortTerm);
        }

        else if (markers.equals("usepa-pm25")){
            markersTitle.setText(R.string.fineparticlepollution);
            markersCommonContent.setText(R.string.particlesAerosolEffects);
        }

        else if (markers.equals("usepa-10")){
            markersTitle.setText(R.string.coarseparticlepollution);
            markersCommonContent.setText(R.string.particlesAerosolEffects);
        }

        else if (markers.equals("usepa-o3")){
            markersTitle.setText(R.string.ozone);
            markersCommonContent.setText(R.string.ozoneShortTerm);
            markersCommonContent.setMovementMethod(new ScrollingMovementMethod());
        }

        else if (markers.equals("usepa-so2")){
            markersTitle.setText(R.string.so2);
            markersCommonContent.setText(R.string.so2ShortTerm);
        }

        else if (markers.equals("usepa-co")){
            markersTitle.setText(R.string.carbonMonoxide);
            markersCommonContent.setText(R.string.coShortTerm);
        }
        else{
            markersTitle.setText(R.string.noRatingTitle);
            markersCommonContent.setText(R.string.noRatingContent);
        }

        TabHost.TabSpec spec = tabHost.newTabSpec("overlay");
        spec.setIndicator("Overlay");
        spec.setContent(R.id.shortTermOverlayTab);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("markers");
        spec.setIndicator("Markers");
        spec.setContent(R.id.shortTermMarkersTab);
        tabHost.addTab(spec);

        if (tab.equals("Overlay")){
            tabHost.setCurrentTab(0);
        }
        else {
            tabHost.setCurrentTab(1);
        }

        // Inflate the layout for this fragment
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
