package com.example.practical_2_1

import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.io.File

class AudioActivity : AppCompatActivity() {

    val MICROPHONE_PERMISION_CODE = 200
    private lateinit var analytics: FirebaseAnalytics
    lateinit var mediaRecorder: MediaRecorder
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        analytics = Firebase.analytics
        if(checkMicrophone()){
            getMicPermision()
        }
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

    fun onImage(){}


    fun onDelete(){
        analytics.logEvent("audio_record_deleted", null)
    }

    public fun startRecording(v: View) {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setOutputFile(getRecordingFilePath())
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.prepare()
        mediaRecorder.start()
        Toast.makeText(this, "Audio recording has started!", Toast.LENGTH_SHORT).show()
        analytics.logEvent("audio_record_started", null)
    }

    public fun stopRecording(v: View) {
        mediaRecorder.stop()
        mediaRecorder.release()
        Toast.makeText(this, "Audio recording has stopped!", Toast.LENGTH_SHORT).show()
        analytics.logEvent("audio_record_saved", null)
    }

    public fun playRecording(v: View) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(getRecordingFilePath())
        mediaPlayer.prepare()
        mediaPlayer.start()
        Toast.makeText(this, "Playing audio!", Toast.LENGTH_SHORT).show()
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

    public fun getRecordingFilePath(): String{
        var contextWrapper: ContextWrapper = ContextWrapper(applicationContext)
        var musicDirectory: File = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!
        var file: File = File(musicDirectory, "testRecordingFile" + ".mp3")
        return file.path
    }
}