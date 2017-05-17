package com.uottawa.tipper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements booleanBillPass, booleanPplPass, booleanTipPass {
    private View dialogView;
    private Typeface fontAwesome;
    private Typeface sanFran;
    private String currencyTotalPage = "$";
    private CustomViewPager mViewPager;
    private double billAmtn = 0;
    private double tipAmnt = 0;
    private double pplAmnt = 0;
    private TextView [] indicators;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (CustomViewPager) findViewById(R.id.mainPager);
        mViewPager.setAdapter(new thipPagerAdapter(
                getSupportFragmentManager()));

        fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        sanFran = Typeface.createFromAsset(getAssets(), "fonts/SanFranciscoDisplay-Light.otf");


        TextView settings = (TextView) findViewById(R.id.settings);
        settings.setTypeface(fontAwesome);
        settings.setTextColor(Color.parseColor("#505050"));

        indicators = new TextView[]{
                (TextView) findViewById(R.id.indicator1),
                (TextView) findViewById(R.id.indicator2),
                (TextView) findViewById(R.id.indicator3)
        };

        for (int i=0; i<3; i++){
            indicators[i].setTypeface(fontAwesome);
        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

                final LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.settings_page, null);

                final Spinner spinner = (Spinner)dialogView.findViewById(R.id.currency_spinner);
                final EditText tipPercentage = (EditText) dialogView.findViewById(R.id.percentage_default);


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         double tipVal;

                        if (tipPercentage.getText().toString().trim().matches("")){
                            tipVal=10;
                        }

                        else{
                            tipVal = (Double.parseDouble(tipPercentage.getText().toString()));
                        }

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Currency",spinner.getSelectedItem().toString());
                        editor.putLong("tipPercentage",Double.doubleToRawLongBits(tipVal));
                        editor.apply();

                        String currencyText = sharedPref.getString("Currency", "Dollar");


                        TextView currency = (TextView) findViewById(R.id.curency_sign);
                        EditText defaultTip = (EditText) findViewById(R.id.totalTipAmnt);

                        String tip = String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage",10))));



                        if (currency == null){

                        }
                        else {
                            switch (currencyText) {
                                case "Dollar":
                                    currency.setText("\uf155");
                                    currencyTotalPage = "$";
                                    break;
                                case "Euro":
                                    currency.setText("\uf153");
                                    currencyTotalPage ="€";
                                    break;
                                case "British Pound":
                                    currency.setText("\uf154");
                                    currencyTotalPage ="£";
                                    break;
                                default:
                                    break;
                            }
                        }

                        if (defaultTip == null){

                        }
                        else{
                            defaultTip.setText(tip);
                        }

                    }
                });

                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.currencies, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
                String currencyText = sharedPref.getString("Currency", "Dollar");

                switch (currencyText) {
                    case "Dollar":
                        spinner.setSelection(0);
                        break;
                    case "Euro":
                        spinner.setSelection(2);
                        break;
                    case "British Pound":
                        spinner.setSelection(1);
                        break;
                    default:
                        break;
                }

                EditText defaultTip = (EditText) dialogView.findViewById(R.id.percentage_default);

                String tip = String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage",10))));

                defaultTip.setText(tip);

                dialog.show();
            }
        });
    }

    @Override
    public void onBooleanBillChange(boolean changed,double amnt) {
        billAmtn = amnt;
    }

    @Override
    public void onBooleanPplChange(boolean changed,int amnt) {
        pplAmnt = amnt;
    }

    @Override
    public void onBooleanTipChange(boolean changed,double amnt) {
        tipAmnt = amnt/100;
    }

    public ViewPager getPager() {
        return mViewPager;
    }

        public void lauchCalculateTotal(){

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.summary_window, null, false), (int) (width * .95), (int) (height * .6), true);

            TextView xButton = (TextView) pw.getContentView().findViewById(R.id.exitPopUp);
            xButton.setTypeface(fontAwesome);
            xButton.setTextColor(Color.RED);
            xButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    mViewPager.setAdapter(new thipPagerAdapter(
                            getSupportFragmentManager()));
                    EditText billamnt = (EditText) findViewById(R.id.totalBillAmnt);
                    billamnt.getText().clear();

                    EditText defaultTip = (EditText) findViewById(R.id.totalTipAmnt);
                    String tip = String.format("%.2f", (Double.longBitsToDouble(sharedPref.getLong("tipPercentage", 10))));
                    defaultTip.setText(tip);

                    changeIndicatorLevel(1);

                    pw.dismiss();
                }
            });

            TextView billCost = (TextView) pw.getContentView().findViewById(R.id.dinnigCost);
            billCost.setText(currencyTotalPage + String.format("%.2f", billAmtn));
            billCost.setTypeface(sanFran);
            TextView billCostTitle = (TextView) pw.getContentView().findViewById(R.id.dinnigCostTV);
            billCostTitle.setTypeface(sanFran);

            TextView tipCost = (TextView) pw.getContentView().findViewById(R.id.tipAmnt);
            tipCost.setText(currencyTotalPage + String.format("%.2f", tipAmnt * billAmtn));
            tipCost.setTypeface(sanFran);
            TextView tipCostTitle = (TextView) pw.getContentView().findViewById(R.id.tipAmntTV);
            tipCostTitle.setTypeface(sanFran);

            TextView pplNumber = (TextView) pw.getContentView().findViewById(R.id.pplAmnt);
            pplNumber.setText(String.format("%.0f", pplAmnt));
            pplNumber.setTypeface(sanFran);
            TextView pplNumberTitle = (TextView) pw.getContentView().findViewById(R.id.pplAmntTV);
            pplNumberTitle.setTypeface(sanFran);


            TextView totalCost = (TextView) pw.getContentView().findViewById(R.id.totalCost);
            totalCost.setText(currencyTotalPage + String.format("%.2f", billAmtn * (1 + tipAmnt)));
            totalCost.setTypeface(sanFran);
            TextView totalCostTitle = (TextView) pw.getContentView().findViewById(R.id.totalCostTV);
            totalCostTitle.setTypeface(sanFran);

            TextView yourTip = (TextView) pw.getContentView().findViewById(R.id.yourTip);
            yourTip.setText(currencyTotalPage + String.format("%.2f", (billAmtn * tipAmnt) / pplAmnt));
            yourTip.setTypeface(sanFran);
            TextView yourtipTitle = (TextView) pw.getContentView().findViewById(R.id.yourTipTV);
            yourtipTitle.setTypeface(sanFran);

            TextView youPay = (TextView) pw.getContentView().findViewById(R.id.youPay);
            youPay.setText(currencyTotalPage + String.format("%.2f", (billAmtn * (1 + tipAmnt)) / pplAmnt));
            youPay.setTypeface(sanFran);
            TextView youPayTitle = (TextView) pw.getContentView().findViewById(R.id.youPayTV);
            youPayTitle.setTypeface(sanFran);


            pw.showAtLocation(findViewById(R.id.activity_main), Gravity.CENTER, 0, 0);

        }

    public void changeIndicatorLevel(int indiexId){

        for(int i=0; i<indicators.length;i++){
            indicators[i].setText("\uf10c");
        }
        indicators[indiexId-1].setText("\uf111");
    }


    }


