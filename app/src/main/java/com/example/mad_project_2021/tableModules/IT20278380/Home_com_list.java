package com.example.mad_project_2021.tableModules.IT20278380;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mad_project_2021.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class Home_com_list extends RecyclerView.Adapter<Home_com_list.HomeComHolder> {

    Context context;

    ArrayList<Home_Computer> list;

    Spinner orSpin;
    String province;

    public Home_com_list(Context context, ArrayList<Home_Computer> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeComHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cus_home_cmputer,parent,false);
        return new HomeComHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeComHolder holder, int position) {

        Home_Computer home_computer = list.get(position);
        holder.name.setText(home_computer.getNew_Cname());
        holder.generaion.setText(home_computer.getnew_Cgenarate());
        holder.description.setText(home_computer.getNew_Cdescription());
        holder.price.setText(home_computer.getNew_Cprice());
        Glide.with(context).load(list.get(position).getNew_Cimage()).into(holder.image);

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.order))
                        .setExpanded(true,1100)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText address = view.findViewById(R.id.order_add);
                EditText number = view.findViewById(R.id.order_num);

                orSpin = view.findViewById(R.id.rder_spinner);

                TextView iname = view.findViewById(R.id.order_iname);
                TextView iprice = view.findViewById(R.id.order_price);
                Button order = view.findViewById(R.id.order_but);

                iname.setText(home_computer.getNew_Cname());
                iprice.setText(home_computer.getNew_Cprice());

                //spinner
                String[] objects = { "Western", "North Western", "Central", "Eastern", "North Central",
                        "Northern", "Sabaragamuwa", "Southern", "Uva"};

                ArrayAdapter adapter = new ArrayAdapter(context.getApplicationContext(), android.R.layout.simple_list_item_1, objects);

                orSpin.setAdapter(adapter);
                orSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context.getApplicationContext(),
                                orSpin.getItemAtPosition(position).toString(), Toast.LENGTH_LONG)
                                .show();

                        province = orSpin.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dialogPlus.show();

                order.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        DatabaseReference order = FirebaseDatabase.getInstance().getReference().child("Order_Items").child(province.toString().toString());

                        String Oaddres = address.getText().toString();
                        String Onumber = number.getText().toString();
                        String Oprovince = province;
                        String Oiname = iname.getText().toString();
                        String Oiprice = iprice.getText().toString();

                        Order order0 = new Order(Oaddres, Onumber, Oprovince, Oiname, Oiprice);

                        if(Oaddres.isEmpty() || Onumber.isEmpty() || Oprovince.isEmpty() || Oiname.isEmpty()){
                            Toast.makeText(address.getContext(), "Fill All Details", Toast.LENGTH_SHORT).show();

                        }else {
                            order.child(number.getText().toString()).setValue(order0);
                            Toast.makeText(address.getContext(), "Item Orded", Toast.LENGTH_SHORT).show();

                            dialogPlus.dismiss();
                        }
                    }
                });



            }

        });
    }


    @Override
    public int getItemCount() {
        return  list.size();
    }

    public static class HomeComHolder extends RecyclerView.ViewHolder{

        TextView name, generaion, description, price;
        Button order;
        ImageView image;

        public HomeComHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name45);
            generaion = itemView.findViewById(R.id.en45);
            description = itemView.findViewById(R.id.des45);
            price = itemView.findViewById(R.id.price45);
            order = itemView.findViewById(R.id.order45);
            image = itemView.findViewById(R.id.image45);
        }
    }

}
