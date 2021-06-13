package com.example.medicineadvisor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineadvisor.R
import com.example.medicineadvisor.databinding.FetchMedicineLayoutBinding
import com.example.medicineadvisor.db.MedicineEntity
import java.util.*

class MedicineAdapter(var listner : RowClickListner) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    var medList = ArrayList<MedicineEntity>()

    fun setMedicineList(med : ArrayList<MedicineEntity>){
        this.medList = med
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        var layout = DataBindingUtil.inflate<FetchMedicineLayoutBinding>(LayoutInflater.from(parent.context),R.layout.fetch_medicine_layout,parent,false)
        return MedicineViewHolder(layout,listner)
    }

    override fun getItemCount(): Int {
        return medList.size
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {

        holder.itemView2.medicines = medList[position]
        holder.itemView2.root.setOnClickListener {
            listner.onItemClick(medList[position])
        }
        holder.itemView2.btnDeleteMedicine.setOnClickListener {
            listner.onDeleteClick(medList[position])
        }

    }

    inner class MedicineViewHolder(var itemView2:FetchMedicineLayoutBinding,listner: RowClickListner) : RecyclerView.ViewHolder(itemView2.root)

}

interface RowClickListner{
    fun onItemClick(med : MedicineEntity)
    fun onDeleteClick(med : MedicineEntity)
}