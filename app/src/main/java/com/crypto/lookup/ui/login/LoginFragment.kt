package com.crypto.lookup.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crypto.lookup.MainActivity
import com.crypto.lookup.R
import com.crypto.lookup.data.User
import com.crypto.lookup.data.UserFirebaseDaoImpl
import com.crypto.lookup.data.UserService
import com.crypto.lookup.data.listeners.onGetDataListener
import com.crypto.lookup.databinding.FragmentLoginBinding
import com.google.firebase.firestore.DocumentSnapshot


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val db = UserService(UserFirebaseDaoImpl())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.LoginLoginButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            this.activity!!.finish()
            startActivity(intent)
        }


        binding.LoginRegisterButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
            db.retrieve("test", object : onGetDataListener {
                override fun onSuccess(data: DocumentSnapshot) {
                    println(data.toObject(User::class.java))
                }

                override fun onFailed(e: Exception) {
                    println("error")
                }
            })

        }

        binding.LoginForgotPassword.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

}