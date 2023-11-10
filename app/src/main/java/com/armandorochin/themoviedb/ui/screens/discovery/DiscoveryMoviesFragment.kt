package com.armandorochin.themoviedb.ui.screens.discovery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentDiscoveryMoviesBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.calculateNoOfColumns
import com.armandorochin.themoviedb.ui.screens.about.AboutFragment
import com.armandorochin.themoviedb.ui.screens.detail.DetailMovieFragment
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DiscoveryMoviesFragment(private val title:String) : Fragment(), MenuProvider {

    private var _binding:FragmentDiscoveryMoviesBinding? = null
    private val binding get() = _binding!!

    private val discoveryViewModel: DiscoveryViewModel by viewModels()
    private var adapter: DiscoveryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoveryMoviesBinding.inflate(inflater, container, false)

        setupRecycler()

        setupToolbar()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
    private fun setupToolbar(){
        (activity as MainActivity).supportActionBar?.title = title
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun setupRecycler() {
        adapter = DiscoveryAdapter(-1, false) { onMovieClicked(it) }
        binding.rvMovies.layoutManager = GridLayoutManager(context, calculateNoOfColumns(requireContext(),180f))
        binding.rvMovies.adapter = adapter
        discoveryViewModel.getDiscoveryLiveData().observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                adapter?.submitData(movies)
            }
        }
    }
    private fun onMovieClicked(movie: Movie){
        (activity as MainActivity).addFragmentToBackstack(DetailMovieFragment(movie.movieId))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_about -> {
                (activity as MainActivity).addFragmentToBackstack(AboutFragment())
                true
            }
            android.R.id.home -> {
                val fm: FragmentManager = parentFragmentManager

                if (fm.backStackEntryCount > 0) {
                    fm.popBackStack()
                }
                true
            }
            else -> false
        }
    }
}