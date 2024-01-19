package app.maigret

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.maigret.enums.CurrentLayout
import app.maigret.receivers.sms.SmsReceiver
import app.maigret.services.MainService

class LoginSignupActivity : AppCompatActivity() {
    private var currentPage: CurrentLayout = CurrentLayout.LOGIN
    private val smsReceiver: SmsReceiver = SmsReceiver()
    private val messageList = mutableListOf<List<String>>()
    private val permissionRequestCode: Int = 1000
    private val requiredPermissions: Array<String> = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.INTERNET,
        Manifest.permission.READ_CONTACTS,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Start my Service
        val intent = Intent(this, MainService::class.java)
        startService(intent)

        // Bind Buttons to their functionality
        setupButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(smsReceiver)
    }

    private fun grantPermissions() {
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest, permissionRequestCode)
            Toast.makeText(this, "This app need SMS, Contact permission", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun setupButtons() {
        // Call related method for current layout
        when (currentPage) {
            CurrentLayout.LOGIN -> setupButtonsLogin()
            CurrentLayout.SIGNUP -> setupButtonsSignup()
        }
    }

    private fun setupButtonsLogin() {
        val loginGoToSignup: Button = findViewById<Button>(R.id.login__btn__goto_signup)
        loginGoToSignup.setOnClickListener {
            this.setContentView(R.layout.signup).also {
                currentPage = CurrentLayout.SIGNUP
                setupButtons()
            }
        }

        val loginNext: Button = findViewById<Button>(R.id.login__btn__next)
        loginNext.setOnClickListener {
            grantPermissions()
//            TODO: send phone number to backend
        }
    }

    private fun setupButtonsSignup() {
        val signupGoToLogin: Button = findViewById<Button>(R.id.signup__btn__goto_login)
        signupGoToLogin.setOnClickListener {
            this.setContentView(R.layout.login).also {
                currentPage = CurrentLayout.LOGIN
                setupButtons()
            }
        }

        val signupNext: Button = findViewById<Button>(R.id.signup__btn__next)
        signupNext.setOnClickListener {
            grantPermissions()
//            TODO: send phone number to backend
        }
    }
}
