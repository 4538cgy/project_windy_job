package com.uos.project_windy_job.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.uos.project_windy_job.R
import com.uos.project_windy_job.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    var auth = FirebaseAuth.getInstance()
    var googleSignInClient : GoogleSignInClient ? = null
    var GOOGLE_LOGIN_CODE = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.activitylogin = this@LoginActivity

        //구글 로그인 옵션 활성화
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //폰번호 로그인
        binding.activityLoginButtonLogin.setOnClickListener {

        }

        //구글 로그인
        binding.googleSignInButton.setOnClickListener {

        }
    }

    fun signinPhone(){
        //핸드폰 자동인증 처리


        //progressDialog?.show()

        val phoneNumber = "+82" + binding.activityLoginEdittextPhonenumber.text.toString()
        var code: String? = null
        FirebaseAuth.getInstance().firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(
            phoneNumber,
            code
        )

        //auth.useAppLanguage()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            120,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    //성공시
                    Log.d("credential", p0.toString())
                    Log.d("성공", "인증에 성공 했습니다.")
                    makeText(binding.root.context,
                        "핸드폰 인증에 성공했습니다.",
                        Toast.LENGTH_LONG).show()
                    //progressDialog?.dismiss()

                    FirebaseAuth.getInstance().signInWithCredential(p0).addOnFailureListener {



                    }.addOnCompleteListener {
                        //startActivity(Intent(binding.root.context,LobbyActivity::class.java))
                        makeText(binding.root.context,"로그인 성공",Toast.LENGTH_LONG).show()
                        //finish()



                    }


                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    //실패시
                    Log.d("exception", p0.toString())
                    Log.d("실패", "인증에 실패 했습니다.")
                    makeText(binding.root.context,
                        "핸드폰 인증에 실패했습니다..",
                        Toast.LENGTH_LONG).show()






                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    Log.d("코드가 전송됨", p0.toString())
                }

                override fun onCodeAutoRetrievalTimeOut(p0: String) {
                    super.onCodeAutoRetrievalTimeOut(p0)



                }

            }
        )


    }
}