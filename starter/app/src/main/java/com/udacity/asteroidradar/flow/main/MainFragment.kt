package com.udacity.asteroidradar.flow.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(
                requireContext()
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater).let {
        it.lifecycleOwner = this
        it.viewModel = mainViewModel

        it.asteroidRecycler.adapter = AsteroidAdapter(OnClickListener { asteroid ->
            mainViewModel.displayAsteroidDetails(asteroid)
        })
        mainViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, { asteroid ->
            if (null != asteroid) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                mainViewModel.displayAsteroidDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        it.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = true

}
