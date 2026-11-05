package com.example.accesshubsa.ui.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accesshubsa.data.remote.ServiceResponse
import com.example.accesshubsa.databinding.ItemServiceBinding

class ServiceAdapter(
    private var services: List<ServiceResponse>,
    private val onClick: (ServiceResponse) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ServiceResponse, onClick: (ServiceResponse) -> Unit) {
            binding.tvServiceName.text = item.serviceName
            binding.tvServiceCategory.text = "Category: ${item.category}"
            binding.tvServiceDescription.text = item.description
            binding.btnViewService.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(services[position], onClick)

    override fun getItemCount() = services.size

    fun update(items: List<ServiceResponse>) {
        services = items
        notifyDataSetChanged()
    }
}
