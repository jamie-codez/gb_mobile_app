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
        binding.apply {
            homeHousesListRv.visibility = View.GONE
            housesProgressBar.visibility = View.VISIBLE
            housesLoadingTv.visibility = View.VISIBLE
            noItemsIvHouses.visibility = View.GONE
            noItemsTvHouses.visibility = View.GONE
        }
        val houseAdapter = HouseAdapter(listOf())
        housesRecyclerView.setHasFixedSize(true)
        housesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        housesRecyclerView.adapter = houseAdapter
        viewModel.getHousez().observe(viewLifecycleOwner) {
            if (it!!.isEmpty()) {
                binding.apply {
                    homeHousesListRv.visibility = View.GONE
                    housesProgressBar.visibility = View.GONE
                    housesLoadingTv.visibility = View.GONE
                    noItemsIvHouses.visibility = View.VISIBLE
                    noItemsTvHouses.visibility = View.VISIBLE
                }
            }else {
                binding.apply {
                    homeHousesListRv.visibility = View.VISIBLE
                    housesProgressBar.visibility = View.GONE
                    housesLoadingTv.visibility = View.GONE
                    noItemsIvHouses.visibility = View.GONE
                    noItemsTvHouses.visibility = View.GONE
                }
                houseAdapter.setHouses(it)
            }
        }
    }

}