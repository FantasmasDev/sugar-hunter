package ru.practicum.android.diploma.ui.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoicePlaceBinding

class ChoicePlaceFragment : Fragment() {

    private var _binding: FragmentChoicePlaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ChoicePlaceViewModel>()
    private var chosenCountry = String()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoicePlaceBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("setArea") { _, bundle ->
            if (!bundle.isEmpty) viewModel.setArea(bundle)
        }
        setFragmentResultListener("setAreaFromFilters") { _, bundle ->
            if (!bundle.isEmpty) viewModel.setAreaFromFilters(bundle)
        }

        binding.buttonApply.setOnClickListener {
            viewModel.savePlace()
            setFragmentResult("areaFilters", viewModel.savePlace())
            findNavController().popBackStack(R.id.filterFragment, false)
        }

        binding.selectCountryActionButton.setOnClickListener {
            when (binding.selectCountryActionButton.tag) {
                "clear" -> viewModel.clearCountry()
                "arrow" -> findNavController().navigate(R.id.action_choicePlaceFragment_to_countryFragment)
            }
        }

        binding.selectRegionActionButton.setOnClickListener {
            when (binding.selectRegionActionButton.tag) {
                "clear" -> viewModel.clearRegion()
                "arrow" -> {
                    setFragmentResult("chosenCountry", bundleOf("chosenCountry" to chosenCountry))
                    findNavController().navigate(R.id.action_choicePlaceFragment_to_regionFragment)
                }
            }
        }

        viewModel.getArea().observe(viewLifecycleOwner){
            render(it)
        }

        binding.selectCountryButtonGroup.setOnClickListener {
            findNavController().navigate(R.id.action_choicePlaceFragment_to_countryFragment)
        }

        binding.selectRegionButtonGroup.setOnClickListener {
            setFragmentResult("chosenCountry", bundleOf("chosenCountry" to chosenCountry))
            findNavController().navigate(R.id.action_choicePlaceFragment_to_regionFragment)
        }

    }

    private fun render(area: MutableMap<String, String>) {
        with(binding) {
            if (area["regionName"].isNullOrEmpty()) {
                selectedRegionText.text = null
                selectedRegionText.isVisible = false
                selectRegionActionButton.setImageResource(R.drawable.leading_icon_filter)
                selectRegionActionButton.tag = "arrow"
            } else {
                selectedRegionText.text = area["regionName"]
                selectedRegionText.isVisible = true
                selectRegionActionButton.setImageResource(R.drawable.clear_button)
                selectRegionActionButton.tag = "clear"
            }
            if (area["countryName"].isNullOrEmpty()) {
                selectedCountryText.text = null
                selectedCountryText.isVisible = false
                selectCountryActionButton.setImageResource(R.drawable.leading_icon_filter)
                selectCountryActionButton.tag = "arrow"
                chosenCountry = String()
            } else {
                selectedCountryText.text = area["countryName"]
                selectedCountryText.isVisible = true
                selectCountryActionButton.setImageResource(R.drawable.clear_button)
                selectCountryActionButton.tag = "clear"
                chosenCountry = area["countryName"]!!
            }
        }
        binding.buttonApply.isVisible = area.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
