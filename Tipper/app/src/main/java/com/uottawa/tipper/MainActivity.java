package com.uottawa.tipper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements booleanBillPass, booleanPplPass, booleanTipPass {
    private ViewPager mViewPager;
    private boolean billPageDone=false;
    private double billAmtn = 0;
    private boolean tipPageDone=false;
    private double tipAmnt = 0;
    private boolean pplPageDone=false;
    private double pplAmnt = 0;

    private boolean [] calculateConditions = {
            billPageDone,
            tipPageDone,
            pplPageDone
    };

    private boolean ready = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mViewPager.setAdapter(new thipPagerAdapter(
                getSupportFragmentManager()));

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        Typeface sanFran = Typeface.createFromAsset(getAssets(), "fonts/SanFranciscoDisplay-Light.otf");

        TextView settings = (TextView) findViewById(R.id.settings);
        settings.setTypeface(fontAwesome);
        settings.setTextColor(Color.parseColor("#505050"));

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make window pop up with n amnt of tabs
            }
        });

        Button getTotal = (Button) findViewById(R.id.getTotal);
        getTotal.setTypeface(sanFran);
        getTotal.setTextColor(Color.parseColor("#999999"));

        getTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ready){

                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);

                    int width = dm.widthPixels;
                    int height = dm.heightPixels;

                    PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.summary_window,null, false),(int)(width*.8),(int)(height*.8), true);

                    TextView billCost = (TextView) pw.getContentView().findViewById(R.id.dinnigCost);
                    billCost.setText(String.valueOf(billAmtn));
                    TextView tipCost = (TextView)  pw.getContentView().findViewById(R.id.tipAmnt);
                    tipCost.setText(String.valueOf(tipAmnt*billAmtn));
                    TextView pplNumber= (TextView) pw.getContentView().findViewById(R.id.pplAmnt);
                    pplNumber.setText(String.valueOf(pplAmnt));
                    TextView totalCost = (TextView) pw.getContentView().findViewById(R.id.totalCost);
                    totalCost.setText(String.valueOf(billAmtn * (1+tipAmnt)));
                    TextView youPay = (TextView) pw.getContentView().findViewById(R.id.youPay );
                    youPay.setText(String.valueOf((billAmtn * (1+tipAmnt))/pplAmnt));

                    pw.showAtLocation(findViewById(R.id.activity_main),Gravity.CENTER,0,0);
                }

                else{
                    String message = "";
                    for (int i=0; i<calculateConditions.length;i++){
                        if (!calculateConditions[i]){
                            if (i == 0){
                                message += "Bill Amount";

                                if(!calculateConditions[1] || !calculateConditions[2]){
                                    message+=",";
                                }
                            }

                            else if (i == 1){
                                message += "Tip Percentage";
                                if(!calculateConditions[2]){
                                    message+=",";
                                }
                            }

                            else{
                                message += "Number of People";
                            }
                        }
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Please Fill in the " + message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    @Override
    public void onBooleanBillChange(boolean changed,int amnt) {
        billPageDone = changed;
        ready = billPageDone && tipPageDone && pplPageDone;
        calculateConditions = new boolean [] {
                billPageDone,
                tipPageDone,
                pplPageDone
        };
        billAmtn = amnt;
    }

    @Override
    public void onBooleanPplChange(boolean changed,int amnt) {
        pplPageDone = changed;
        ready = billPageDone && tipPageDone && pplPageDone;
        calculateConditions = new boolean[]{
                billPageDone,
                tipPageDone,
                pplPageDone
        };
        pplAmnt = amnt;
    }

    @Override
    public void onBooleanTipChange(boolean changed,int amnt) {
        tipPageDone = changed;
        ready = billPageDone && tipPageDone && pplPageDone;
        calculateConditions = new boolean[] {
                billPageDone,
                tipPageDone,
                pplPageDone
        };
        tipAmnt = amnt/100;

    }
}
