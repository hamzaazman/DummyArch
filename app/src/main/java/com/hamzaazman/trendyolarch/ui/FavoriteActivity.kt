package com.hamzaazman.trendyolarch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hamzaazman.trendyolarch.common.viewBinding
import com.hamzaazman.trendyolarch.databinding.ActivityFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityFavoriteBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}