package com.example.accesshubsa.ui.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accesshubsa.data.local.ServiceRequest
import com.example.accesshubsa.databinding.ItemRequestBinding

class RequestAdapter(private var items: List<ServiceRequest>) :
    RecyclerView.Adapter<RequestAdapter.Holder>() {

    class Holder(private val binding: ItemRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ServiceRequest) {
            binding.tvRequestId.text = "Request #${item.id}"
            binding.tvRequestDescription.text = item.description
            binding.tvPriority.text = "Priority: ${item.priority}"
            binding.tvStatus.text = "Status: ${item.status}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(ItemRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    fun update(newItems: List<ServiceRequest>) {
        items = newItems
        notifyDataSetChanged()
    }
}
