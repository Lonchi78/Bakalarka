package com.lonchi.andrej.lonchi_bakalarka.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class LoginActivity : BaseActivity<LoginViewModel>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, LoginActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }

        private const val RC_SIGN_IN = 1
    }

    override val layoutId: Int? = R.layout.activity_login
    override val vmClassToken: Class<LoginViewModel> = LoginViewModel::class.java

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun initView() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener {
            Timber.d("initView: signin click")
            signIn()
        }

        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUIFirebase(currentUser)
    }

    private fun updateUIFirebase(account: FirebaseUser?) {
        Timber.d("updateUI displayName: ${account?.displayName}")
        Timber.d("updateUI email: ${account?.email}")
        Timber.d("updateUI id: ${account?.metadata}")
        Timber.d("updateUI photoUrl: ${account?.photoUrl}")

        if (account != null) {
            finishAffinity()
            startActivity(MainActivity.getStartIntent(this, intent.extras))
        }
    }

    private fun signIn() {
        Timber.d("signIn:")
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun bindViewModel() = Unit

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("onActivityResult: $requestCode, resultCode = $resultCode")

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                firebaseAuthWithGoogle(it)
            } ?: signInFailed()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.e("handleSignInResult: $e")
            signInFailed()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("firebaseAuthWithGoogle: success")
                    auth.currentUser?.let{
                        viewModel.login(it)
                        updateUIFirebase(it)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.d("firebaseAuthWithGoogle: ${task.exception}")
                    signInFailed()
                }

                // ...
            }
    }

    private fun signInFailed() {
        showSnackbar("Authentication Failed.")
        updateUIFirebase(null)
    }
}