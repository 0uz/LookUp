package com.crypto.lookup.ui.register

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.crypto.lookup.data.UserFirebaseDaoImpl
import com.crypto.lookup.data.UserService
import com.crypto.lookup.databinding.FragmentRegisterBinding
import com.crypto.lookup.utils.Validation


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val db = UserService(UserFirebaseDaoImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isEnable = arrayListOf<Boolean>(false, false, false, false, false, false)
        binding.RegisterEmail.addTextChangedListener {
            isEnable[0] = Validation.isEmailValid(it.toString())
        }
        binding.RegisterName.addTextChangedListener {
            isEnable[1] = Validation.isTextValid(it.toString(), 10, 2)
        }
        binding.RegisterPassword.addTextChangedListener {
            isEnable[2] = Validation.isTextValid(it.toString(), 10, 4)
        }
        binding.RegisterPasswordAgain.addTextChangedListener {
            isEnable[3] = Validation.isTextValid(it.toString(), 10, 4) && binding.RegisterPassword.text.toString()
                .equals(it.toString())
        }
        binding.RegisterIDNum.addTextChangedListener {
            isEnable[4] = Validation.isTextValid(it.toString(), 11, 2) // TODO min 11
        }
        binding.RegisterPhoneNum.addTextChangedListener {
            isEnable[5] = Validation.isTextValid(it.toString(), 10, 2) // TODO min
        }

        //TODO birthdate

        binding.RegisterCreateAccButton.setOnClickListener {
            if (isEnable.stream().allMatch { it.equals(true) }) {
                Toast.makeText(context, "basarilir", 100).show()
            } else {
                Toast.makeText(context, "degildir", 100).show()
            }
        }
    }

}