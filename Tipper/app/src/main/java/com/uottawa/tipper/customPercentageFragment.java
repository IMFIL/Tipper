package com.uottawa.tipper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by filipslatinac on 2017-05-18.
 */
public class customPercentageFragment extends Fragment {

    private View rootView;

    private Typeface fontAwesome;
    private Typeface sanFran;


    private TextView starRatingTextButton;
    private TextView percentageIcon;

    private boolean initialized = false;

    private EditText totalTipAmnt;

    private SharedPreferences sharedPref = null;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);


        initialized = true;

        rootView = inflater.inflate(R.layout.tip_amnt_custom_test, container,
                false);

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");



        starRatingTextButton = (TextView) rootView.findViewById(R.id.starRatingTextButton);
        starRatingTextButton.setTextColor(Color.parseColor("#2196F3"));
        starRatingTextButton.setTypeface(sanFran);
        starRatingTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getTipPager().setCurrentItem(1);

            }
        });

        percentageIcon = (TextView) rootView.findViewById(R.id.percentageIcon);
        percentageIcon.setTypeface(fontAwesome);

        totalTipAmnt = (EditText) rootView.findViewById(R.id.totalTipAmnt);
        totalTipAmnt.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});
        totalTipAmnt.setText(String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage", 0)))));
        totalTipAmnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tipValue = String.valueOf(totalTipAmnt.getText()).trim();

                if (tipValue.length() != 0){
                    double tipamnt = Double.parseDouble(((EditText) rootView.findViewById(R.id.totalTipAmnt)).getText().toString());
                    ((MainActivity)getActivity()).percentageViewChange(String.format("%.2f",tipamnt));
                    ((MainActivity)getActivity()).onBooleanTipChange(true);

                }


                else{
                    ((MainActivity)getActivity()).percentageViewChange("0");
                    ((MainActivity)getActivity()).onBooleanTipChange(false);
                }

            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && initialized) {

            String tipValue = String.valueOf(totalTipAmnt.getText()).trim();

            if (tipValue.length() != 0){
                double tipamnt = Double.parseDouble(((EditText) rootView.findViewById(R.id.totalTipAmnt)).getText().toString());
                ((MainActivity)getActivity()).percentageViewChange(String.format("%.2f",tipamnt));
                ((MainActivity)getActivity()).onBooleanTipChange(true);

            }


            else{
                ((MainActivity)getActivity()).percentageViewChange(String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage", 0)))));
                totalTipAmnt.setText(String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage", 0)))));
                ((MainActivity)getActivity()).onBooleanTipChange(true);
            }

        }
        else {
        }
    }
}
