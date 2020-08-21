package com.example.plantsbabysitter.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.ui.ViewAnimator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class GoogleSignInFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonLogin: Button
    private lateinit var buttonLogout: Button
    private lateinit var textView: TextView
    private lateinit var firebaseImageView: ImageView
    private lateinit var profilePicImageView: CircleImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        buttonLogin = view.findViewById(R.id.login_button)
        buttonLogout = view.findViewById(R.id.logout_button)
        textView = view.findViewById(R.id.firebase_text)
        progressBar = view.findViewById(R.id.progress_login)
        firebaseImageView = view.findViewById(R.id.firebase_image)
        profilePicImageView = view.findViewById(R.id.profile_image)

        buttonLogin.setOnClickListener { signInGoogle() }
        buttonLogout.setOnClickListener { logOut() }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!

        if (mAuth.currentUser != null) {
            val user = mAuth.currentUser
            updateUIWithUser(user)
        }
    }

    private fun signInGoogle() {
        progressBar.visibility = View.VISIBLE
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Log.e(LOG_TAG, "Google sign in failed", e)
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(LOG_TAG, "firebaseAuthWithGoogle:" + account.id)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            progressBar.visibility = View.INVISIBLE
            if (task.isSuccessful) {
                Log.d(LOG_TAG, "signInWithCredential:success")

                val user = mAuth.currentUser
                updateUIWithUser(user)
            } else {
                Log.e(LOG_TAG, "signInWithCredential:failure", task.exception)
                task.exception?.let { FirebaseCrashlytics.getInstance().recordException(it) }

                Toast.makeText(
                    context, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                updateUINoUser()
            }
        }
    }

    private fun updateUIWithUser(user: FirebaseUser?) {
        val name = user!!.displayName
        val email = user.email
        val photo = user.photoUrl

        textView.text = getString(R.string.user_info_title)
        textView.append("\n$name")
        textView.append("\n$email")

        // FIXME: 20/AUG/2020 There is some weird flicker when starting the layout with profile pic on
        Picasso.get().load(photo)
            .placeholder(R.drawable.profile_image_placeholder).error(R.drawable.ic_google_logo)
            .into(profilePicImageView)
        ViewAnimator.viewInvisibleAnimator(firebaseImageView)
        ViewAnimator.viewVisibleAnimator(profilePicImageView)

        buttonLogout.visibility = View.VISIBLE
        buttonLogin.visibility = View.INVISIBLE

    }

    private fun updateUINoUser() {
        textView.text = resources.getString(R.string.firebase_login)

        ViewAnimator.viewInvisibleAnimator(profilePicImageView)
        ViewAnimator.viewVisibleAnimator(firebaseImageView)

        buttonLogout.visibility = View.INVISIBLE
        buttonLogin.visibility = View.VISIBLE
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener { updateUINoUser() }
    }

    companion object {
        private const val GOOGLE_SIGN_IN = 123
        private const val LOG_TAG = "GoogleSignInFragment"
    }
}