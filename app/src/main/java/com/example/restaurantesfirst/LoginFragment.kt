package com.example.restaurantesfirst

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.restaurantesfirst.databinding.FragmentLoginBinding
import com.example.restaurantesfirst.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.singInButton.setOnClickListener {
            checkForValidUsers()
        }

        return binding.root
    }

    private fun checkForValidUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val request: Response<ValidUsers> = RetrofitProvider.getRetrofit().create(APIService::class.java).getUsers("usersRegistered")
            val users: ValidUsers? = request.body()
            activity?.runOnUiThread {
                if (request.isSuccessful && users != null) {
                    validateUserFields(binding, users)
                } else {
                    showNetworkingError()
                }
            }
        }
    }

    private fun validateUserFields(bindingPass: FragmentLoginBinding, validUsers: ValidUsers) {
        val userString: String = bindingPass.mailTextViewLogin.text.toString()
        val passwordString: String = bindingPass.passwordTextViewLogin.text.toString()
        for (user in validUsers.users) {
            if (user.mail == userString && user.password == passwordString) {
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_restaurantsListFragment)
            } else {
                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNetworkingError() {
        Toast.makeText(context, "Couldn't fetch the restaurants", Toast.LENGTH_SHORT).show()
    }

}