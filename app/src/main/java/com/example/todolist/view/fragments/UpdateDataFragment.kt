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
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.databinding.FragmentUpdateDataBinding
import com.example.todolist.view.entity.ItemData
import com.example.todolist.view.viewModel.ItemViewModel
import java.time.LocalTime

class UpdateDataFragment : Fragment() {

    private lateinit var binding: FragmentUpdateDataBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: ItemViewModel

    private val args by navArgs<UpdateDataFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateDataBinding.inflate(inflater, container, false)
        binding.forNumberUpdate.setText(args.currentItem.number.toString())
        binding.forTextUpdate.setText(args.currentItem.text)

        binding.selectedTimeTextView.let { textView ->
            args.currentItem.let { currentItem ->
                currentItem.time?.let { time ->
                    textView.text = "Selected Time: $time"
                }
            }
        }

        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        binding.updateButton.setOnClickListener {
            updateData()
        }

        binding.setTimeButton.setOnClickListener {
            openTimePickerDialog()
        }

        return binding.root
    }

    private var selectedTime: LocalTime? = null


    @SuppressLint("SetTextI18n")
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

    private fun updateData() {
        val num = binding.forNumberUpdate.text.toString().trim()
        val text = binding.forTextUpdate.text.toString().trim()

        if (num.isEmpty()) {
            Toast.makeText(context, "Please enter a number", Toast.LENGTH_SHORT).show()
        } else if (text.isEmpty()) {
            Toast.makeText(context, "Please enter a text", Toast.LENGTH_SHORT).show()
        }else if (selectedTime == null) {
           selectedTime = args.currentItem.time
            val updateData = ItemData(
                args.currentItem.id,
                number = num.toInt(),
                text = text,
                time = selectedTime
            )
            viewModel.update(updateData)
        } else {

            val updateData = ItemData(
                args.currentItem.id,
                number = num.toInt(),
                text = text,
                time = selectedTime
            )
            viewModel.update(updateData)
        }

        Toast.makeText(requireContext(), "Task updated !!!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_updateDataFragment_to_firstFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view = view)
        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_updateDataFragment_to_firstFragment)
        }
    }
}
