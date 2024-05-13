package com.example.trabajofinal

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import utilidades.SessionManager


class Splash : AppCompatActivity() {
    lateinit var tvSplash: TextView
    lateinit var imageView: ImageView
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        sessionManager = SessionManager(this)
        tvSplash=findViewById(R.id.tvSplash)
        imageView=findViewById(R.id.imageView)


        val animacionimagen: Animation = AnimationUtils.loadAnimation(this, R.anim.animacion_splash)
        imageView.startAnimation(animacionimagen)

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(5000)
                    sessionManager = SessionManager(this@Splash)
                    sessionManager.isLoggin()
                    if (sessionManager.isLoggin()) {
                        if (sessionManager.isAdmin()){
                            val intent = Intent(this@Splash,MenuAdmin::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            overridePendingTransition(0, 0)
                            startActivity(intent)
                        }else{
                            val intent = Intent(this@Splash,Carta::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }
                        finish()
                    } else {
                        val intent = Intent(this@Splash,MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        finish()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                }
            }
        }
        timer.start()
    }
}