package com.dam.xevi.pochosubastes.fragments

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.dam.xevi.pochosubastes.R
import com.dam.xevi.pochosubastes.databinding.FragmentEditObjectBinding
import com.dam.xevi.pochosubastes.databinding.FragmentSubastesBinding
import com.dam.xevi.pochosubastes.models.SubastaObject
import com.dam.xevi.pochosubastes.singleton.PochoSingleton
import java.time.LocalDate
import java.time.Year
import java.util.UUID.randomUUID

class EditObjectFragment : Fragment() {
    private var subastaObject: SubastaObject? = null
    private var _binding: FragmentEditObjectBinding? = null
    private val binding get() = _binding!!
    private var newObject: SubastaObject? = null
    @RequiresApi(Build.VERSION_CODES.O)
    private var selectedDate: LocalDate = LocalDate.now()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditObjectBinding.inflate(inflater, container, false)
        val view = binding.root
        val singleton = PochoSingleton.getInstance()
        subastaObject = singleton.getSelectedObject()
        val categorysSpinner = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
        val qualitysSpinner = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.qualitys,
            android.R.layout.simple_spinner_item
        )
        qualitysSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.quality.setAdapter(qualitysSpinner)
        binding.quality.setSelection(0)
        categorysSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.category.setAdapter(categorysSpinner)
        binding.category.setSelection(0)
        binding.btnCalendar.setOnClickListener {
            showDatePicker()
        }
        if (subastaObject != null) {
            binding.nomObj.setText(subastaObject!!.nom)
            binding.period.setText(subastaObject!!.epocaOrigen)
            binding.origin.setText(subastaObject!!.procedencia)
            binding.startingPrice.setText(subastaObject!!.preuInici.toString())
            binding.description.setText(subastaObject!!.descripcio)
            binding.category.setSelection(categorysSpinner.getPosition(subastaObject!!.categoria))
            binding.quality.setSelection(qualitysSpinner.getPosition(subastaObject!!.estat))
            binding.sold.isChecked = subastaObject!!.venut
        }
        binding.acceptbt.setOnClickListener {
            if(validationFormulary()) {
                newObject = SubastaObject(
                    if (subastaObject != null) subastaObject!!.id else randomUUID(),
                    binding.nomObj.text.toString(),
                    binding.description.text.toString(),
                    binding.category.selectedItem.toString(),
                    binding.period.text.toString(),
                    binding.origin.text.toString(),
                    binding.sold.isChecked,
                    binding.quality.selectedItem.toString(),
                    binding.startingPrice.text.toString().toDouble(),
                    00.0,
                    selectedDate,
                    "",
                    "https://picsum.photos/200"
                )

                if (binding.sold.isChecked) {
                    val bundle = Bundle().apply {
                        putParcelable("newObject", newObject)
                    }
                    val fragment = EditSellFragment().apply { arguments = bundle }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                else{
                    if (subastaObject != null) {
                        subastaObject!!.clone(newObject!!)}
                    else
                        singleton.addSubasta(newObject!!)
                    parentFragmentManager.popBackStack()
                }
            }
        }

        binding.cancelbt.setOnClickListener {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Cancel")
                .setMessage("Are you sure you want to cancel ?")
                .setPositiveButton("Yes") { dialog, _ ->
                    parentFragmentManager.popBackStack()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker(){
        var year = selectedDate.year
        var month = selectedDate.monthValue - 1
        var day = selectedDate.dayOfMonth

        val datePicker = DatePickerDialog(requireContext(),{_,
            selectedYear, selectedMonth, selectedDay ->
            val selectedDateToast = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            Toast.makeText(requireContext(), selectedDateToast, Toast.LENGTH_SHORT).show()
            selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
        },year,month,day)
        datePicker.show()
        }
    private fun validationFormulary(): Boolean {

        val nomObj = binding.nomObj.text.toString().trim()
        val period = binding.period.text.toString().trim()
        val origin = binding.origin.text.toString().trim()
        val startingPrice = binding.startingPrice.text.toString().trim()
        val description = binding.description.text.toString().trim()
        val category = binding.category.selectedItem.toString()
        val quality = binding.quality.selectedItem.toString()

        // Validación de campos vacíos
        if (nomObj.isEmpty()) {
            Toast.makeText(requireContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (period.isEmpty()) {
            Toast.makeText(requireContext(), "Period cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (origin.isEmpty()) {
            Toast.makeText(requireContext(), "Origin cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (startingPrice.isEmpty()) {
            Toast.makeText(requireContext(), "Starting Price cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (description.isEmpty()) {
            Toast.makeText(requireContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (category == "Select Category") { // Cambia "Select Category" por el valor que tengas por defecto en tu Spinner
            Toast.makeText(requireContext(), "Please select a category", Toast.LENGTH_SHORT).show()
            return false
        }
        if(quality == "Select Quality"){
            Toast.makeText(requireContext(), "Please select a quality", Toast.LENGTH_SHORT).show()
            return false
        }
        // Validación del precio
        val price = startingPrice.toDoubleOrNull()
        if (price == null || price < 0) {
            Toast.makeText(requireContext(), "Starting Price must be a valid number", Toast.LENGTH_SHORT).show()
            return false
        }
            return true
    }
}

