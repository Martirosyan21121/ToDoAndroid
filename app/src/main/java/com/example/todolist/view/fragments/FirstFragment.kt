package com.example.todolist.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentFirstBinding
import com.example.todolist.view.itemAdapter.ItemsAdapter
import com.example.todolist.view.viewModel.ItemViewModel


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var navController: NavController
    private lateinit var itemViewModel: ItemViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        val adapter = ItemsAdapter()
        val recyclerView = binding.toDoRecyclerView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        itemViewModel.getAllData.observe(viewLifecycleOwner) { item ->
            adapter.setData(item)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view = view)
        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_addDataFragment)
        }
        binding.home.setOnClickListener {
            findNavController().navigate(R.id.firstFragment)
        }
    }
}


