package com.example.todolist.view.fragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddDataBinding
import com.example.todolist.view.entity.ItemData
import com.example.todolist.view.viewModel.ItemViewModel
import java.time.LocalTime


class AddDataFragment : Fragment() {

    private lateinit var binding: FragmentAddDataBinding
    private lateinit var navController: NavController
    private lateinit var itemViewModel: ItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddDataBinding.inflate(inflater, container, false)
        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        binding.addButton.setOnClickListener {
            insertItem()
        }
        binding.setTimeButton.setOnClickListener {
            openTimePickerDialog()
        }
        return binding.root
    }

    private var selectedTime: LocalTime? = null

    private fun openTimePickerDialog() {
        val currentTime = LocalTime.now()
        val hour = currentTime.hour
        val minute = currentTime.minute

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialog,
            { _, selectedHour, selectedMinute ->
                selectedTime = LocalTime.of(selectedHour, selectedMinute)
                binding.selectedTimeTextView.text = "Selected Time: $selectedTime"

            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view = view)
        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_addDataFragment_to_firstFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertItem() {
        val num = binding.forNumber.text.toString().trim()
        val text = binding.forText.text.toString().trim()
        println(num)
        if (num.isEmpty()) {
            Toast.makeText(context, "Please enter a number", Toast.LENGTH_SHORT).show()
        } else if (text.isEmpty()) {
            Toast.makeText(context, "Please enter a text", Toast.LENGTH_SHORT).show()
        } else {

            val itemData = ItemData(number = num.toInt(), text = text, time = selectedTime)
            itemViewModel.insert(itemData)
            Toast.makeText(requireContext(), "Task added !!!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addDataFragment_to_firstFragment)
        }
    }
}