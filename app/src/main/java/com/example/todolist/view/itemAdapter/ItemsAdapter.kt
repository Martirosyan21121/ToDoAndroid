package com.example.todolist.view.itemAdapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.view.entity.ItemData
import com.example.todolist.view.fragments.FirstFragmentDirections
import com.example.todolist.view.viewModel.ItemViewModel

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    private var itemList = emptyList<ItemData>()


    class ItemsViewHolder(itemView: View, private val viewModel: ItemViewModel) :
        RecyclerView.ViewHolder(itemView) {
        private var itemText: TextView = itemView.findViewById(R.id.id_text)
        private var itemNumber: TextView = itemView.findViewById(R.id.id_number)
        private var updateButton: ImageButton = itemView.findViewById(R.id.item_update)
        private var checkBox: CheckBox = itemView.findViewById(R.id.item_done)
        private var time: TextView = itemView.findViewById(R.id.id_time)
        private var deleteItem: ImageButton = itemView.findViewById(R.id.item_delete)

        fun bind(itemData: ItemData) {
            itemText.text = itemData.text
            itemNumber.text = itemData.number.toString()
            checkBox.isChecked = itemData.completed
            if (itemData.time == null){
                time.text = ""
            } else {
                time.text = itemData.time.toString()
            }

            updateButton.setOnClickListener {
                val action =
                    FirstFragmentDirections.actionFirstFragmentToUpdateDataFragment(itemData)
                itemView.findNavController().navigate(action)
            }


            checkBox.setOnCheckedChangeListener { _, _ ->
                if (checkBox.isChecked) {
                    itemData.completed = true
                    viewModel.update(itemData)
                    Toast.makeText(
                        itemView.context,
                        "Successfully done: ${itemData.number}", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    itemData.completed = false
                    viewModel.update(itemData)
                    Toast.makeText(
                        itemView.context,
                        "task failed: ${itemData.number}", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            deleteItem.setOnClickListener {
                val builder = AlertDialog.Builder(itemView.context)
                builder.setPositiveButton("Yes") { _, _ ->
                    viewModel.delete(itemData)
                    Toast.makeText(
                        itemView.context,
                        "Successfully removed: ${itemData.number}", Toast.LENGTH_SHORT
                    ).show()
                    itemView.findNavController().navigate(R.id.firstFragment)
                }

                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Delete ${itemData.number} ?")
                builder.setMessage("Are you sure wont to delete task !!! ${itemData.number}")
                builder.create().show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewModel =
            ViewModelProvider(parent.context as ViewModelStoreOwner)[ItemViewModel::class.java]
        val itemView: View = layoutInflater.inflate(R.layout.item_list, parent, false)
        return ItemsViewHolder(itemView = itemView, viewModel = viewModel)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val clickedItem = itemList[position]
        holder.bind(itemData = clickedItem)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(item: List<ItemData>) {
        this.itemList = item
        notifyDataSetChanged()
    }
}


