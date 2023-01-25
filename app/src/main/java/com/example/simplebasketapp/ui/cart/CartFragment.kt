package com.example.simplebasketapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplebasketapp.databinding.FragmentCartBinding
import com.example.simplebasketapp.ui.cart.adapter.CartItemListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartViewModel>()
    lateinit var cartItemListAdapter: CartItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
        observeCartItemList()
    }

    private fun setupRecycleView() {
        cartItemListAdapter = CartItemListAdapter()

        binding.rvFavoriteProducts.apply {
            adapter = cartItemListAdapter

            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeCartItemList() {
        val adapter = binding.rvFavoriteProducts.adapter as CartItemListAdapter

        viewModel.productList.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                adapter.differ.submitList(it)
            }
        })

    }

}