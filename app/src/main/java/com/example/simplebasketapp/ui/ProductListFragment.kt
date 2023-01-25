package com.example.simplebasketapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simplebasketapp.databinding.FragmentProductListBinding
import com.example.simplebasketapp.ui.adapter.ProductListAdapter
import com.example.simplebasketapp.utils.ProductButtonClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment: Fragment(), ProductButtonClickListener {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    lateinit var productListAdapter: ProductListAdapter
    private val viewModel by viewModels<ProductListViewModel>()

    lateinit var listener: ProductButtonClickListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater,container,false)

        listener = this
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecycleView()

        observeProductList()

        observeErrorState()

        observeProgressbar()



    }

    private fun setupRecycleView() {
        productListAdapter = ProductListAdapter(
            listener
        )
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

    private fun observeErrorState() {
        viewModel.errorString.observe(viewLifecycleOwner, Observer { errorString ->
            Toast.makeText(
                requireContext(),
                errorString,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun observeProgressbar() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar2.isVisible = isLoading
        })
    }

    override fun onProductListButtonClickListener(view: View) {

        findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToCartFragment())

    }
}