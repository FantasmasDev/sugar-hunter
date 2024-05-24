package ru.practicum.android.diploma.ui.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterViewModel>()
    private var filters: MutableMap<String, String> = mutableMapOf()
    private var oldFilters: MutableMap<String, String> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("areaFilters") { _, bundle ->
            if (!bundle.isEmpty) {
                with (bundle) {
                    Log.d("BUNDLE_TAG", "${bundle.getString("regionName")} ${bundle.getString("regionId")} ${bundle.getString("countryName")}")
                    getString("regionName")?.let {
                        filters["regionName"] = it
                    }
                    getString("regionId")?.let {
                        filters["area"] = it
                    }
                    getString("countryName")?.let {
                        filters["countryName"] = it
                    } ?: filters.remove("countryName")
                }
                setStatements()
            }
        }

        filters = viewModel.getFilters()
        oldFilters.putAll(filters)

        setStatements()

        binding.selectRegionActionButton.setOnClickListener{
            when (binding.selectRegionActionButton.tag) {
                "clear" -> {
                    filters.remove("regionName")
                    filters.remove("area")
                    filters.remove("countryName")
                    binding.selectRegionActionButton.setImageResource(R.drawable.leading_icon_filter)
                    binding.selectRegionActionButton.tag = "arrow"
                    setStatements()
                }
                "arrow" -> selectRegionClick()
            }
        }

        binding.salaryEdit.addTextChangedListener(getTextWatcher())

        binding.salaryClearButton.setOnClickListener {
            binding.salaryEdit.text.clear()
            salaryHeaderColor(null)
        }

        binding.salaryEdit.onFocusChangeListener = OnFocusChangeListener { _, isFocus ->
            salaryEditOnFocusChangeListener(isFocus)
        }

        binding.buttonDecline.setOnClickListener {
            filters.clear()
            salaryHeaderColor(null)
            setStatements()
        }

        binding.buttonApply.setOnClickListener {
            Log.d("FILTERS_TAG", filters.toString())
            viewModel.updateFilters(filters)
            setFragmentResult("requestKey", bundleOf("isApplyButton" to true))
            findNavController().popBackStack(R.id.searchFragment, false)
        }

        binding.selectIndustryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_choiceSphereFragment)
        }
        binding.selectRegionLayout.setOnClickListener { selectRegionClick() }

        binding.salaryCheckBox.setOnClickListener {
            salaryCheckBoxProcessing()
        }

        binding.backButton.setOnClickListener { exit() }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exit()
                }
            }
        )
    }

    private fun salaryCheckBoxProcessing() {
        binding.salaryEdit.clearFocus()
        when (binding.salaryCheckBox.isChecked) {
            true -> filters[ONLY_WITH_SALARY] = "true"
            false -> filters.remove(ONLY_WITH_SALARY)
        }
        setStatements()
    }

    private fun salaryEditOnFocusChangeListener(isFocus: Boolean) {
        when (isFocus) {
            true -> salaryHeaderColor(true)
            false -> {
                if (filters[SALARY].isNullOrEmpty()) {
                    salaryHeaderColor(null)
                } else {
                    salaryHeaderColor(false)
                }
            }
        }
    }

    private fun selectRegionClick() {
        setFragmentResult(
            "setAreaFromFilters", bundleOf(
                "regionName" to filters["regionName"],
                "regionId" to filters["area"],
                "countryName" to filters["countryName"],
            )
        )
        findNavController().navigate(R.id.action_filterFragment_to_choicePlaceFragment)
    }
    private fun salaryHeaderColor(isFocus: Boolean?) {
        when (isFocus) {
            true -> {
                binding.salaryHeader.setTextColor(requireContext().getColorStateList(R.color.salary_header_focus))
            }
            null -> {
                binding.salaryHeader.setTextColor(requireContext().getColorStateList(R.color.salary_header_default))
            }
            false -> {
                binding.salaryHeader.setTextColor(requireContext().getColorStateList(R.color.salary_header_not_empty))
            }
        }
    }

    private fun setStatements() {
        Log.d("SET_STATEMENTS_TAG", filters.toString())
        binding.buttonApply.isVisible = oldFilters != filters
        if (filters.isNotEmpty()) {
            filters.keys.forEach { key ->
                when (key) {
                    SALARY -> {
                        binding.salaryEdit.setText(filters[key])
                        binding.salaryClearButton.isVisible = true
                        salaryHeaderColor(false)
                    }
                    ONLY_WITH_SALARY -> binding.salaryCheckBox.isChecked = true
                    INDUSTRY -> Unit
                    AREA -> renderArea()
                }
                binding.buttonDecline.isVisible = true
            }
        } else {
            binding.buttonDecline.isVisible = false
            binding.salaryCheckBox.isChecked = false
            binding.salaryEdit.text.clear()
            binding.selectedRegionsText.isVisible = false
        }
    }

    private fun renderArea() {
        if (filters["countryName"].isNullOrEmpty()) {
            binding.selectedRegionsText.text = filters["regionName"]
        } else {
            val st = "${filters["countryName"]}, ${filters["regionName"]}"
            binding.selectedRegionsText.text = st
        }
        binding.selectRegionActionButton.setImageResource(R.drawable.clear_button)
        binding.selectRegionActionButton.tag = "clear"
        binding.selectedRegionsText.isVisible = true
    }

    private fun getTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // empty
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) {
                filters.remove(SALARY)
                binding.salaryClearButton.isVisible = false
                setStatements()
            } else {
                filters[SALARY] = s.toString()
                binding.buttonApply.isVisible = oldFilters != filters
                binding.buttonDecline.isVisible = true
                binding.salaryClearButton.isVisible = true
            }
        }
        override fun afterTextChanged(s: Editable?) {
            // empty
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("FILTERS_TAG", filters.toString())
        viewModel.updateFilters(filters)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun exit() {
        Log.d("FILTERS_TAG", filters.toString())
        viewModel.updateFilters(filters)
        setFragmentResult("requestKey", bundleOf("isApplyButton" to false))
        findNavController().popBackStack(R.id.searchFragment, false)
    }

    companion object {
        private const val AREA = "area"
        private const val INDUSTRY = "industry"
        private const val SALARY = "salary"
        private const val ONLY_WITH_SALARY = "only_with_salary"
    }
}
