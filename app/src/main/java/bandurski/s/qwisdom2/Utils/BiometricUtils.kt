package bandurski.s.qwisdom2.Utils

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.v4.app.ActivityCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.widget.Toast
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import java.util.jar.Manifest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.net.ssl.KeyManager
import kotlin.properties.Delegates

@TargetApi(Build.VERSION_CODES.M)
class BiometricUtils(
        val context: Context,
        val fManager: FingerprintManager = context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager,
        val keyManager: KeyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager,
        val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore"),
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore"),
        val cipher: Cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7),
        val KEY_NAME: String = "AndroidKey",
        val listener: OnAuthenticationResult
        ): FingerprintManager.AuthenticationCallback() {

    private var authResult by Delegates.observable(
            initialValue = false,
            onChange = {
                _,_,new -> listener.onAuthenticationResult(new)
            }
    )

    fun startAuth(cryptoObject: FingerprintManager.CryptoObject) {

        val cancellationSignal = CancellationSignal()
        fManager.authenticate(
                cryptoObject,
                cancellationSignal,
                0,
                this,
                null)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun generateKey() {
        keyStore.load(null)
        keyGenerator.init(
                KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build()
        )
        keyGenerator.generateKey()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun cipherInit(): Boolean {
        keyStore.load(null)
        val key = keyStore.getKey(KEY_NAME, null)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return true
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) = this.update("There was an Auth Error. $errString", false)

    override fun onAuthenticationFailed() = this.update("Auth failed.",false)

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) = this.update("Error: ${helpString.toString()}",false)

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        this.update("Authentication successful.",true)
        authResult = true
    }

    private fun update(message: String, authSucceeded: Boolean) = Toast.makeText(context,message,Toast.LENGTH_LONG).show()

    fun fingerprint(): Boolean {
        if (isHardwareSupported()) {
            if (isFingerprintAvailable()) {
                if (isPermissionGranted()) {
                    if (isKeyguardSecure()) {
                        return true
                    }
                    else Toast.makeText(context,"Smartphone not secured",Toast.LENGTH_LONG).show()
                }
                else Toast.makeText(context,"Permission not granted",Toast.LENGTH_LONG).show()
            }
            else Toast.makeText(context,"Fingerprint is not available",Toast.LENGTH_LONG).show()
        }
        else Toast.makeText(context,"Hardware not supported",Toast.LENGTH_LONG).show()
        return false
    }

    /**
     * Sprawdza czy telefon posiada czytnik linii papilarnych.
     * */
    private fun isHardwareSupported(): Boolean = FingerprintManagerCompat.from(context).isHardwareDetected

    /**
     * Sprawdza czy istnieje przynajmniej jeden zapisany odcisk palca.
     */
    private fun isFingerprintAvailable(): Boolean = FingerprintManagerCompat.from(context).hasEnrolledFingerprints()

    /**
     * Sprawdza czy aplikacja ma pozwolenie na uzycie czytnika linii papilarnych.
     */
    private fun isPermissionGranted(): Boolean = ActivityCompat.checkSelfPermission(context, android.Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED

    private fun isKeyguardSecure(): Boolean = keyManager.isKeyguardSecure

    interface OnAuthenticationResult {
        fun onAuthenticationResult(b: Boolean)
    }
}
