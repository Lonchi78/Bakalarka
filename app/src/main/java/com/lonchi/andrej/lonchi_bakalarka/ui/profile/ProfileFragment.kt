package com.lonchi.andrej.lonchi_bakalarka.ui.profile

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentProfileBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.about.AboutActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.login.LoginActivity

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override val layoutId: Int = R.layout.fragment_profile
    override val vmClassToken: Class<ProfileViewModel> = ProfileViewModel::class.java
    override val bindingInflater: (View) -> FragmentProfileBinding =
        { FragmentProfileBinding.bind(it) }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun initView() {
        handleLoggedUser()

        binding?.viewFavourites?.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToFavouritesFragment())
        }

        binding?.viewOwnRecipes?.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToOwnRecipesFragment())
        }

        binding?.viewAllergens?.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAllergnesFragment())
        }

        binding?.viewDiets?.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToDietsFragment())
        }

        binding?.viewSettings?.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
        }

        binding?.viewAbout?.setOnClickListener {
            startActivity(AboutActivity.getStartIntent(requireContext()))
        }

        binding?.viewLogOut?.setOnClickListener {
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

    private fun handleLoggedUser() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        Firebase.auth.currentUser?.let {
            binding?.textUserName?.text = it.displayName
            binding?.textUserMail?.text = it.email

            //  TODO - nefunguje
            binding?.imgUserAvatar?.load(it.photoUrl) {
                crossfade(true)
                //placeholder(R.drawable.ic_profile_24)
                error(R.drawable.ic_profile_24)
            }
        }
    }
}