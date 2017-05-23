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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView[] starIcons;
    private TextView[] pplIcons;

    private int currentSelectionPpl = -1;
    private int currentSelectionStar = -1;

    private TextView currency;
    private TextView takePictureOfReceiptButtonText;
    private TextView serviceText;
    private TextView customPercentageButtonText;
    private TextView peopleEatingText;
    private TextView moreButtonText;
    private TextView percentageView;
    private TextView peopleView;
    private TextView checkMark;

    private ViewPager tipPager;
    private ViewPager pplPager;

    private EditText billAmount;





    private View dialogView;
    private Typeface fontAwesome;
    private Typeface sanFran;
    private Typeface sanFranBolder;
    private ViewPager mViewPager;
    private String currencyTotalPage = "$";
    private boolean billSet = false;
    private boolean tipSet = false;
    private boolean pplSet= false;
    private TextView [] indicators;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_testview);

        fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        sanFran = Typeface.createFromAsset(getAssets(), "fonts/SanFranciscoDisplay-Light.otf");
        sanFranBolder = Typeface.createFromAsset(getAssets(), "fonts/SanFranciscoDisplay-Semibold.otf");



        currency = (TextView) findViewById(R.id.curency_sign);
        currency.setTypeface(fontAwesome);

        checkMark = (TextView) findViewById(R.id.checkMark);
        checkMark.setTypeface(fontAwesome);



        takePictureOfReceiptButtonText = (TextView) findViewById(R.id.camera);
        takePictureOfReceiptButtonText.setTypeface(sanFran);
        takePictureOfReceiptButtonText.setTextColor(Color.parseColor("#2196F3"));

        peopleEatingText = (TextView) findViewById(R.id.pplEatingText);
        peopleEatingText.setTypeface(sanFranBolder);

        serviceText = (TextView) findViewById(R.id.ServiceText);
        serviceText.setTypeface(sanFranBolder);

        percentageView = (TextView) findViewById(R.id.percentageView);
        percentageView.setTypeface(sanFran);


        peopleView = (TextView) findViewById(R.id.pplView);
        peopleView.setTypeface(sanFran);


        TextView settings = (TextView) findViewById(R.id.settings);
        settings.setTypeface(fontAwesome);
        settings.setTextColor(Color.parseColor("#505050"));


        billAmount = (EditText) findViewById(R.id.totalBillAmnt);
        billAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double billAmountValue;

                if (billAmount.getText().toString().equals("")){
                    onBooleanBillChange(false);
                }

                else{
                    billAmountValue = Double.parseDouble(billAmount.getText().toString());
                    if (billAmountValue == 0){
                        onBooleanBillChange(false);
                    }

                    else{
                        onBooleanBillChange(true);
                    }
                }

            }
        });



        checkMark = (TextView) findViewById(R.id.checkMark);
        checkMark.setTextColor(Color.RED);
        checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> textToToast = new ArrayList<String>();
                textToToast.add("Please input the following:\n");
                double billAmountValue;

                if (billAmount.getText().toString().equals("")){
                    billAmountValue = 0.0;
                    textToToast.add("Bill Amount \n");

                }

                else{
                    billAmountValue = Double.parseDouble(billAmount.getText().toString());
                    if (billAmountValue == 0){
                        textToToast.add("Bill Amount \n");
                    }

                }

                double tipPercentage;

                if (percentageView.getText().toString().equals("")){
                    tipPercentage = 0.0;
                    textToToast.add("Tip Percentage\n");
                }

                else{
                    tipPercentage = Double.parseDouble(percentageView.getText().toString().substring(0,percentageView.getText().toString().length()-1));
                    if (tipPercentage == 0){
                        textToToast.add("Tip Percentage\n");
                    }
                }

                int numberOfPeople;

                if (peopleView.getText().toString().equals("")){
                    numberOfPeople = 0;
                    textToToast.add("Number of people who dined\n");
                }

                else{
                    numberOfPeople = Integer.parseInt(peopleView.getText().toString());
                    if (numberOfPeople == 0){
                        textToToast.add("Number of people who dined\n");
                    }
                }

                if (textToToast.size()>1){
                    String text = textToToast.get(0);

                    for(int i=1;i<textToToast.size(); i++){
                        text += textToToast.get(i);
                    }

                    text = text.substring(0,text.length()-1);
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                    toast.show();
                }

                else{
                    lauchCalculateTotal(tipPercentage/100,billAmountValue,numberOfPeople);
                }

            }
        });



//        mViewPager = (CustomViewPager) findViewById(R.id.mainPager);
//        mViewPager.setAdapter(new thipPagerAdapter(
//                getSupportFragmentManager()));
//
//
//        indicators = new TextView[]{
//                (TextView) findViewById(R.id.indicator1),
//                (TextView) findViewById(R.id.indicator2),
//                (TextView) findViewById(R.id.indicator3)
//        };
//
//        for (int i=0; i<3; i++){
//            indicators[i].setTypeface(fontAwesome);
//        }
//
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

                final LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.settings_page, null);

                final Spinner spinner = (Spinner)dialogView.findViewById(R.id.currency_spinner);
                final EditText tipPercentage = (EditText) dialogView.findViewById(R.id.percentage_default);

                tipPercentage.setText(String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage", 0)))));


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Currency",spinner.getSelectedItem().toString());
                        editor.apply();

                         double tipVal;

                        if (tipPercentage.getText().toString().trim().matches("")){

                        }

                        else{
                            tipVal = (Double.parseDouble(tipPercentage.getText().toString()));
                            editor.putLong("tipPercentage",Double.doubleToRawLongBits(tipVal));
                            editor.apply();
                            EditText totalTipAmnt = (EditText) findViewById(R.id.totalTipAmnt);
                            totalTipAmnt.setText(String.format("%.2f",(Double.longBitsToDouble(sharedPref.getLong("tipPercentage", 0)))));
                        }


                        String currencyText = sharedPref.getString("Currency", "Dollar");


                        if (currency == null){

                        }
                        else {
                            switch (currencyText) {
                                case "Dollar($)":
                                    currency.setText("\uf155");
                                    currencyTotalPage = "$";
                                    break;
                                case "Euro(€)":
                                    currency.setText("\uf153");
                                    currencyTotalPage ="€";
                                    break;
                                case "British Pound(£)":
                                    currency.setText("\uf154");
                                    currencyTotalPage ="£";
                                    break;
                                default:
                                    break;
                            }
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
                    case "Dollar($)":
                        spinner.setSelection(0);
                        break;
                    case "Euro(€)":
                        spinner.setSelection(2);
                        break;
                    case "British Pound(£)":
                        spinner.setSelection(1);
                        break;
                    default:
                        break;
                }

                dialog.show();
            }
        });

        tipPager = (ViewPager) findViewById(R.id.tipPager);
        tipPager.setAdapter(new TipPagerAdapter(
                getSupportFragmentManager()));

        pplPager = (ViewPager) findViewById(R.id.pplPager);
        pplPager.setAdapter(new PplPagerAdapter(
                getSupportFragmentManager()));
    }


    public void onBooleanBillChange(boolean changed) {
        billSet = changed;
        if (billSet && tipSet && pplSet){
            checkMark.setTextColor(Color.GREEN);
        }
        else{
            checkMark.setTextColor(Color.RED);
        }
    }

    public void onBooleanPplChange(boolean changed) {
        pplSet = changed;
        if (billSet && tipSet && pplSet){
            checkMark.setTextColor(Color.GREEN);
        }
        else{
            checkMark.setTextColor(Color.RED);
        }
    }

    public void onBooleanTipChange(boolean changed) {
        tipSet = changed;
        if (billSet && tipSet && pplSet){
            checkMark.setTextColor(Color.GREEN);
        }
        else{
            checkMark.setTextColor(Color.RED);
        }
    }

    public ViewPager getPager() {
        return mViewPager;
    }

    public ViewPager getPplPager() {
        return pplPager;
    }

    public ViewPager getTipPager() {
        return tipPager;
    }


    public void lauchCalculateTotal(double tipAmnt, double billAmtn, int pplAmnt){

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.summary_window, null, false), (int) (width * .95), (int) (height * .8), true);

            TextView xButton = (TextView) pw.getContentView().findViewById(R.id.exitPopUp);
            xButton.setTypeface(fontAwesome);
            xButton.setTextColor(Color.RED);
            xButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

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
            pplNumber.setText(String.valueOf(pplAmnt));
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

    public void percentageViewChange(String value){

        percentageView.setText(value+"%");

    }

    public void peopleViewChange(String value){

        peopleView.setText(value);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    }


