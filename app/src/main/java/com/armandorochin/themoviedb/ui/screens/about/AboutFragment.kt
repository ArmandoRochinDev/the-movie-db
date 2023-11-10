package com.armandorochin.themoviedb.ui.screens.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
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
import androidx.lifecycle.Lifecycle
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentAboutBinding
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment(), MenuProvider {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        setupUI()

        setupToolbar()


        return binding.root
    }

    private fun setupToolbar() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.about_title)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupUI() {
        Glide.with(binding.profilePicture.context).load("https://avatars.githubusercontent.com/u/88679335?v=4").into(binding.profilePicture)

        binding.ivGoogleplay.setOnClickListener { openUrl("https://play.google.com/store/apps/dev?id=4832117956294238601") }
        binding.ivGithub.setOnClickListener { openUrl("https://github.com/ArmandoRochinDev") }
        binding.ivCv.setOnClickListener { openUrl("https://github.com/ArmandoRochinDev/ArmandoRochinDev/blob/main/CV2023.pdf") }

        binding.tvAboutDescription.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
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