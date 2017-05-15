package com.uottawa.tipper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements booleanBillPass, booleanPplPass, booleanTipPass {

    private Typeface fontAwesome;
    private Typeface sanFran;
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

        fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        sanFran = Typeface.createFromAsset(getAssets(), "fonts/SanFranciscoDisplay-Light.otf");

        TextView settings = (TextView) findViewById(R.id.settings);
        settings.setTypeface(fontAwesome);
        settings.setTextColor(Color.parseColor("#505050"));

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                View v = inflater.inflate(R.layout.settings_page, null);

                builder.setView(v);

                AlertDialog dialog = builder.create();

                Spinner spinner = (Spinner)v.findViewById(R.id.currency_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.currencies, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);

                dialog.show();
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

                    final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.summary_window,null, false),(int)(width*.95),(int)(height*.6), true);

                    TextView xButton = (TextView) pw.getContentView().findViewById(R.id.exitPopUp);
                    xButton.setTypeface(fontAwesome);
                    xButton.setTextColor(Color.RED);
                    xButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pw.dismiss();
                        }
                    });

                    TextView billCost = (TextView) pw.getContentView().findViewById(R.id.dinnigCost);
                    billCost.setText("$"+String.format("%.2f", billAmtn));
                    billCost.setTypeface(sanFran);
                    TextView billCostTitle = (TextView) pw.getContentView().findViewById(R.id.dinnigCostTV);
                    billCostTitle.setTypeface(sanFran);

                    TextView tipCost = (TextView)  pw.getContentView().findViewById(R.id.tipAmnt);
                    tipCost.setText("$"+String.format("%.2f", tipAmnt*billAmtn));
                    tipCost.setTypeface(sanFran);
                    TextView tipCostTitle = (TextView) pw.getContentView().findViewById(R.id.tipAmntTV);
                    tipCostTitle.setTypeface(sanFran);

                    TextView pplNumber= (TextView) pw.getContentView().findViewById(R.id.pplAmnt);
                    pplNumber.setText(String.format("%.0f", pplAmnt));
                    pplNumber.setTypeface(sanFran);
                    TextView pplNumberTitle = (TextView) pw.getContentView().findViewById(R.id.pplAmntTV);
                    pplNumberTitle.setTypeface(sanFran);


                    TextView totalCost = (TextView) pw.getContentView().findViewById(R.id.totalCost);
                    totalCost.setText("$"+String.format("%.2f", billAmtn * (1+tipAmnt)));
                    totalCost.setTypeface(sanFran);
                    TextView totalCostTitle = (TextView) pw.getContentView().findViewById(R.id.totalCostTV);
                    totalCostTitle.setTypeface(sanFran);

                    TextView youPay = (TextView) pw.getContentView().findViewById(R.id.youPay );
                    youPay.setText("$"+String.format("%.2f", (billAmtn * (1+tipAmnt))/pplAmnt));
                    youPay.setTypeface(sanFran);
                    TextView youPayTitle = (TextView) pw.getContentView().findViewById(R.id.youPayTV);
                    youPayTitle.setTypeface(sanFran);

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
    public void onBooleanBillChange(boolean changed,double amnt) {
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
    public void onBooleanTipChange(boolean changed,double amnt) {
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
