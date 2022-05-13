package com.crypto.lookup.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.crypto.lookup.databinding.FragmentNotificationsBinding
import com.crypto.lookup.ui.login.UserViewModel
import java.text.SimpleDateFormat

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var sharedViewModel: UserViewModel
    private var _binding: FragmentNotificationsBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

      /*  val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        }) */
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = sharedViewModel.getCurrentUser()
        binding.profileEmail.text = currentUser.email
        binding.profileBirthdate.text = SimpleDateFormat("dd/mm/yyyy").format(currentUser.birthDate)
        binding.profileIdentity.text = currentUser.identityNumber.toString()
        binding.profilePhone.text = currentUser.phoneNumber.toString()
        binding.profileNameSur.text =
            currentUser.name.replaceFirstChar { it.uppercase() } + " " + currentUser.surname.uppercase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}