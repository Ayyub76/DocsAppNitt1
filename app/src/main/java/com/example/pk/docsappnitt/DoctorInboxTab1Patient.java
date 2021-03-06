package com.example.pk.docsappnitt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DoctorInboxTab1Patient extends Fragment{
    private static final String TAB="Tab1Fragment";
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private MessageToDoctor msgToDoctor;
    FirebaseRecyclerAdapter<MessageToDoctor, ViewHolder> firebaseRecyclerAdapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.doctorinboxtab1patient,container,false);
        swipe=view.findViewById(R.id.swipe);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager();
        final LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("DoctorInBox").child("Patient");
        databaseReference.keepSynced(true);

        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<MessageToDoctor, ViewHolder>(MessageToDoctor.class
                ,R.layout.messagetodoctor,ViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final MessageToDoctor model, int position) {
                viewHolder.txtView.setText(model.getFrom());
                viewHolder.txtTime.setText(model.getTime());
                viewHolder.txtSubject.setText(model.getSubject());
                viewHolder.txtDate.setText(model.getDate());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),MessageViewForDoctor.class);
                        intent.putExtra("AgeKey",model.getAge());
                        intent.putExtra("SubjectKey",model.getSubject());
                        intent.putExtra("PtNameKey",model.getPatientName());
                        intent.putExtra("DateKey",model.getDate());
                        intent.putExtra("TimeKey",model.getTime());
                        intent.putExtra("PatientNameKey",model.getPatientName());
                        intent.putExtra("MobileKey",model.getPatientPhoneNumber());
                        intent.putExtra("GenderKey",model.getGender());
                        intent.putExtra("BloodGroupKey",model.getBloodGroup());
                        intent.putExtra("ProblemKey",model.getProblem());
                        intent.putExtra("AppointmentTimeKey",model.getAppointmentTime());
                        intent.putExtra("PatientIdKey",model.getPatientId());
                        intent.putExtra("AddressKey",model.getAddress());
                        intent.putExtra("PatientIdKey",model.getPatientId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public MessageToDoctor getItem(int position) {
                return super.getItem(getItemCount()-1-position);
            }

            @Override
            protected void onDataChanged() {
                firebaseRecyclerAdapter.notifyDataSetChanged();
                super.onDataChanged();
            }
        };

        firebaseRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                firebaseRecyclerAdapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
        /*if(firebaseRecyclerAdapter.getItemCount()==0){
            view.findViewById(R.id.NoMessages).setVisibility(View.VISIBLE);
        }
        else{
            view.findViewById(R.id.NoMessages).setVisibility(View.GONE);
        }*/

        return view;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtView,txtTime,txtSubject,txtDate;
        View view;
        public ViewHolder(View itemView){
            super(itemView);
            txtView=(TextView)itemView.findViewById(R.id.From);
            txtTime=(TextView)itemView.findViewById(R.id.Time);
            txtDate=(TextView)itemView.findViewById(R.id.Date);
            txtSubject=(TextView)itemView.findViewById(R.id.Subject);
            view=itemView;
        }

    }


}
