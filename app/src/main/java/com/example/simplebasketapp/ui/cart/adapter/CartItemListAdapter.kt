package com.example.simplebasketapp.ui.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplebasketapp.R
import com.example.simplebasketapp.databinding.CartListItemBinding
import com.example.simplebasketapp.utils.CartItemClickListener

class CartItemListAdapter constructor(
    val cartItemClickListener: CartItemClickListener
) : RecyclerView.Adapter<CartItemListAdapter.CartViewHolder>() {

    private lateinit var binding : CartListItemBinding



    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<CartListItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: CartListItemUiModel,
            newItem: CartListItemUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CartListItemUiModel,
            newItem: CartListItemUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemListAdapter.CartViewHolder {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cart_list_item,
            parent,
            false
        )
        return CartViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: CartItemListAdapter.CartViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(product.image).into(binding.ivProduct)
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "${product.price} ${product.currency}"
            binding.tvNumberOfItem.text = product.qty.toString()

            binding.ivIncrease.setOnClickListener {
                cartItemClickListener.increaseProductNumber(product.id)

            }

            binding.ivDecrease.setOnClickListener {
                cartItemClickListener.decreaseProductNumber(product.id)
            }

            binding.tvRemove.setOnClickListener {
                cartItemClickListener.removeProductFromList(product.id)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}