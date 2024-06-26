package ru.practicum.android.diploma.ui.filter.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter(id: String, private val onItemClick: (Industry) -> Unit) :
    RecyclerView.Adapter<IndustryAdapter.IndustryViewHolder>() {

    var industryList = ArrayList<Industry>()

    var industryId = id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return IndustryViewHolder(ItemIndustryBinding.inflate(layoutInspector, parent, false))
    }
    override fun getItemCount(): Int {
        return industryList.size
    }
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = industryList[position]
        holder.bind(industry)
    }

    inner class IndustryViewHolder(val binding: ItemIndustryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemCheck.setOnClickListener {
                val industry = industryList[bindingAdapterPosition]
                selection(industry)
                onItemClick.invoke(industry)
            }
        }

        fun bind(industry: Industry) {
            binding.itemCheck.text = industry.name
            if (industryId == industry.id) {
                selected()
            } else {
                unselected()
            }
        }

        private fun selection(selectedIndustry: Industry) {
            val currentSelectedIndustry = industryList.find { it.id == industryId }
            val selectedIndex = industryList.indexOf(currentSelectedIndustry)
            if (currentSelectedIndustry != selectedIndustry) {
                industryId = selectedIndustry.id
                if (selectedIndex != -1) {
                    notifyItemChanged(selectedIndex)
                }
                notifyItemChanged(bindingAdapterPosition)
            }
        }

        private fun selected() {
            binding.itemCheck.isChecked = true
        }

        private fun unselected() {
            binding.itemCheck.isChecked = false
        }
    }
}
