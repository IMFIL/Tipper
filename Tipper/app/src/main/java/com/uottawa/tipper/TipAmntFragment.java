package com.uottawa.tipper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private View rootView;
    private TextView [] tviews;
    private int currentSelection = -1;
    private Typeface fontAwesome;
    private EditText tip;
    private TextView arrowRight;
    private TextView arrowLeft;
    private boolean arrowClickable = true;
    private boolean systemText = false;
    private boolean numberIn;

    private booleanTipPass dataPasser;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (booleanTipPass) context;

    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        final Typeface sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long tipDefault = sharedPref.getLong("tipPercentage", 10);

        String tipDefaultString = String.format("%.2f",(Double.longBitsToDouble(tipDefault)));

        rootView = inflater.inflate(R.layout.tip_amnt, container,
                false);

        arrowRight = (TextView) rootView.findViewById(R.id.arrowRight);

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrowClickable){
                    ((MainActivity) getActivity()).getPager().setCurrentItem(2);
                    ((MainActivity) getActivity()).changeIndicatorLevel(3);
                }
            }
        });

        arrowLeft = (TextView) rootView.findViewById(R.id.arrowLeft);

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ((MainActivity) getActivity()).getPager().setCurrentItem(0);
                ((MainActivity) getActivity()).changeIndicatorLevel(1);
            }
        });

        arrowLeft.setTypeface(fontAwesome);
        arrowLeft.setTextColor(Color.parseColor("#32A0A0"));

        EditText tipShowed = (EditText) rootView.findViewById(R.id.totalTipAmnt);
        tipShowed.setText(tipDefaultString);
        dataPasser.onBooleanTipChange(true,Double.longBitsToDouble(tipDefault));


        if (!tipDefaultString.equals("")){
            arrowRight.setTypeface(fontAwesome);
            arrowRight.setTextColor(Color.parseColor("#32A0A0"));
            arrowRight.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
        }


        tviews = new TextView[]{
                (TextView) rootView.findViewById(R.id.star1),
                (TextView) rootView.findViewById(R.id.star2),
                (TextView) rootView.findViewById(R.id.star3),
                (TextView) rootView.findViewById(R.id.star4),
                (TextView) rootView.findViewById(R.id.star5)
        };


        TextView percentage = (TextView) rootView.findViewById(R.id.percentage);
        percentage.setTypeface(fontAwesome);

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
                clearStarRating();
                String tipValue = String.valueOf(tip.getText()).trim();

                if (tipValue.length() != 0 && !numberIn){
                    double tipamnt = Double.parseDouble(((EditText) rootView.findViewById(R.id.totalTipAmnt)).getText().toString());
                    arrowRight.setTypeface(fontAwesome);
                    arrowRight.setTextColor(Color.parseColor("#32A0A0"));
                    arrowRight.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));
                    arrowClickable = true;
                    dataPasser.onBooleanTipChange(true,tipamnt);

                }

                else if (numberIn && tipValue.length() != 0 ) {
                    double tipamnt = Double.parseDouble(((EditText) rootView.findViewById(R.id.totalTipAmnt)).getText().toString());
                    arrowClickable = true;
                    dataPasser.onBooleanTipChange(true,tipamnt);
                }

                else{
                    arrowRight.setTextColor(Color.TRANSPARENT);
                    arrowClickable = false;
                    dataPasser.onBooleanTipChange(false,0);
                }

                systemText = false;

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
            systemText = true;

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
            tip.setText(String.format("%.2f",(double)number*2+10));
        }

    }


    private void clearStarRating(){

        if (!systemText){
            for(int i=0;i<tviews.length;i++){
                tviews[i].setText("\uf006");
                tviews[i].setTypeface(fontAwesome);
                tviews[i].setTextColor(Color.GRAY);
            }
            currentSelection = -1;
        }
    }

}
