package com.armandorochin.themoviedb.ui.screens.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.FragmentAboutBinding
import com.armandorochin.themoviedb.ui.screens.main.MainActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        setupUI()



        return binding.root
    }

    private fun setupUI() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.about_title)

        Glide.with(binding.profilePicture.context).load("https://avatars.githubusercontent.com/u/88679335?v=4").into(binding.profilePicture)

        binding.ivGoogleplay.setOnClickListener { openUrl("https://play.google.com/store/apps/dev?id=4832117956294238601") }
        binding.ivGithub.setOnClickListener { openUrl("https://github.com/ArmandoRochinDev") }
        binding.ivCv.setOnClickListener { openUrl("https://github.com/ArmandoRochinDev/ArmandoRochinDev/blob/main/CV2023.pdf") }
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}