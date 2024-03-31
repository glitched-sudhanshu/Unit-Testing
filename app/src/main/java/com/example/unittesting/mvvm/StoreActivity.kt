package com.example.unittesting.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unittesting.R
import com.example.unittesting.mvvm.adapter.ProductAdapter
import com.example.unittesting.mvvm.viewmodels.StoreViewModel
import com.example.unittesting.mvvm.viewmodels.StoreViewModelFactory
import com.example.unittesting.utils.NetworkResult

class StoreActivity : AppCompatActivity() {
    lateinit var mainViewModel: StoreViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        recyclerView = findViewById(R.id.productList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val repository = (application as TestingApplication).productRepository
        mainViewModel =
            ViewModelProvider(this, StoreViewModelFactory(repository))
                .get(StoreViewModel::class.java)

        mainViewModel.getProducts()

        mainViewModel.products.observe(
            this,
            Observer {
                when (it) {
                    is NetworkResult.Success -> {
                        Log.d("r02_sudhanshu", it.data.toString())
                        adapter = ProductAdapter(it.data!!)
                        recyclerView.adapter = adapter
                    }

                    is NetworkResult.Error -> {}

                    else -> {}
                }
            },
        )
    }
}
