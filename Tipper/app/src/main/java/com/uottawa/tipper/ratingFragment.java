package com.uottawa.tipper;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by filipslatinac on 2017-05-18.
 */
public class ratingFragment extends Fragment {

    private View rootView;

    private TextView [] starIcons;
    private TextView customPercentageButtonText;

    private Typeface fontAwesome;
    private Typeface sanFran;

    private int currentSelectionStar = -1;

    private boolean initialized = false;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
    Bundle savedInstanceState) {

        initialized = true;

        rootView = inflater.inflate(R.layout.tip_amnt_test, container,
                false);

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");


        starIcons = new TextView[]{
                (TextView) rootView.findViewById(R.id.star1),
                (TextView) rootView.findViewById(R.id.star2),
                (TextView) rootView.findViewById(R.id.star3),
                (TextView) rootView.findViewById(R.id.star4),
                (TextView) rootView.findViewById(R.id.star5)
        };

        for (int i=0; i< starIcons.length;i++){
            starIcons[i].setTypeface(fontAwesome);
            starIcons[i].setTextColor(Color.GRAY);
            starIcons[i].setOnClickListener(new View.OnClickListener() {
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

        customPercentageButtonText = (TextView) rootView.findViewById(R.id.customPercentage);
        customPercentageButtonText.setTypeface(sanFran);
        customPercentageButtonText.setTextColor(Color.parseColor("#2196F3"));
        customPercentageButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getTipPager().setCurrentItem(0);
            }
        });


        return rootView;

    }
    private void starRating(int number){
        if (number == currentSelectionStar){
            for(int i=0;i<starIcons.length;i++){
                starIcons[i].setText("\uf006");
                starIcons[i].setTypeface(fontAwesome);
                starIcons[i].setTextColor(Color.GRAY);
            }
            currentSelectionStar = -1;
            ((MainActivity)getActivity()).percentageViewChange("0");
            ((MainActivity)getActivity()).onBooleanTipChange(false);
        }

        else {

            for (int i = 0; i <= number; i++) {
                starIcons[i].setText("\uf005");
                starIcons[i].setTypeface(fontAwesome);
                starIcons[i].setTextColor(Color.YELLOW);
            }

            for (int i=number+1;i<5;i++){
                starIcons[i].setText("\uf006");
                starIcons[i].setTypeface(fontAwesome);
                starIcons[i].setTextColor(Color.GRAY);
            }
            currentSelectionStar = number;
            ((MainActivity)getActivity()).percentageViewChange(String.format("%.2f",(double)number*2+10));
            ((MainActivity)getActivity()).onBooleanTipChange(true);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && initialized) {
            if (currentSelectionStar !=-1) {
                ((MainActivity) getActivity()).percentageViewChange(String.format("%.2f", (double) currentSelectionStar * 2 + 10));
                ((MainActivity)getActivity()).onBooleanTipChange(true);

            }

            else{
                ((MainActivity) getActivity()).percentageViewChange(String.format("%.2f", 0.00));
                ((MainActivity)getActivity()).onBooleanTipChange(false);
            }
        }
        else {
        }
    }

}
