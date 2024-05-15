package ru.practicum.android.diploma.ui.search.recyclerview

import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemEmptyBinding
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class VacancyViewHolder(
        private val binding: ItemVacancyBinding
    ) : SearchViewHolder(binding) {

        companion object {
            const val RADIUS_IN_DP = 12f
        }

        fun bind(model: Vacancy) {
            Glide.with(binding.vacancyLogo)
                .load(model.logos?.logo240)
                .placeholder(R.drawable.vacancy_no_image_holder)
                .transform(RoundedCorners(dpToPx(itemView.context)))
                .into(binding.vacancyLogo)

            binding.vacancyName.text = model.title
            binding.companyName.text = model.employer
            binding.financeCount.text = "от ${model.salary?.from} до ${model.salary?.to}"
        }

        private fun dpToPx(context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                RADIUS_IN_DP,
                context.resources.displayMetrics
            ).toInt()
        }

    }

    class EmptyViewHolder(
        private val binding: ItemEmptyBinding
    ) : SearchViewHolder(binding)
}
