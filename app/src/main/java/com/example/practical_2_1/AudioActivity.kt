package com.example.practical_2_1

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.io.File


class AudioActivity : AppCompatActivity() {

    private val MICROPHONE_PERMISION_CODE = 200
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        analytics = Firebase.analytics
        if(checkMicrophone()){
            getMicPermission()
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

    private fun onImage(){
        val intent = Intent(this@AudioActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onDelete(){
        analytics.logEvent("audio_record_deleted", null)
    }

    fun startRecording(v: View) {
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

    fun stopRecording(v: View) {
        mediaRecorder.stop()
        mediaRecorder.release()
        Toast.makeText(this, "Audio recording has stopped!", Toast.LENGTH_SHORT).show()
        analytics.logEvent("audio_record_saved", null)
    }

    fun playRecording(v: View) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(getRecordingFilePath())
        mediaPlayer.prepare()
        mediaPlayer.start()
        Toast.makeText(this, "Playing audio!", Toast.LENGTH_SHORT).show()
    }

    fun displayRecordedAudio(v: View){
        val contextWrapper: ContextWrapper = ContextWrapper(applicationContext)
        val files = File(contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!.path).list()
        val a = ArrayAdapter(this, android.R.layout.simple_list_item_1, files)

        val listView = findViewById<ListView>(R.id.audioDisplay)
        listView.setAdapter(a)
    }

    private fun checkMicrophone(): Boolean{
        if(this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        return false;
    }

    private fun getMicPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), MICROPHONE_PERMISION_CODE)
        }
    }

    fun getRecordingFilePath(): String{
        val sp = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putInt("fileID", sp.getInt("fileID", 0) + 1)
        val contextWrapper = ContextWrapper(applicationContext)
        val musicDirectory: File = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!
        val file = File(musicDirectory, "testRecordingFile" + sp.getInt("fileID", 0) + ".mp3" )
        editor.commit()
        return file.path
    }
}