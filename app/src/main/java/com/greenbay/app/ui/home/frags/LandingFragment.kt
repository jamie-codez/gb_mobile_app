package com.greenbay.app.ui.home.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenbay.app.databinding.FragmentLandingBinding
import com.greenbay.app.ui.home.adapters.HouseAdapter
import com.greenbay.app.ui.home.viewmodels.HomeViewModel

class LandingFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        HomeViewModel(requireActivity().application)
    }
    private lateinit var binding: FragmentLandingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val housesRecyclerView = binding.homeHousesListRv
        housesRecyclerView.setHasFixedSize(true)
        housesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        housesRecyclerView.adapter = HouseAdapter(listOf())

        viewModel.getHouses().observe(viewLifecycleOwner) {
            if (it != null) {
                (housesRecyclerView.adapter as HouseAdapter).setHouses(it)
            }else {
                (housesRecyclerView.adapter as HouseAdapter).setHouses(listOf())
            }
        }
    }

}