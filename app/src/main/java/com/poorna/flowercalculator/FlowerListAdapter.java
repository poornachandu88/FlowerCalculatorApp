package com.poorna.flowercalculator;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;
import android.support.v7.app.AlertDialog;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.List;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import com.poorna.flowercalculator.Flowers;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.database.sqlite.SQLiteDatabase;

import android.widget.ImageButton;

public class FlowerListAdapter extends ArrayAdapter<Flowers>

{
    private static final String TAG = "FlowerListAdapter";

    SQLiteDataBaseClass database;
    private Context mCtx;
    ArrayList<Flowers> flowerslist;
    Toast toast;
    TextView text;
    ListView listView;
    private int mResource;
    private int lastPosition = -1;
    SQLiteDatabase mDatabase;
    Double grandtotaldb;
   // Double grandtotaldb;



    private static class ViewHolder {

        TextView textViewId,textViewDate,textViewCustomerName,textViewMobileNo,textViewCost,textViewQuantity,textViewTotal;
        TextView grandtotal;
        ImageButton ButtonEdit,ButtonDelete;

    }


    public FlowerListAdapter(Context mCtx, int resource, ArrayList<Flowers> flowerslist, SQLiteDataBaseClass database)
    {
        super(mCtx, resource, flowerslist);
        this.mCtx = mCtx;
        this.mResource = resource;
        this.flowerslist = flowerslist;
        this.database = database;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)

    {
        final View result;
         View row=convertView;
        //ViewHolder object
        ViewHolder holder;
        if(row == null){


            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(mResource, null);
            holder= new ViewHolder();

            holder.textViewId=row.findViewById(R.id.textViewIdlist);
            holder.textViewDate = row .findViewById(R.id.textViewDatelist);
            holder.textViewCustomerName = row .findViewById(R.id.textViewCustomerNamelist);
            holder.textViewMobileNo = row .findViewById(R.id.textViewMobileNolist);
            holder.textViewCost = row .findViewById(R.id.textViewCostlist);
            holder.textViewQuantity = row .findViewById(R.id.textViewQuantitylist);
            holder.textViewTotal = row .findViewById(R.id.textViewTotallist);
            holder.grandtotal = row .findViewById(R.id.grandtotal);




            result = row;

            row.setTag(holder);

        }
        else{
            holder = (ViewHolder) row.getTag();
            result = row;
        }


        Animation animation = AnimationUtils.loadAnimation(mCtx,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;



        final Flowers flower=flowerslist.get(position);

        holder.textViewId.setText(""+flower.getId());
        holder.textViewCustomerName.setText("" + flower.getCustomername());
        holder.textViewMobileNo.setText("" + flower.getMobileno());
        holder.textViewDate.setText(""+flower.getDateofpurchase());
        holder.textViewCost.setText(""+flower.getCostofflowers());
        holder.textViewQuantity.setText("" + flower.getQuantityofflowers());
        holder.textViewTotal.setText("" + flower.getTotalamountofflowers());


        holder.ButtonEdit = row.findViewById(R.id.editlist);
        holder.ButtonDelete = row.findViewById(R.id.deletelist);

        final int positionPopup = position;

        //adding a clicklistener to button
        holder.ButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(flower);
            }
        });

        holder.ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(flower,positionPopup);
            }


        });

        String outputtotal=null;
        if(position==flowerslist.size()-1){//check if list last element
            //show your total count view here---- and add total amount
            grandtotaldb=database.getTotalcost();

            outputtotal="Grand Total :" + grandtotaldb;


        }
        holder.grandtotal.setText(outputtotal);
        return  row;
    }






   private void deleteEmployee(final Flowers flower, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //database.deleteFlowers(flower);
               int status= database.deleteFlowers(flower);

               if(status==1) {
                   toast = Toast.makeText(mCtx, "Customer Deleted", Toast.LENGTH_LONG);
                   text = (TextView) toast.getView().findViewById(android.R.id.message);
                   text.setTextColor(Color.RED);
                   toast.show();
               }
                flowerslist.remove(position);


               //flowerslist.clear();

                notifyDataSetChanged();




            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();



    }



    private void updateEmployee(final Flowers flower)

    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.edit_popup, null);
        builder.setView(view);

        final EditText CustomerNameEdit = view.findViewById(R.id.editTextCustomerName);
        final EditText MobileNoEdit = view.findViewById(R.id.editTextMobileNo);
        final EditText DateEdit = view.findViewById(R.id.editTextDate);
        final EditText CostEdit = view.findViewById(R.id.editTextCost);
        final EditText QuantityEdit = view.findViewById(R.id.editTextQuantity);
        final EditText TotalEdit = view.findViewById(R.id.editTextTotal);



        CustomerNameEdit.setText("" + flower.getCustomername());
        MobileNoEdit.setText("" + flower.getMobileno());
        DateEdit.setText(""+flower.getDateofpurchase());
        CostEdit.setText(""+flower.getCostofflowers());
        QuantityEdit.setText("" + flower.getQuantityofflowers());
        TotalEdit.setText("" + flower.getTotalamountofflowers());


        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String DateEditStr = DateEdit.getText().toString();
                String CustomerNameEditStr = CustomerNameEdit.getText().toString();
                String MobileNoEditStr = MobileNoEdit.getText().toString();
                String CostEditStr = CostEdit.getText().toString();
                String QuantityStr = QuantityEdit.getText().toString();
                String TotalEditStr = TotalEdit.getText().toString();

                flower.setDateofpurchase(DateEditStr);
                flower.setCustomername(CustomerNameEditStr);
                flower.setMobileno(MobileNoEditStr);
                flower.setCostofflowers(Double.valueOf(CostEditStr));
                flower.setQuantityofflowers(Double.valueOf(QuantityStr));
                flower.setTotalamountofflowers(Double.valueOf(TotalEditStr));

                database.updateFlowers(flower);


                toast = Toast.makeText(mCtx, "Customer Updated", Toast.LENGTH_LONG);
                text = (TextView) toast.getView().findViewById(android.R.id.message);
                text.setTextColor(Color.BLUE);
                toast.show();

                notifyDataSetChanged();
                notifyDataSetInvalidated();

                dialog.dismiss();
            }
        });

        view.findViewById(R.id.textViewTotalbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String CostEditStr = CostEdit.getText().toString();
                String QuantityStr = QuantityEdit.getText().toString();

                Double cost=Double.valueOf(CostEditStr);
                Double quantity=Double.valueOf(QuantityStr);
                Double total=cost*quantity;
                String res=String.valueOf(total);
                TotalEdit.setText(res);


            }
        });


    }

    private void totaldisp()
    {



       /* grandtotaldb=database.getTotalcost();
        grandtotal=view.findViewById(R.id.grandtotal);
        grandtotal.setText("Grand Total :"+grandtotaldb);*/
    }


}
