package com.example.demo0401_camera

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.hzw.doodle.DoodleActivity
import cn.hzw.doodle.DoodleParams
import cn.hzw.doodle.DoodleView
import kotlinx.android.synthetic.main.activity_preview.*
import java.io.File

class PreviewActivity : AppCompatActivity() {

    private val REQ_CODE_DOODLE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        // 接受传来的照片地址
        val path = intent.getStringExtra("path")
        // 如果地址不为空
        if (!path.isNullOrEmpty()) {
            // 显示照片
            img_preview.setImageURI(Uri.fromFile(File(path)))
        }

        btn_edit.setOnClickListener {
            // 涂鸦参数
            val params = DoodleParams()
            params.mIsFullScreen = true
            // 图片路径
            params.mImagePath = path
            // 初始画笔大小
            params.mPaintUnitSize = DoodleView.DEFAULT_SIZE.toFloat()
            // 画笔颜色
            params.mPaintColor = Color.RED
            // 是否支持缩放item
            params.mSupportScaleItem = true
            // 启动涂鸦页面
            DoodleActivity.startActivityForResult(this@PreviewActivity, params, REQ_CODE_DOODLE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_DOODLE) {
            // 如果没有带回数据，返回
            if (data == null) {
                return
            }
            // 如果成功带回数据，显示涂鸦后的照片
            if (resultCode == DoodleActivity.RESULT_OK) {
                val path = data.getStringExtra(DoodleActivity.KEY_IMAGE_PATH)
                if (!path.isNullOrEmpty()) {
                    img_preview.setImageURI(Uri.fromFile(File(path)))
                }
            } else if (resultCode == DoodleActivity.RESULT_ERROR) { // 如果发生错误，弹窗报错
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
