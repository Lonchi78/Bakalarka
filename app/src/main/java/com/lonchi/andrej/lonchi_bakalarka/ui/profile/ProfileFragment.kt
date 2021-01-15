package com.lonchi.andrej.lonchi_bakalarka.ui.profile

import android.view.View
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentHomeBinding
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentProfileBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    override val layoutId: Int = R.layout.fragment_profile
    override val vmClassToken: Class<ProfileViewModel> = ProfileViewModel::class.java
    override val bindingInflater: (View) -> FragmentProfileBinding = { FragmentProfileBinding.bind(it) }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun initView() {
        setupUser()

        btnSignOut.setOnClickListener {
            Firebase.auth.signOut()

            mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(requireActivity()) {
                    viewModel.logout()
                    requireActivity().finishAffinity()
                    startActivity(LoginActivity.getStartIntent(requireActivity(), null))
                }
        }
    }

    override fun bindViewModel() = Unit

    private fun setupUser() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val firebaseUser = Firebase.auth.currentUser
        Timber.d("setupUser firebaseUser: $firebaseUser")
        firebaseUser?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = it.uid

            Timber.d("setupUser firebaseUser name: $name")
            Timber.d("setupUser firebaseUser email: $email")
            Timber.d("setupUser firebaseUser photoUrl: $photoUrl")
            Timber.d("setupUser firebaseUser emailVerified: $emailVerified")
            Timber.d("setupUser firebaseUser uid: $uid")

            textUserName.text = name
            textUserMail.text = email

            imgUserAvatar.load(photoUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_profile)
                transformations(CircleCropTransformation())
            }
        }


    }
}