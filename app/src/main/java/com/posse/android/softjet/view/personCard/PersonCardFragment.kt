package com.posse.android.softjet.view.personCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.posse.android.softjet.databinding.PersonBigCardLayoutBinding

class PersonCardFragment : Fragment() {

    private var _binding: PersonBigCardLayoutBinding? = null
    private val binding get() = _binding!!

    private val args: PersonCardFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PersonBigCardLayoutBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        name.text = args.name
        surname.text = args.surname
        email.text = args.email
        avatar.load(args.imageUri) {
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}