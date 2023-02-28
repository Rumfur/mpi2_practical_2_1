package com.example.practical_2_1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AudioActivity : AppCompatActivity() {

    val MICROPHONE_PERMISION_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.audio_mnu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.image_option -> onAudio()
            R.id.delete_option -> onDelete()
        }
        return true
    }

    fun onAudio(){}
    fun onDelete(){}

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