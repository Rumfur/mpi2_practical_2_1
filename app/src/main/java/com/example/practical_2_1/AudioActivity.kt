package com.example.practical_2_1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class AudioActivity : AppCompatActivity() {

    val MICROPHONE_PERMISION_CODE = 200
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        analytics = Firebase.analytics
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.audio_mnu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.image_option -> onImage()
            R.id.delete_option -> onDelete()
        }
        return true
    }
// For Record button
//    analytics.logEvent("audio_record_started", null)
// For saving record
//    analytics.logEvent("audio_record_saved", null)

    fun onImage(){}
    fun onDelete(){
        analytics.logEvent("audio_record_deleted", null)
    }

    public fun startRecording(v: View) {

    }

    public fun stopRecording(v: View) {

    }

    public fun checkMicrophone(): Boolean{
        if(this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        return false;
    }

    private fun getMicPermision() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), MICROPHONE_PERMISION_CODE)
        }
    }
}