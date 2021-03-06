/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.persistence.R
import com.example.android.persistence.databinding.ProductFragmentBinding
import com.example.android.persistence.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    private var mBinding: ProductFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false)

        return mBinding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ProductViewModel.Factory(
                activity!!.application, arguments!!.getInt(KEY_PRODUCT_ID))

        val model = ViewModelProviders.of(this, factory)
                .get(ProductViewModel::class.java)

        mBinding!!.productViewModel = model

        subscribeToModel(model)
    }

    private fun subscribeToModel(model: ProductViewModel) {

        // Observe product data
        model.observableProduct.observe(this, Observer { productEntity -> model.setProduct(productEntity) })
    }

    companion object {

        private val KEY_PRODUCT_ID = "product_id"

        /** Creates product fragment for specific product ID  */
        fun forProduct(productId: Int): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putInt(KEY_PRODUCT_ID, productId)
            fragment.arguments = args
            return fragment
        }
    }
}