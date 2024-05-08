package com.sajon.dev.rickandmorty.epoxy

import com.sajon.dev.rickandmorty.R
import com.sajon.dev.rickandmorty.databinding.ModelLoadingBinding

class LoadingEpoxyModel : ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading) {
    override fun ModelLoadingBinding.bind() {
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}