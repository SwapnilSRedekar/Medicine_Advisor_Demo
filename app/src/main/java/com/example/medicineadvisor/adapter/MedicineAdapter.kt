package com.example.medicineadvisor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineadvisor.R
import com.example.medicineadvisor.model.MedicineList

class MedicineAdapter(var listner : RowClickListner,var ctx :Context,var medList : MutableList<MedicineList>) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        var layout = LayoutInflater.from(ctx).inflate(R.layout.fetch_medicine_layout,parent,false)
        return MedicineViewHolder(layout,listner)
    }

    override fun getItemCount(): Int {
        return medList.size
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.medicineName.text = medList[position].fields.med_name
        holder.itemView.setOnClickListener{
            listner.onItemClick(medList[position])
        }
        holder.medDelete.setOnClickListener {
            listner.onDeleteClick(medList[position],position)
        }


    }

    inner class MedicineViewHolder(itemView:View,var listner: RowClickListner) : RecyclerView.ViewHolder(itemView){
        var medicineName = itemView.findViewById<TextView>(R.id.tv_FetchedMedicines)
        var medDelete = itemView.findViewById<Button>(R.id.btn_deleteMedicine)
    }

}

interface RowClickListner{
    fun onItemClick(med : MedicineList)
    fun onDeleteClick(med : MedicineList,position : Int)
}