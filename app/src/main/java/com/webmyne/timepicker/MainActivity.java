package com.webmyne.timepicker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText edtStart, edtEnd, sdate, edate;
    private TextView txtCalculate, txtResult, txtCdate, txtRdate;
    int val = 0;
    private Calendar calendar1;
    private String strStart, strEnd, strSdate, strEdate;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        edtStart = (EditText) findViewById(R.id.edtStartdate);
        edtEnd = (EditText) findViewById(R.id.edtEnddate);
        txtCalculate = (TextView) findViewById(R.id.txtCalculate);
        txtResult = (TextView) findViewById(R.id.txtResult);

        sdate = (EditText) findViewById(R.id.sDate);
        edate = (EditText) findViewById(R.id.eDate);
        txtCdate = (TextView) findViewById(R.id.cDate);
        txtRdate = (TextView) findViewById(R.id.rDate);
        context = this;

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(edtStart);
                // val = 1;
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(edtEnd);
                val = 2;
            }
        });

        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(sdate);
                val = 3;
            }
        });

        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(edate);
                val = 4;
            }
        });

        txtCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation1();

                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                String startTime = edtStart.getText().toString().trim();
                String endDate = edtEnd.getText().toString().trim();
                try {
                    Date Date1 = format.parse(startTime);
                    Date Date2 = format.parse(endDate);
                    long mills = Date2.getTime() - Date1.getTime();
                    int Hours = (int) (mills / (1000 * 60 * 60));
                    int Mins = (int) (mills % (1000 * 60 * 60));

                    String diff = Hours + "hr :" + Mins + "min";
                    txtResult.setText(diff);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

           /*   *//*  SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String startTime = edtStart.getText().toString().trim();
                String endDate = edtEnd.getText().toString().trim();

                try {
                    Date date1 = format.parse(startTime);
                    Date date2 = format.parse(endTate);
                    long diff = date2.getTime() - date1.getTime();
                    txtResult.setText("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                }*//*
                Calendar cal = Calendar.getInstance();
                long diff = cal.getTimeInMillis() - cal.getTimeInMillis();
                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diff);
                int hour = (int) (diffInSec / (60 * 60));
                int minremaining = (int) (diffInSec % (60 * 60));
                int min = (int) (minremaining / (60));
                txtResult.setText(String.format("%02d:%02d", hour, min));*/
            }
        });

        txtCdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation2();
                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                String startTime = sdate.getText().toString().trim();
                String endDate = edate.getText().toString().trim();
                try {
                    Date date1 = format.parse(startTime);
                    Date date2 = format.parse(endDate);
                    long mills = date2.getTime() - date1.getTime();
                    /*int Hours = (int) (mills/(1000 * 60 * 60));
                    int Mins = (int) (mills % (1000*60*60));*/

                    long Hours = TimeUnit.MILLISECONDS.toHours(mills);
                    long Mins = TimeUnit.MILLISECONDS.toMinutes(mills);

                    long actualMin;
                    if (Hours == 0) {
                        actualMin = Mins;
                    } else {
                        if (Mins == 60) {
                            actualMin = 0;
                        } else {
                            actualMin = (Mins / 60);
                        }
                    }

                    if (date1.after(date2)) {
                        Toast.makeText(context, "PLEASE SELECT PROPER START TIME", Toast.LENGTH_SHORT).show();
                        txtRdate.setText("");
                        return;
                    }
                    String diff = Hours + "hr : " + actualMin + "min";
                    txtRdate.setText(diff);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkValidation1() {
        strStart = edtStart.getText().toString().trim();
        strEnd = edtEnd.getText().toString().trim();


        if (TextUtils.isEmpty(strStart)) {
            Toast.makeText(context, "Please Enter Start Time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strEnd)) {
            Toast.makeText(context, "Please Enter End Time", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private void checkValidation2() {
        strSdate = sdate.getText().toString().trim();
        strEdate = edate.getText().toString().trim();

        if (TextUtils.isEmpty(strSdate)) {
            Toast.makeText(context, "Please Enter Start Time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(strEdate)) {
            Toast.makeText(context, "Please Enter End Time", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void showTimeDialog(final EditText edt) {
        if (edt == edtStart) {
            calendar1 = Calendar.getInstance();
            int hour = calendar1.get(Calendar.HOUR_OF_DAY);
            int minute = calendar1.get(Calendar.MINUTE);
            RangeTimePickerDialog rangeTimePickerDialog = new RangeTimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    setTextTime(hourOfDay, minute, edt);
                }
            }, hour, minute, false);
//            rangeTimePickerDialog.setMax(hour, minute);
            rangeTimePickerDialog.show();


           /* TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                     updateLable(val);
                    setTextTime(hourOfDay, minute, edt);
                }


            }, hour, minute, false);
            timePickerDialog.show();*/

        } else if (edt == edtEnd) {
            calendar1 = Calendar.getInstance();
            int hour = calendar1.get(Calendar.HOUR_OF_DAY);
            int minute = calendar1.get(Calendar.MINUTE);
            RangeTimePickerDialog rangeTimePickerDialog = new RangeTimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    setTextTime(hourOfDay, minute, edt);
                }
            }, hour, minute, false);
//            rangeTimePickerDialog.setMin(hour, minute);
            rangeTimePickerDialog.show();

        } else if (edt == sdate) {
            calendar1 = Calendar.getInstance();
            int hour = calendar1.get(Calendar.HOUR_OF_DAY);
            int minute = calendar1.get(Calendar.MINUTE);
            RangeTimePickerDialog rangeTimePickerDialog = new RangeTimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    setTextTime(hourOfDay, minute, edt);
                }
            }, hour, minute, false);
            rangeTimePickerDialog.show();

        } else if (edt == edate) {
            int hour = calendar1.get(Calendar.HOUR_OF_DAY);
            int minute = calendar1.get(Calendar.MINUTE);
            RangeTimePickerDialog rangeTimePickerDialog = new RangeTimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    setTextTime(hourOfDay, minute, edt);
                }
            }, hour, minute, false);
            rangeTimePickerDialog.show();
//            rangeTimePickerDialog.setMin(hour, minute);

        }
    }

    private void setTextTime(int hourOfDay, int minute, EditText edt) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        try {
            edt.setText(calToString(calendar));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("timeError", e.getMessage());
        }

    }

    private void updateLable(int val) {
    /*    DateFormat outputFormat = new SimpleDateFormat("HH:mm a");
        // String formattedTime = outputFormat.format(calendar1.getTime());
        String myFormat = "dd MMM, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (val == 1) {
            edtStart.setText(outputFormat.format(calendar1.get(Calendar.HOUR)), ((calendar1.get(Calendar.MINUTE)));
        } else if (val == 2) {
            // edtEnd.setText(sdf.format(calendar1.getTime()));
        } else if (val == 3) {
            // sdate.setText(sdf.format(calendar1.getTime()));
        } else if (val == 4) {
            //  edate.setText(sdf.format(calendar1.getTime()));
        }*/
    }

    public String calToString(Calendar cal) {
        String result = "";
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a");
        result = format1.format(cal.getTime());
        return result;
    }
}
