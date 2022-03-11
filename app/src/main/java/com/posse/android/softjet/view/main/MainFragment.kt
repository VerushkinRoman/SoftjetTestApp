package com.posse.android.softjet.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.posse.android.softjet.databinding.MainFragmentLayoutBinding
import com.posse.android.softjet.model.AppState
import com.posse.android.softjet.model.data.Data
import com.posse.android.softjet.model.data.Response
import com.posse.android.softjet.utils.NetworkStatus
import com.posse.android.softjet.view.main.adapter.Adapter
import com.posse.android.softjet.view.main.viewModel.MainFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainFragmentViewModel by lazy {
        viewModelFactory.create(MainFragmentViewModel::class.java)
    }

    private var _binding: MainFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private var adapter: Adapter? = null

    private var isOnline = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MainFragmentLayoutBinding.inflate(inflater, container, false)
        .apply { _binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedData = savedInstanceState?.getParcelableArray(screenDataKey)
        val adapterData = if (savedData?.isNotEmpty() == true && savedData.first() is Data) {
            @Suppress("UNCHECKED_CAST")
            savedData as Array<Data>
        } else null
        attachAdapter(adapterData)
        viewModel.getStateLiveData().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun attachAdapter(adapterData: Array<Data>?) = with(binding) {
        if (adapter == null) {
            adapter = Adapter {
                val action = MainFragmentDirections.actionMainFragmentToPersonFragment(
                    it.first_name,
                    it.last_name,
                    it.avatar,
                    it.email
                )
                findNavController().navigate(action)
            }
        } else {
            loadingLayout.visibility = View.INVISIBLE
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        if (adapterData?.isNotEmpty() == true) {
            restoreAdapter(adapterData)
            loadingLayout.visibility = View.INVISIBLE
        } else {
            adapter?.clear()
            viewModel.getStartData(isOnline)
        }
    }

    private fun restoreAdapter(adapterData: Array<Data>) {
        adapter?.clear()
        adapterData.forEach {
            adapter?.setSingleData(it)
        }
    }

    private fun renderData(appState: AppState<*>) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                if (appState.data is Response) {
                    appState.data.data.forEach {
                        adapter?.setSingleData(it)
                    }
                    loadingLayout.visibility = View.INVISIBLE
                } else throw RuntimeException("Wrong data type: ${appState.data}")
            }

            is AppState.Error -> {
                loadingLayout.visibility = View.INVISIBLE
                handleErrorWithToast(appState.error)
            }

            AppState.Loading -> loadingLayout.visibility = View.VISIBLE
        }
    }

    private fun handleErrorWithToast(e: Throwable?) {
        Toast.makeText(context, "Error happened: $e", Toast.LENGTH_SHORT).show()
    }

    @Inject
    fun initNetwork(networkStatus: NetworkStatus) {
        CoroutineScope(Dispatchers.IO).launch {
            networkStatus
                .isOnline()
                .collect { isOnline = it }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        adapter?.getData()?.let {
            outState.putParcelableArray(screenDataKey, it.toTypedArray())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val screenDataKey = "KEY_DATA"
    }
}