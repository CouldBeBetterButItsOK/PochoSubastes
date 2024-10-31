package com.dam.xevi.pochosubastes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import com.dam.xevi.pochosubastes.R


class AboutUsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null){
            val mapsFragment = MapsFragment()
            childFragmentManager.beginTransaction()
                .replace(R.id.map_container, mapsFragment)
                .commit()
        }
    }
}