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
import com.example.simplebasketapp.ui.adapter.ProductLisItemUiModel

class CartItemListAdapter() : RecyclerView.Adapter<CartItemListAdapter.CartViewHolder>() {

    private lateinit var binding : CartListItemBinding

    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<ProductLisItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: ProductLisItemUiModel,
            newItem: ProductLisItemUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductLisItemUiModel,
            newItem: ProductLisItemUiModel
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
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}