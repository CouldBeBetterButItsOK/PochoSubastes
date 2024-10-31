package com.dam.xevi.pochosubastes.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.dam.xevi.pochosubastes.R
import com.dam.xevi.pochosubastes.databinding.FragmentEditSellBinding
import com.dam.xevi.pochosubastes.models.SubastaObject
import com.dam.xevi.pochosubastes.singleton.PochoSingleton


class EditSellFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    val singleton = PochoSingleton.getInstance()
    private var _binding: FragmentEditSellBinding? = null
    private val binding get() = _binding!!
    private var newObject: SubastaObject? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditSellBinding.inflate(inflater, container, false)
        val view = binding.root
        arguments?.let {
            newObject = it.getParcelable("newObject")
        }
        binding.buyersName.setText(newObject!!.comprador)
        binding.finalPrice.setText(newObject!!.preuFinal.toString())
        binding.acceptbt.setOnClickListener {
            if (validationFormulary()) {
                if(singleton.getSelectedObject() != null) {
                    singleton.getSelectedObject()!!.clone(newObject!!)}
                else{
                    singleton.addSubasta(newObject!!)}
                val fragment = SubastesFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
                    singleton.setSelectedObject(null)
                }

            }

        binding.cancelbt.setOnClickListener {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Cancel")
                .setMessage("Are you sure you want to cancel ?")
                .setPositiveButton("Yes") { dialog, _ ->
                    val fragment = SubastesFragment()
                    singleton.setSelectedObject(null)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
        return view
    }

    private fun validationFormulary(): Boolean {
        val buyersName = binding.buyersName.text.toString().trim()
        val finalPrice = binding.finalPrice.text.toString().trim()
        if (buyersName.isEmpty()) {
            Toast.makeText(requireContext(), "Buyers Name cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        val price = finalPrice.toDoubleOrNull()
        if (price == null || price < 0) {
            Toast.makeText(requireContext(), "Final Price must be a valid number", Toast.LENGTH_SHORT).show()
            return false
        }
        else return true
    }
}