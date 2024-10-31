package com.dam.xevi.pochosubastes.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.xevi.pochosubastes.R
import com.dam.xevi.pochosubastes.adapter.SubastaAdapter
import com.dam.xevi.pochosubastes.databinding.FragmentHomeBinding
import com.dam.xevi.pochosubastes.databinding.FragmentSubastesBinding
import com.dam.xevi.pochosubastes.singleton.PochoSingleton

class SubastesFragment : Fragment() {
    private var _binding: FragmentSubastesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubastesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val singleton = PochoSingleton.getInstance()
        val objects = singleton.getSubastas()
        val subastaAdapter = SubastaAdapter(objects) { subObject ->
            val itemDetailFragment = ItemDetailFragment.newInstance(subObject)
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_container, itemDetailFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = subastaAdapter
        binding.floatingActionButton.setOnClickListener {
            val fragment = EditObjectFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}