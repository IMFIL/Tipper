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
public class pplFragment extends Fragment {

    private View rootView;

    private TextView[] pplIcons;
    private TextView peopleEatingText;
    private TextView moreButtonText;;

    private Typeface fontAwesome;
    private Typeface sanFran;

    private int currentSelectionPpl = -1;

    private boolean initialized = false;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        initialized = true;

        rootView = inflater.inflate(R.layout.ppl_amnt_test, container,
                false);

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        sanFran = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SanFranciscoDisplay-Light.otf");

        pplIcons = new TextView[]{
                (TextView) rootView.findViewById(R.id.ppl1),
                (TextView) rootView.findViewById(R.id.ppl2),
                (TextView) rootView.findViewById(R.id.ppl3),
                (TextView) rootView.findViewById(R.id.ppl4),
                (TextView) rootView.findViewById(R.id.ppl5)
        };

        for (int i=0; i< pplIcons.length;i++){
            pplIcons[i].setTypeface(fontAwesome);
            pplIcons[i].setTextColor(Color.GRAY);
            pplIcons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (view.getId()){
                        case R.id.ppl1 :
                            pplNumber(0);
                            break;
                        case R.id.ppl2 :
                            pplNumber(1);
                            break;
                        case R.id.ppl3 :
                            pplNumber(2);
                            break;
                        case R.id.ppl4 :
                            pplNumber(3);
                            break;
                        case R.id.ppl5 :
                            pplNumber(4);
                            break;

                        default:
                            break;
                    }

                }
            });
        }

        moreButtonText = (TextView) rootView.findViewById(R.id.morePeople);
        moreButtonText.setTypeface(fontAwesome);
        moreButtonText.setTextColor(Color.parseColor("#2196F3"));
        moreButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ((MainActivity) getActivity()).getPplPager().setCurrentItem(1);
            }
        });

        return rootView;
    }

    private void pplNumber(int number){
        if (number == currentSelectionPpl){
            for(int i=0;i<pplIcons.length;i++){
                pplIcons[i].setText("\uf2c0");
                pplIcons[i].setTypeface(fontAwesome);
                pplIcons[i].setTextColor(Color.GRAY);
            }
            currentSelectionPpl = -1;
            ((MainActivity)getActivity()).peopleViewChange("0");
            ((MainActivity)getActivity()).onBooleanPplChange(false);
        }

        else {

            for (int i = 0; i <= number; i++) {
                pplIcons[i].setText("\uf007");
                pplIcons[i].setTypeface(fontAwesome);
                pplIcons[i].setTextColor(Color.parseColor("#32A0A0"));
            }

            for (int i=number+1;i<5;i++){
                pplIcons[i].setText("\uf2c0");
                pplIcons[i].setTypeface(fontAwesome);
                pplIcons[i].setTextColor(Color.GRAY);
            }
            currentSelectionPpl = number;
            ((MainActivity)getActivity()).peopleViewChange(String.valueOf(number+1));
            ((MainActivity)getActivity()).onBooleanPplChange(true);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && initialized) {
            if (currentSelectionPpl !=-1) {
                ((MainActivity) getActivity()).peopleViewChange(String.valueOf(currentSelectionPpl+1));
                ((MainActivity)getActivity()).onBooleanPplChange(true);
            }

            else{
                ((MainActivity) getActivity()).peopleViewChange(String.valueOf(0));
                ((MainActivity)getActivity()).onBooleanPplChange(false);
            }
        }
        else {
        }
    }

}

