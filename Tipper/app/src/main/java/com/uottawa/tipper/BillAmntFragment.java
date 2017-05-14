package com.uottawa.tipper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private View rootView;
    private TextView arrow;
    private Typeface fontAwesome;
    private EditText bill;
    private boolean numberIn;
    private booleanBillPass dataPasser;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (booleanBillPass) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            Typeface sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");


            rootView = inflater.inflate(R.layout.bill_amnt, container,
                    false);


            TextView or = (TextView) rootView.findViewById(R.id.orText);
            TextView camera = (TextView) rootView.findViewById(R.id.camera);

            TextView currency = (TextView) rootView.findViewById(R.id.curency_sign);
            currency.setTypeface(fontAwesome);

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
                        int billamnt = Integer.parseInt(((EditText) rootView.findViewById(R.id.totalBillAmnt)).getText().toString());
                        arrow.setTypeface(fontAwesome);
                        arrow.setTextColor(Color.parseColor("#32A0A0"));
                        arrow.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
                        dataPasser.onBooleanBillChange(true,billamnt);

                    }

                    else if (numberIn && tipValue.length() != 0 ) {
                        int billamnt = Integer.parseInt(((EditText) rootView.findViewById(R.id.totalBillAmnt)).getText().toString());
                        dataPasser.onBooleanBillChange(false,billamnt);
                    }

                    else{
                        arrow.setTextColor(Color.TRANSPARENT);
                        dataPasser.onBooleanBillChange(false,0);

                    }

                }
            });


        return rootView;
        }

}
