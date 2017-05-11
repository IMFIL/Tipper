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
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by filipslatinac on 2017-05-11.
 */
public class NumberOfPeopleFragment extends Fragment {
    private Typeface fontAwesome;
    private TextView checkMark;
    private EditText ppl;
    private boolean numberIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        View rootView = inflater.inflate(R.layout.people_amnt, container,
                false);

        checkMark = (TextView) rootView.findViewById(R.id.checkMark);

        ppl = (EditText) rootView.findViewById(R.id.totalpplAmnt);
        ppl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tipValue = String.valueOf(ppl.getText()).trim();

                if (tipValue.length() != 0 ){
                    numberIn = true;
                }

                else {
                    numberIn = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String tipValue = String.valueOf(ppl.getText()).trim();

                if (tipValue.length() != 0 && !numberIn){
                    checkMark.setTypeface(fontAwesome);
                    checkMark.setTextColor(Color.parseColor("#32A0A0"));
                    checkMark.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
                }

                else if (numberIn && tipValue.length() != 0 ) {
                }

                else{
                    checkMark.setTextColor(Color.TRANSPARENT);
                }

            }
        });

        return rootView;
    }
}
