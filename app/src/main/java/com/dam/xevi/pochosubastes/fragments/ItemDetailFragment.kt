package com.dam.xevi.pochosubastes.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.dam.xevi.pochosubastes.R
import com.dam.xevi.pochosubastes.databinding.FragmentItemDetailBinding
import com.dam.xevi.pochosubastes.models.SubastaObject
import com.dam.xevi.pochosubastes.singleton.PochoSingleton
import com.squareup.picasso.Picasso

class ItemDetailFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    val singleton = PochoSingleton.getInstance()
    companion object {
        private const val ARG_ITEM = "arg_item"
        fun newInstance(item: SubastaObject): ItemDetailFragment {
            val fragment = ItemDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val item = arguments?.getParcelable<SubastaObject>(ARG_ITEM)
        singleton.setSelectedObject(item!!)
        binding.objName.text = item?.nom ?: "No disponible"
        Picasso.get().load(item?.imatgeUrl).into(binding.objImg)
        binding.category.text = item?.categoria ?: "No disponible"
        binding.date.text = item?.dataSubasta.toString() ?: "No disponible"
        binding.Origin.text = "Origin: " + item?.procedencia ?: "No disponible"
        binding.period.text = "Period: " + item?.epocaOrigen ?: "No disponible"
        binding.quality.text = "Quality: " + item?.estat ?: "No disponible"
        binding.tvdesc.text = "Description:"
        binding.description.text = item?.descripcio ?: "No disponible"
        binding.startingPrice.text = "Starting Price: " + item?.preuInici.toString() + "€"
        if (item?.venut == true) {
            binding.soldLayout.visibility = View.VISIBLE
            binding.buyersName.text ="Buyers Name: " +  item.comprador
            binding.finalPrice.text ="Final Price: " +  item.preuFinal.toString() + "€"
        }
        binding.editbt.setOnClickListener {
            val fragment = EditObjectFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.deletebt.setOnClickListener {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { dialog, _ ->
                    singleton.removeFromSubastas()
                    parentFragmentManager.popBackStack()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroyView() {
        super.onDestroyView()
        singleton.setSelectedObject(null)
    }
}

