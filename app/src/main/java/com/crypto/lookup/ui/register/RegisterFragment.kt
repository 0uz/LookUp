package com.crypto.lookup.ui.register

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crypto.lookup.data.User
import com.crypto.lookup.data.UserFirebaseDaoImpl
import com.crypto.lookup.data.UserService
import com.crypto.lookup.data.listeners.onSaveDataListener
import com.crypto.lookup.databinding.FragmentRegisterBinding
import com.crypto.lookup.utils.Common
import com.crypto.lookup.utils.Validation
import com.google.firebase.messaging.FirebaseMessaging
import java.sql.Timestamp
import java.util.*


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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        var isEnable = arrayListOf<Boolean>(false, false, false, false, false, false, false)


        binding.email.addTextChangedListener {
            if (Validation.isEmailValid(it.toString())) {
                isEnable[0] = Validation.isEmailValid(it.toString())
            } else {

            }
        }
        binding.name.addTextChangedListener {
            isEnable[1] = Validation.isTextValid(it.toString(), 10, 2)
        }
        binding.password.addTextChangedListener {
            isEnable[2] = Validation.isTextValid(it.toString(), 10, 4)
        }
        binding.passwordAgain.addTextChangedListener {
            isEnable[3] = Validation.isTextValid(it.toString(), 10, 4) && binding.password.text.toString()
                .equals(it.toString())
        }
        binding.identityNumber.addTextChangedListener {
            isEnable[4] = Validation.isTextValid(it.toString(), 11, 2) // TODO min 11
        }
        binding.phoneNo.addTextChangedListener {
            isEnable[5] = Validation.isTextValid(it.toString(), 10, 2) // TODO min and phone regex
        }

        val birthdate = Calendar.getInstance()
        val date = object : DatePickerDialog.OnDateSetListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                birthdate.set(Calendar.YEAR, year)
                birthdate.set(Calendar.YEAR, year)
                birthdate.set(Calendar.MONTH, monthOfYear)
                birthdate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.RegisterBirthdate.text = Common.dateFormat(birthdate)
            }
        }

        binding.RegisterBirthdate.setOnClickListener {
            DatePickerDialog(
                context!!, date,
                birthdate.get(Calendar.YEAR),
                birthdate.get(Calendar.MONTH),
                birthdate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.RegisterCreateAccButton.setOnClickListener {
            isEnable[6] = Validation.isBirthdateValid(birthdate)
            if (isEnable.stream().allMatch { it.equals(true) }) {
                FirebaseMessaging.getInstance().token.addOnSuccessListener {
                    val user = User(
                        binding.name.text.toString(),
                        binding.surname.text.toString(),
                        binding.identityNumber.text.toString().toLong(),
                        binding.phoneNo.text.toString().toLong(),
                        Timestamp(birthdate.timeInMillis),
                        binding.email.text.toString(),
                        it
                    )
                    db.save(user, object : onSaveDataListener {
                        override fun onSuccess() {
                            navController.popBackStack()
                        }

                        override fun onFailed() {
                        }

                    })
                }

            } else {
                Toast.makeText(context, "degildir", 100).show()
            }
        }
    }

}