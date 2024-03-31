package com.example.unittesting.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unittesting.mvvm.models.ProductListItem
import com.example.unittesting.mvvm.repository.ProductRepository
import com.example.unittesting.utils.NetworkResult
import kotlinx.coroutines.launch

class StoreViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<NetworkResult<List<ProductListItem>>>()
    val products: LiveData<NetworkResult<List<ProductListItem>>>
        get() = _products

    fun getProducts() {
        viewModelScope.launch {
            val result = repository.getProducts()
            _products.postValue(result)
        }
    }
}

class StoreViewModelFactory(private val productRepository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StoreViewModel(productRepository) as T
    }
}
