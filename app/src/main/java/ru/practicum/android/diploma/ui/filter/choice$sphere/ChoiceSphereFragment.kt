package ru.practicum.android.diploma.ui.filter.`choice$sphere`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentChoiceSphereBinding
class ChoiceSphereFragment : Fragment() {

    private var _binding: FragmentChoiceSphereBinding? = null
    private val binding get() = _binding!!
    // private val viewModel by viewModel<ChoiceSphereViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceSphereBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
