package com.baptista.kevin.sanctair;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailsPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsPage.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsPage newInstance(String param1, String param2) {
        DetailsPage fragment = new DetailsPage();
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
        View rootView = inflater.inflate(R.layout.fragment_details_page, container, false);
        TabHost tabHost = (TabHost)rootView.findViewById(R.id.detailsTabHost);
        TextView overlayTitle = (TextView)rootView.findViewById(R.id.overlayTitle);
        TextView overlayContent = (TextView)rootView.findViewById(R.id.overlayContent);
        ImageView overlayScale = (ImageView)rootView.findViewById(R.id.overlayScale);
        TextView maxValue = (TextView)rootView.findViewById(R.id.maxValue);
        TextView minValue = (TextView)rootView.findViewById(R.id.minValue);
        TextView markersTitle = (TextView)rootView.findViewById(R.id.markersTitle);
        TextView markersContent = (TextView)rootView.findViewById(R.id.markersContent);
        TextView goodRange = (TextView)rootView.findViewById(R.id.goodRange);
        TextView moderateRange = (TextView)rootView.findViewById(R.id.moderateRange);
        TextView ufsgRange = (TextView)rootView.findViewById(R.id.ufsgRange);
        TextView unhealthyRange = (TextView)rootView.findViewById(R.id.unhealthyRange);
        TextView vuRange = (TextView)rootView.findViewById(R.id.vuRange);
        TextView hazardousRange = (TextView)rootView.findViewById(R.id.hazardousRange);
        TextView ratingDetail = (TextView)rootView.findViewById(R.id.ratingDetail);
        tabHost.setup();
        String overlay = getArguments().getString("overlay");
        String markers = getArguments().getString("ratingOverlay");
        String tab = getArguments().getString("tab");

        //TODO: Optimize with switch?

        if (overlay.equals("AIRS_CO_Total_Column_Day")){
            overlayTitle.setText(R.string.carbonMonoxide);
            overlayContent.setText(R.string.coAIRS);
            overlayScale.setImageResource(R.drawable.carbonscale);
            maxValue.setText(R.string.maxCO);
            minValue.setText(R.string.minCO);
        }

/*        else if (overlay.equals("MLS_HNO3_46hPa_Day")){

        }


        else if (overlay.equals("AIRS_Dust_Score")){

        }*/

        else if (overlay.equals("AIRS_RelativeHumidity_400hPa_Day")){
            overlayTitle.setText(R.string.relativehumidity);
            overlayContent.setText(R.string.humidityInfo);
            overlayScale.setImageResource(R.drawable.humidityindex);
            maxValue.setText(R.string.maxHumidity);
            minValue.setText(R.string.minHumidity);
        }


        else if (overlay.equals("MLS_O3_46hPa_Day")){
            overlayTitle.setText(R.string.ozone);
            overlayContent.setText(R.string.ozoneInfo);
            overlayScale.setImageResource(R.drawable.ozoneindex);
            maxValue.setText(R.string.ozoneMax);
            minValue.setText(R.string.ozoneMin);
        }

        else if (overlay.equals("MODIS_Terra_Aerosol")){
            //TODO: Add other Aerosol overlays
            overlayTitle.setText(R.string.aerosol);
            overlayContent.setText(R.string.aerosolInfo);
            maxValue.setText(R.string.aerosolMax);
            minValue.setText(R.string.aerosolMin);
            overlayScale.setImageResource(R.drawable.aerosolindex);
        }
        else{
            overlayTitle.setText(R.string.noOvelaySelected);
            overlayContent.setText(R.string.noOverlayContent);
            overlayScale.setImageDrawable(null);
            maxValue.setText(null);
            minValue.setText(null);

        }

        if (markers.equals("usepa-aqi")){
            markersTitle.setText(R.string.compositeaqi);
            markersContent.setText(R.string.compositeAQIinfo);
            goodRange.setText(R.string.compositeGood);
            moderateRange.setText(R.string.compositeModerate);
            ufsgRange.setText(R.string.compositeUFSG);
            unhealthyRange.setText(R.string.compositeUnhealthy);
            vuRange.setText(R.string.compositeVU);
            hazardousRange.setText(R.string.compositeHazardous);
            ratingDetail.setText(null);
        }
        else if (markers.equals("usepa-pm25")){
            markersTitle.setText(R.string.fineparticlepollution);
            markersContent.setText(R.string.pm25MarkerInfo);
            goodRange.setText(R.string.o3Good);
            moderateRange.setText(R.string.o3Moderate);
            ufsgRange.setText(R.string.o3UFSG);
            unhealthyRange.setText(R.string.o3Unhealthy);
            vuRange.setText(R.string.o3VU);
            hazardousRange.setText(R.string.allHazardous);
            ratingDetail.setText(R.string.pm25Detail);
        }
        else if (markers.equals("usepa-10")){
            markersTitle.setText(R.string.coarseparticlepollution);
            markersContent.setText(R.string.pm25MarkerInfo);
            goodRange.setText(R.string.o3Good);
            moderateRange.setText(R.string.o3Moderate);
            ufsgRange.setText(R.string.o3UFSG);
            unhealthyRange.setText(R.string.o3Unhealthy);
            vuRange.setText(R.string.o3VU);
            hazardousRange.setText(R.string.allHazardous);
            ratingDetail.setText(R.string.pm10Detail);
        }
        else if (markers.equals("usepa-o3")){
            markersTitle.setText(R.string.ozone);
            markersContent.setText(R.string.ozoneMarkerInfo);
            goodRange.setText(R.string.o3Good);
            moderateRange.setText(R.string.o3Moderate);
            ufsgRange.setText(R.string.o3UFSG);
            unhealthyRange.setText(R.string.o3Unhealthy);
            vuRange.setText(R.string.o3VU);
            hazardousRange.setText(R.string.allHazardous);
            ratingDetail.setText(R.string.o3Detail);
        }
/*        else if (markers.equals("usepa-no2")){

        }*/
        else if (markers.equals("usepa-so2")){
            markersTitle.setText(R.string.so2);
            markersContent.setText(R.string.so2MarkerInfo);
            goodRange.setText(R.string.o3Good);
            moderateRange.setText(R.string.coModerate);
            ufsgRange.setText(R.string.so2UFSG);
            unhealthyRange.setText(R.string.so2Unhealthy);
            vuRange.setText(R.string.so2VU);
            hazardousRange.setText(R.string.allHazardous);
            ratingDetail.setText(R.string.so2Detail);

        }
        else if (markers.equals("usepa-co")){
            markersTitle.setText(R.string.carbonMonoxide);
            markersContent.setText(R.string.coMarkerInfo);
            goodRange.setText(R.string.o3Good);
            moderateRange.setText(R.string.coModerate);
            ufsgRange.setText(R.string.coUFSG);
            unhealthyRange.setText(R.string.coUnhealthy);
            vuRange.setText(R.string.coVU);
            hazardousRange.setText(R.string.allHazardous);
            ratingDetail.setText(R.string.coDetail);
        }
        else {
            markersTitle.setText(R.string.noRatingTitle);
            markersContent.setText(R.string.noRatingContent);
            goodRange.setText(null);
            moderateRange.setText(null);
            ufsgRange.setText(null);
            unhealthyRange.setText(null);
            vuRange.setText(null);
            hazardousRange.setText(null);
            ratingDetail.setText(null);
        }



        TabHost.TabSpec spec = tabHost.newTabSpec("overlay");
        spec.setIndicator("Overlay");
        spec.setContent(R.id.detailsOverlayTab);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("markers");
        spec.setIndicator("Markers");
        spec.setContent(R.id.detailsMarkersTab);
        tabHost.addTab(spec);

        if (tab.equals("Overlay")){
            tabHost.setCurrentTab(0);
        }
        else {
            tabHost.setCurrentTab(1);
        }

        return rootView;
    }

    public void updateFromContext(String newOverlay, String newMarkers){

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
