package com.example.simplebasketapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplebasketapp.databinding.FragmentProductListBinding
import com.example.simplebasketapp.ui.adapter.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment: Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    lateinit var productListAdapter: ProductListAdapter
    private val viewModel by viewModels<ProductListViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecycleView()

        observeProductList()



    }

    private fun setupRecycleView() {
        productListAdapter = ProductListAdapter()
        binding.rvFavoriteProducts.apply {
            adapter = productListAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
    }

    private fun observeProductList() {
        val adapter = binding.rvFavoriteProducts.adapter as ProductListAdapter
        viewModel.productList.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                adapter.differ.submitList(it)
            }
        })
    }
}