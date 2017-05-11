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
public class TipAmntFragment extends Fragment {

    private TextView [] tviews;
    private int currentSelection = -1;
    private Typeface fontAwesome;
    private EditText tip;
    private TextView arrow;
    private boolean numberIn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        Typeface sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");


        View rootView = inflater.inflate(R.layout.tip_amnt, container,
                false);

        tviews = new TextView[]{
                (TextView) rootView.findViewById(R.id.star1),
                (TextView) rootView.findViewById(R.id.star2),
                (TextView) rootView.findViewById(R.id.star3),
                (TextView) rootView.findViewById(R.id.star4),
                (TextView) rootView.findViewById(R.id.star5)
        };

        arrow = (TextView) rootView.findViewById(R.id.arrow);

        tip = (EditText) rootView.findViewById(R.id.totalTipAmnt);
        tip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tipValue = String.valueOf(tip.getText()).trim();

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

                String tipValue = String.valueOf(tip.getText()).trim();

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

        TextView or = (TextView) rootView.findViewById(R.id.orText);
        or.setTypeface(sanFran);

        for (int i=0; i< tviews.length;i++){
            tviews[i].setTypeface(fontAwesome);
            tviews[i].setTextColor(Color.GRAY);
            tviews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (view.getId()){
                        case R.id.star1 :
                            starRating(0);
                            break;
                        case R.id.star2 :
                            starRating(1);
                            break;
                        case R.id.star3 :
                            starRating(2);
                            break;
                        case R.id.star4 :
                            starRating(3);
                            break;
                        case R.id.star5 :
                            starRating(4);
                            break;

                        default:
                            break;
                    }

                }
            });
        }

        return rootView;
    }


    private void starRating(int number){
        if (number == currentSelection){
            for(int i=0;i<tviews.length;i++){
                tviews[i].setText("\uf006");
                tviews[i].setTypeface(fontAwesome);
                tviews[i].setTextColor(Color.GRAY);
            }
            currentSelection = -1;
            tip.setText("");

        }

        else {
            for (int i = 0; i <= number; i++) {
                tviews[i].setText("\uf005");
                tviews[i].setTypeface(fontAwesome);
                tviews[i].setTextColor(Color.YELLOW);
            }

            for (int i=number+1;i<5;i++){
                tviews[i].setText("\uf006");
                tviews[i].setTypeface(fontAwesome);
                tviews[i].setTextColor(Color.GRAY);
            }
            currentSelection = number;
            tip.setText(Integer.toString(number*2+10) + "%");
        }

    }

}
