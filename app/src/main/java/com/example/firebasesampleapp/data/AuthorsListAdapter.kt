package com.example.firebasesampleapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesampleapp.databinding.AuthorsListBinding


class AuthorsListAdapter(val clickListner: AuthorClickListner) : ListAdapter<Author, AuthorsListAdapter.ViewHolder>(AuthorsDiffCallback()) {

//Draw views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
//Bind the Values
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListner)
    }


    class ViewHolder private constructor(val binding:AuthorsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Author, clickListner: AuthorClickListner) {
            binding.clicklistner = clickListner
            binding.authors= item
            binding.executePendingBindings()
        }


        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    AuthorsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }

        }


    }


    // For Update the differences in list
    class AuthorsDiffCallback : DiffUtil.ItemCallback<Author>() {
        override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
            return oldItem == newItem
        }

    }

    class AuthorClickListner(val clickListner: (author:Author) -> Unit)
    {
        fun onClick(author: Author)
        {
            clickListner(author)
        }
    }





}