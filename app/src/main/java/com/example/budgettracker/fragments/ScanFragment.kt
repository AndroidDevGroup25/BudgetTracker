package com.example.budgettracker.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.budgettracker.MainActivity
import com.example.budgettracker.R
import com.example.budgettracker.Transaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.ByteArrayOutputStream


class ScanFragment(val bottom_navigation: BottomNavigationView) : Fragment() {

    private val imageButton_accept_clickListener = View.OnClickListener {
        processImage(imageBitmap)
    }
    private val REQUEST_IMAGE_CAPTURE = 1

    private val imageButton_scan_clickListener = View.OnClickListener {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Error taking photo")
            e.printStackTrace()
        }
    }

    private lateinit var imageButton_scan: ImageButton
    private lateinit var imageView_receipt: ImageView
    private lateinit var imageButton_accept: ImageButton
    private lateinit var imageBitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageButton_scan = view.findViewById(R.id.imageButton_scan)
        imageView_receipt = view.findViewById(R.id.imageView_receipt)
        imageButton_accept = view.findViewById(R.id.imageButton_accept)

        imageButton_scan.setOnClickListener(imageButton_scan_clickListener)
        imageButton_accept.setOnClickListener(imageButton_accept_clickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBitmap = data!!.extras!!.get("data") as Bitmap
            imageView_receipt.setImageBitmap(imageBitmap)
            imageButton_accept.visibility = View.VISIBLE
        }
    }
    private fun processImage(imageBitmap: Bitmap) {
        val inputImage = InputImage.fromBitmap(imageBitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage).addOnSuccessListener {
            if(it.textBlocks.size == 0) {
                Log.i(TAG, "No text detected")
                return@addOnSuccessListener
            }

            val transaction = Transaction()
            transaction.setReceipt(getParseFileFromBitmap(imageBitmap))
            transaction.setUser(ParseUser.getCurrentUser())
            transaction.setCost(0.0)

            //navigate to Summary fragment
            toSummaryFragment()

        }.addOnFailureListener {
            Log.e(TAG, it.toString())
        }

    }

    private fun toSummaryFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.flContainer, SummaryFragment()).commit()
        bottom_navigation.selectedItemId = R.id.action_summary
    }
    private fun getParseFileFromBitmap(bitmap: Bitmap): ParseFile {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bitmapBytes: ByteArray = stream.toByteArray()
        return ParseFile("myReceipt", bitmapBytes)
    }
    companion object {
        const val TAG = "ScanFragment"
    }
}