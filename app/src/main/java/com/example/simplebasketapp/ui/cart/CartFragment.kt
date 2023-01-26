package com.example.simplebasketapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplebasketapp.R
import com.example.simplebasketapp.databinding.FragmentCartBinding
import com.example.simplebasketapp.ui.BasketViewModel
import com.example.simplebasketapp.ui.cart.adapter.CartItemListAdapter
import com.example.simplebasketapp.utils.CartItemClickListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: Fragment(), CartItemClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartViewModel>()
    lateinit var cartItemListAdapter: CartItemListAdapter

    val mainViewModel: BasketViewModel by hiltNavGraphViewModels(R.id.nav_graph)

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
        placeOrderClickListener()
        observeErrorState()
        observeProgressbar()
        navigateBack()
        observeClearBasket()
    }

    private fun setupRecycleView() {
        cartItemListAdapter = CartItemListAdapter(this)

        binding.rvFavoriteProducts.apply {
            adapter = cartItemListAdapter

            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeCartItemList() {
        val adapter = binding.rvFavoriteProducts.adapter as CartItemListAdapter

        mainViewModel.productList.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                adapter.differ.submitList(it)
                binding.tvMyCart.text = "My Cart (${it.size})"
            }
        })
    }

    private fun placeOrderClickListener() {
        if (!mainViewModel.productList.value.isNullOrEmpty()) {
            binding.clPlaceOrder.setOnClickListener {
                viewModel.postOrder(
                    mainViewModel.productList.value!!
                )
            }
        }
    }

    override fun increaseProductNumber(id: String) {
        mainViewModel.increaseProductNumber(id)
    }

    override fun decreaseProductNumber(id: String) {
        mainViewModel.decreaseProductNumber(id)
    }

    override fun removeProductFromList(id: String) {
        mainViewModel.removeProductFromList(id)
    }

    private fun observeErrorState() {
        viewModel.errorString.observe(viewLifecycleOwner, Observer { errorString ->
            Snackbar.make(binding.root, errorString,Snackbar.LENGTH_LONG).show()
        })
    }

    private fun observeProgressbar() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar2.isVisible = isLoading
        })
    }

    private fun navigateBack() {
        binding.clContinueShopping.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToProductListFragment())
        }
    }

    private fun clearBasket() {
        mainViewModel.clearBasket()
    }

    private fun observeClearBasket() {
        viewModel.clearBasket.observe(viewLifecycleOwner, Observer { clearBasket ->
            if (clearBasket) {
                clearBasket()
            }
        })
    }

}