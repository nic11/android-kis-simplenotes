package ru.nic11.edu.simplenotes

import android.Manifest
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.util.*


const val PERMISSION_REQUEST_CODE = 0

class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedListener {
//    private lateinit var cameraView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

//        checkSelfPermission(android.Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    private fun startCamera() {
//        cameraView = findViewById(R.id.cameraView)
        cameraView.captureMode = CameraView.CaptureMode.IMAGE
        cameraView.bindToLifecycle(this)

//        takePictureButton = findViewById(R.id.takePictureButton)
        takePictureButton.setOnClickListener{
            cameraView.takePicture(
                generatePictureFile(),
                AsyncTask.SERIAL_EXECUTOR,
                this
            )
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, R.string.need_permission, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.permission_in_settings, Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onImageSaved(file: File) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show()
    }

    override fun onError(
        imageCaptureError: ImageCapture.ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generatePictureFile(): File {
        return File(filesDir, UUID.randomUUID().toString())
    }
}
