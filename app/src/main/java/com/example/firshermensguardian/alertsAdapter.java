//package com.example.firshermensguardian;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class alertsAdapter extends RecyclerView.Adapter<alertsAdapter.ViewHolder> {
//    Context context;
//    List<alerts>alertsList;
//
//    public alertsAdapter(Context context,List<alerts>alertsList) {
//        this.context = context;
//        this.alertsList=alertsList;
//    }
//
//    @NonNull
//    @Override
//    public alertsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View view= LayoutInflater.from(context).inflate(R.layout.single_alert,parent,false);
//
//        return new  ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull alertsAdapter.ViewHolder holder, int position) {
//  if(alertsList!=null&&alertsList.size()>0){
//      alerts alert=alertsList.get(position);
//      holder.sNo.setText(alert.getsNo());
//      holder.name.setText(alert.getName());
//      holder.coOrdinates.setText(alert.getCoOrdinates());
//  }
//    }
//
//    @Override
//    public int getItemCount() {
//        return alertsList.size();
//    }
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView sNo,name,coOrdinates;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//         sNo=itemView.findViewById(R.id.alert_sNo);
//         name=itemView.findViewById(R.id.alert_name);
//         sNo=itemView.findViewById(R.id.alert_coOrdinates);
//
//        }
//    }
//}
