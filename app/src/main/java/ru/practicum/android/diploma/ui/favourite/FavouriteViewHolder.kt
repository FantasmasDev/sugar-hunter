package ru.practicum.android.diploma.ui.favourite

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val vacancyName: TextView = itemView.findViewById(R.id.vacancyName)
    private val companyName: TextView = itemView.findViewById(R.id.companyName)
    private val financeCount: TextView = itemView.findViewById(R.id.financeCount)
    private val vacancyLogo: ImageView = itemView.findViewById(R.id.vacancyLogo)

    @SuppressLint("ResourceType")
    fun bind(vacancy: Vacancy) {
        //vacancyName.text = vacancy.?
        //companyName.text = vacancy.?
        //financeCount.text = vacancy.?
    }

}
