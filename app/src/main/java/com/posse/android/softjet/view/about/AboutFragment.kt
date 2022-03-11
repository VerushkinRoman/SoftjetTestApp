package com.posse.android.softjet.view.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.posse.android.softjet.databinding.AboutLayoutBinding

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AboutLayoutBinding.inflate(inflater, container, false).root
}