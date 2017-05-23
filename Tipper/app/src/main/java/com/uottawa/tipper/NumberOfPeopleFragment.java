package com.uottawa.tipper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by filipslatinac on 2017-05-11.
 */
public class NumberOfPeopleFragment extends Fragment {
    private View rootView;
    private Typeface fontAwesome;
    private TextView checkMark;
    private TextView arrowLeft;



    ArrayList<String> numberOfPpl = new ArrayList<>();
    private MaterialBetterSpinner spinner = null;

    private booleanPplPass dataPasser;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (booleanPplPass) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        numberOfPplFiller();

        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");

        rootView = inflater.inflate(R.layout.people_amnt, container,
                false);

        arrowLeft = (TextView) rootView.findViewById(R.id.arrowLeft);

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ((MainActivity) getActivity()).getPager().setCurrentItem(1);
                ((MainActivity) getActivity()).changeIndicatorLevel(2);
            }
        });

        arrowLeft.setTypeface(fontAwesome);
        arrowLeft.setTextColor(Color.parseColor("#32A0A0"));
        arrowLeft.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));

        checkMark = (TextView) rootView.findViewById(R.id.checkMark);
        checkMark.setTypeface(fontAwesome);
        checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("People",spinner.getText().toString());
                editor.apply();
//                ((MainActivity) getActivity()).lauchCalculateTotal();
            }
        });

        spinner = (MaterialBetterSpinner) rootView.findViewById(R.id.ppl_spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.single_listview_item, R.id.txtitem, numberOfPpl);
        spinner.setText(sharedPref.getString("People","1"));
        spinner.setAdapter(adapter);
        dataPasser.onBooleanPplChange(true,Integer.parseInt(sharedPref.getString("People","1")));

        checkMark.setTextColor(Color.parseColor("#32A0A0"));
        checkMark.startAnimation(AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left));

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int value = 0;
                String valueString = spinner.getText().toString();
                if(valueString.equals("More")){

                    final LayoutInflater inflater = getActivity().getLayoutInflater();

                    final View dialogView = inflater.inflate(R.layout.ppl_amnt_dialog, null);

                    final AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setPositiveButton(android.R.string.ok, null)
                            .setNegativeButton(android.R.string.cancel, null)
                            .setView(dialogView)
                            .create();

                    dialog.setOnCancelListener(
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    numberOfPplFiller();
                                    spinner.setText(sharedPref.getString("People","1"));
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.single_listview_item, R.id.txtitem, numberOfPpl);
                                    spinner.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }
                    );

                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                        @Override
                        public void onShow( final DialogInterface dialog) {

                            Button buttonOk = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                            buttonOk.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    String entered = ((EditText)dialogView.findViewById(R.id.totalpplAmnt)).getText().toString();

                                    if (entered.equals("") || entered.equals("0")){
                                        Toast toast = Toast.makeText(getActivity()
                                                .getApplicationContext(), "Please enter the number of people or cancel", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }

                                    else{
                                        updateNumberOfPpl(Integer.parseInt(entered));
                                        spinner.setText(numberOfPpl.get(numberOfPpl.size()-1));
                                        spinner.setAdapter(adapter);
                                        dataPasser.onBooleanPplChange(true,Integer.parseInt(entered));
                                        dialog.dismiss();
                                    }
                                }
                            });

                            Button buttonCancel = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                            buttonCancel.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    numberOfPplFiller();
                                    spinner.setText(sharedPref.getString("People","1"));
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.single_listview_item, R.id.txtitem, numberOfPpl);
                                    spinner.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            });

                        }
                    });

                    dialog.show();
                }

                else{
                    value = Integer.parseInt(valueString);
                }
                dataPasser.onBooleanPplChange(true,value);
            }
        });

        return rootView;
    }

    private void numberOfPplFiller(){
        numberOfPpl = new ArrayList<String>();
        for(int i=1;i<=5;i++){
            numberOfPpl.add(String.valueOf(i));
        }
        numberOfPpl.add("More");
    }

    private void updateNumberOfPpl(int addition){
        numberOfPplFiller();
        numberOfPpl.add(String.valueOf(addition));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, numberOfPpl);
        spinner.setAdapter(adapter);
    }
}
