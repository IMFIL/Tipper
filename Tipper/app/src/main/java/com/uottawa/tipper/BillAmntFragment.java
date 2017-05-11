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
 * Created by filipslatinac on 2017-05-10.
 */
public class BillAmntFragment extends Fragment {
    private TextView arrow;
    private Typeface fontAwesome;
    private EditText bill;
    boolean numberIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            Typeface sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");


            View rootView = inflater.inflate(R.layout.bill_amnt, container,
                    false);


            TextView or = (TextView) rootView.findViewById(R.id.orText);
            TextView camera = (TextView) rootView.findViewById(R.id.camera);

            or.setTypeface(sanFran);
            camera.setTypeface(fontAwesome);

            arrow = (TextView) rootView.findViewById(R.id.arrow);

            bill = (EditText) rootView.findViewById(R.id.totalBillAmnt);
            bill.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String tipValue = String.valueOf(bill.getText()).trim();

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

                    String tipValue = String.valueOf(bill.getText()).trim();

                    if (tipValue.length() != 0 && !numberIn){
                        arrow.setTypeface(fontAwesome);
                        arrow.setTextColor(Color.parseColor("#32A0A0"));
                        arrow.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
                    }

                    else if (numberIn && tipValue.length() != 0 ) {
                    }

                    else{
                        arrow.setTextColor(Color.TRANSPARENT);
                    }

                }
            });


        return rootView;
        }

}
