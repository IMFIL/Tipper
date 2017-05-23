package com.uottawa.tipper;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by filipslatinac on 2017-05-18.
 */
public class customPplFragment extends Fragment {
    private View rootView;

    private Typeface fontAwesome;


    private TextView pplIconMultiplication;
    private TextView pplMultiplication;
    private TextView lessThan5PplEating;


    private boolean initialized = false;



    private EditText pplInput;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        initialized = true;

        rootView = inflater.inflate(R.layout.ppl_amnt_custom_test, container,
                false);

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        pplIconMultiplication = (TextView) rootView.findViewById(R.id.pplIconMultiplication);
        pplIconMultiplication.setTypeface(fontAwesome);

        pplMultiplication = (TextView) rootView.findViewById(R.id.pplMultiplication);
        pplMultiplication.setTypeface(fontAwesome);

        lessThan5PplEating = (TextView) rootView.findViewById(R.id.lessThan5TextButton);
        lessThan5PplEating.setTypeface(fontAwesome);
        lessThan5PplEating.setTextColor(Color.parseColor("#2196F3"));
        lessThan5PplEating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getPplPager().setCurrentItem(0);
            }
        });

        pplInput = (EditText) rootView.findViewById(R.id.pplInput);
        pplInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pplNumber = String.valueOf(pplInput.getText()).trim();

                if (pplNumber.length() != 0 ){
                    int pplamnt = Integer.parseInt(((EditText) rootView.findViewById(R.id.pplInput)).getText().toString());
                    ((MainActivity)getActivity()).peopleViewChange(String.valueOf(pplNumber));
                    ((MainActivity)getActivity()).onBooleanPplChange(true);

                }


                else{
                    ((MainActivity)getActivity()).peopleViewChange("0");
                    ((MainActivity)getActivity()).onBooleanPplChange(false);
                }

            }
        });


        return rootView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && initialized) {
            String pplNumber = String.valueOf(pplInput.getText()).trim();

            if (pplNumber.length() != 0 ){
                int pplamnt = Integer.parseInt(((EditText) rootView.findViewById(R.id.pplInput)).getText().toString());
                ((MainActivity)getActivity()).peopleViewChange(String.valueOf(pplNumber));
                ((MainActivity)getActivity()).onBooleanPplChange(true);

            }


            else{
                ((MainActivity)getActivity()).peopleViewChange("0");
                ((MainActivity)getActivity()).onBooleanPplChange(false);
            }

        }
        else {
        }
    }
}
