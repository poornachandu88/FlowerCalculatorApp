package com.poorna.flowercalculator;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import 	java.text.SimpleDateFormat;
import  java.util.Calendar;



public class FlowerCalculator extends AppCompatActivity implements View.OnClickListener
{


    SQLiteDataBaseClass database;
    Activity activity;
    EditText editTextCustomerName, editTextMobileNo, editTextDate, editTextCost, editTextQuantity, editTextTotal;
    Button btnSave,btnList,btnTotal,btnReset,mywebsite;
    View view;
    TextView text;
    Toast toast;
    String CustomerNameEditStr,MobileNoEditStr,DateEditStr,CostEditStr,QuantityStr,TotalEditStr;
    Double grandtotaldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flower_calculator);
        database = new SQLiteDataBaseClass(this);


        editTextCustomerName = (EditText) findViewById(R.id.editTextCustomerName);
        editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNo);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate.setText(formattedDate);


        editTextCost = (EditText) findViewById(R.id.editTextCost);
        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        editTextTotal = (EditText) findViewById(R.id.editTextTotal);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnList = (Button) findViewById(R.id.btnList);//for list
        btnTotal = (Button) findViewById(R.id.btnTotal);
        btnReset = (Button) findViewById(R.id.btnReset);
        mywebsite=(Button) findViewById(R.id.mywebsite);

        btnSave.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnTotal.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        mywebsite.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:

                addFlowers();

                break;
            case R.id.btnList:

                getFlowersList();

                break;
            case R.id.btnTotal:

                getTotalAmount();

                break;
            case R.id.btnReset:

                resetFlowers();

                break;
            case R.id.mywebsite:

                gotoonlineweb();

                break;
        }
    }


    private void  getTotalAmount() {

               CostEditStr = editTextCost.getText().toString().trim();
               QuantityStr = editTextQuantity.getText().toString().trim();

               if (inputcheck(CostEditStr, QuantityStr))
               {


                   String CostEditStr = editTextCost.getText().toString();
                   String QuantityStr = editTextQuantity.getText().toString();
                   double cost = Double.valueOf(CostEditStr);
                   double quantity = Double.valueOf(QuantityStr);
                   double total = cost * quantity;
                   editTextTotal.setText("" + String.valueOf(total));
               }
           }

    private boolean inputcheck(String costEditStr, String quantityStr) {
        if (costEditStr.isEmpty()) {
            editTextCost.setError("Please enter Cost");
            editTextCost.requestFocus();
            return false;
        }
        if (quantityStr.isEmpty()) {
            editTextQuantity.setError("Please enter Quantity");
            editTextQuantity.requestFocus();
            return false;
        }

        return true;
    }

        private void resetFlowers() {

                        editTextCustomerName.setText("");
                        editTextMobileNo.setText("");
                       //editTextDate.setText("");
                        editTextCost.setText("");
                        editTextQuantity.setText("");
                        editTextTotal.setText("");
                    }
               private void addFlowers(){

                        CustomerNameEditStr = editTextCustomerName.getText().toString().trim();
                         MobileNoEditStr = editTextMobileNo.getText().toString().trim();
                        DateEditStr = editTextDate.getText().toString().trim();
                         CostEditStr = editTextCost.getText().toString().trim();
                         QuantityStr = editTextQuantity.getText().toString().trim();
                        TotalEditStr = editTextTotal.getText().toString().trim();

          if (inputsAreCorrect(CustomerNameEditStr, MobileNoEditStr,CostEditStr,QuantityStr,TotalEditStr)) {




              Flowers flower = new Flowers(CustomerNameEditStr, MobileNoEditStr, DateEditStr, Double.valueOf(CostEditStr),Double.valueOf( QuantityStr),Double.valueOf( TotalEditStr));
              boolean isInserted = database.addFlowers(flower);

              if (isInserted == true) {

                  //Toast.makeText(FlowerCalculator.this, "Data Inserted", Toast.LENGTH_LONG);
                  toast = Toast.makeText(FlowerCalculator.this, "Data Inserted", Toast.LENGTH_LONG);
                  text = (TextView) toast.getView().findViewById(android.R.id.message);
                  text.setTextColor(Color.BLUE);
                  toast.show();
                  //  TotalEditStr="";
                  editTextCustomerName.setText("");
                  editTextMobileNo.setText("");
                  Calendar c = Calendar.getInstance();
                  SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                  String formattedDate = df.format(c.getTime());
                  editTextDate = (EditText) findViewById(R.id.editTextDate);
                  editTextDate.setText(formattedDate);

                  editTextCost.setText("");
                  editTextQuantity.setText("");
                  editTextTotal.setText("");


                  // textViewId.setText(""+flower.getId());
              } else {
                  //   Toast.makeText(FlowerCalculator.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                  toast = Toast.makeText(FlowerCalculator.this, "Data not Inserted", Toast.LENGTH_LONG);
                  text = (TextView) toast.getView().findViewById(android.R.id.message);
                  text.setTextColor(Color.RED);
                  toast.show();
              }

          }
                        }




    private void getFlowersList() {

      //  grandtotaldb=database.getTotalcost();

        Intent intent = new Intent(FlowerCalculator.this, CustomFlowersList.class);
        intent.putExtra("sample_name","Note : Above Grand Total will be Affected after Updations and Deletions ");

        startActivity(intent);


                    }

    private void gotoonlineweb() {
        Intent intent = new Intent(FlowerCalculator.this, MyWebsiteActivity.class);

        startActivity(intent);
    }


    private boolean inputsAreCorrect(String customerNamEditStr, String mobileNoEditStr, String costEditStr, String quantityStr, String totalEditStr)
    {

        if (customerNamEditStr.isEmpty()) {
            editTextCustomerName.setError("Please enter name");
            editTextCustomerName.requestFocus();
            return false;
        }
        if (mobileNoEditStr.isEmpty()) {
            editTextMobileNo.setError("Please enter mobileno");
            editTextMobileNo.requestFocus();
            return false;
        }

        if (costEditStr.isEmpty()) {
            editTextCost.setError("Please enter Cost");
            editTextCost.requestFocus();
            return false;
        }
        if (quantityStr.isEmpty()) {
            editTextQuantity.setError("Please enter Quantity");
            editTextQuantity.requestFocus();
            return false;
        }
        if (totalEditStr.isEmpty()) {
            editTextTotal.setError("Click On Total button ");
            editTextTotal.requestFocus();
            return false;
        }


        return true;
    }


    }

















