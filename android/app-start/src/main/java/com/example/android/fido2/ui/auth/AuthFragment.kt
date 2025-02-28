/*
 * Copyright 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fido2.ui.auth

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.fido2.MainActivity
import com.example.android.fido2.databinding.AuthFragmentBinding
import com.example.android.fido2.ui.observeOnce

class AuthFragment : Fragment() {

    companion object {
        private const val TAG = "AuthFragment"
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: AuthFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.processing.observe(viewLifecycleOwner, Observer { processing ->
            if (processing) {
                binding.processing.show()
            } else {
                binding.processing.hide()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.signinIntent.observeOnce(this) { intent ->
            val a = activity
            if (intent.hasPendingIntent() && a != null) {
                try {

                    // TODO(5): Open the fingerprint dialog.
                    // - Open the fingerprint dialog by launching the intent from FIDO2 API.

                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Error launching pending intent for signin request", e)
                }
            }
        }
    }

    fun handleSignin(data: Intent) {
        viewModel.signinResponse(data)
    }

}
