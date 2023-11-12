package com.armandorochin.themoviedb.ui.fragments.movies

import android.content.Context
import android.os.Bundle
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
import com.armandorochin.themoviedb.databinding.FragmentMoviesBinding
import com.armandorochin.themoviedb.domain.model.Movie
import com.armandorochin.themoviedb.ui.MainActivity
import com.armandorochin.themoviedb.ui.fragments.movie.MovieFragment
import com.armandorochin.themoviedb.ui.fragments.movie.MovieFragment.Companion.movieKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment(), MenuProvider {

    private var _binding:FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var adapter: MoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(requireArguments())
        setupToolbar(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
    }

    private fun setupToolbar(params: Bundle?){
        (activity as MainActivity).supportActionBar?.show()
        (activity as MainActivity).supportActionBar?.title = params?.getString(titleKey) ?: defaultTitle
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun setupRecycler(params: Bundle) {
        val category = params.getString(categoryKey)

        adapter = MoviesAdapter(-1, false) { onMovieClicked(it) }
        binding.rvMovies.layoutManager = GridLayoutManager(context, calculateNoOfColumns(requireContext()))
        binding.rvMovies.adapter = adapter

        moviesViewModel.getMoviesLiveData(category!!).observe(viewLifecycleOwner){ movies ->
            viewLifecycleOwner.lifecycleScope.launch{
                adapter?.submitData(movies)
            }
        }
    }
    private fun onMovieClicked(movie: Movie){
        val args = Bundle()
        val fragment = MovieFragment()

        args.putInt(movieKey, movie.movieId)
        fragment.arguments = args

        (activity as MainActivity).addFragmentToBackstack(fragment)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
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

    private fun calculateNoOfColumns(
        context: Context
    ): Int {
        val columnWidthDp = 180f// For example columnWidthdp=180
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt() // +0.5 for correct rounding to int.
    }

    companion object{
        const val titleKey = "title"
        const val categoryKey = "category"
        private const val defaultTitle = "Películas"
    }
}