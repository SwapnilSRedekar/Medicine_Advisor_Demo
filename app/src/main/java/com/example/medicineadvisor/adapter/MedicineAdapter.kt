package com.example.medicineadvisor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineadvisor.R
import com.example.medicineadvisor.db.MedicineEntity
import com.example.medicineadvisor.model.MedicineList
import java.util.ArrayList

class MedicineAdapter(var listner : RowClickListner) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    var medList = ArrayList<MedicineEntity>()


    fun setMedicineList(med : ArrayList<MedicineEntity>){
        this.medList = med
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        var layout = LayoutInflater.from(parent.context).inflate(R.layout.fetch_medicine_layout,parent,false)
        return MedicineViewHolder(layout,listner)
    }

    override fun getItemCount(): Int {
        return medList.size
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.medicineName.text = medList[position].med_name
        holder.itemView.setOnClickListener{
            listner.onItemClick(medList[position])
        }
        holder.medDelete.setOnClickListener {
            listner.onDeleteClick(medList[position])
        }


    }

    inner class MedicineViewHolder(itemView:View,listner: RowClickListner) : RecyclerView.ViewHolder(itemView){
        var medicineName = itemView.findViewById<TextView>(R.id.tv_FetchedMedicines)
        var medDelete = itemView.findViewById<Button>(R.id.btn_deleteMedicine)
    }

}

interface RowClickListner{
    fun onItemClick(med : MedicineEntity)
    fun onDeleteClick(med : MedicineEntity)
}