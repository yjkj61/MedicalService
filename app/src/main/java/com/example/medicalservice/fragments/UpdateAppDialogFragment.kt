package com.example.medicalservice.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.blankj.utilcode.util.AppUtils
import com.example.medicalservice.MyApplication
import com.example.medicalservice.baseFile.BaseDialogFragment
import com.example.medicalservice.bean.Data
import com.example.medicalservice.databinding.UpdateAppDialogFragmentLayoutBinding
import com.example.medicalservice.tools.ScreenUtils
import com.example.medicalservice.tools.log
import com.example.medicalservice.tools.toast
import java.io.File


class UpdateAppDialogFragment : BaseDialogFragment<UpdateAppDialogFragmentLayoutBinding>() {

    private var localDirPath : String? = null

    //安装包文件
    private var file : File? = null

    //下载任务的id
    private var taskId = 0L

    //安装包名称
    private var apkName = ""

    private var downloadUrl = ""

    /**
     * 反注册下载组件
     */
    private fun unregister() {
        Aria.download(this).unRegister()
    }

    /**
     * 注册下载组件
     */
    private fun register() {
        Aria.download(this).register()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        register()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.let {
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.window?.setDimAmount(0.8f)
            it.window?.setLayout(
                ScreenUtils.getScreenWidth().times(0.4f).toInt(),
                ScreenUtils.getScreenHeight().times(0.45f).toInt()
            )
        }
    }

    @SuppressLint("SetTextI18n", "UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()
        localDirPath = MyApplication.getInstance().getExternalFilesDir("${MyApplication.getInstance().packageName}")?.absolutePath
        apkName = "深空-app-release.apk"
        arguments?.let {
            val data = it.getSerializable(UPDATE_APP_ENTITY) as Data
            downloadUrl = data.apkFile
        }
        viewBinding.apply {
            cancelUpdateButton.setOnClickListener {
                dismiss()
            }
            confirmUpdateButton.setOnClickListener {
                createDownloadTask(downloadUrl)
            }
        }

    }

    /**
     * 创建下载任务
     */
    private fun createDownloadTask(downloadUrl : String){
        file = File(localDirPath,apkName)
        "开始任务:${file?.absolutePath}".log("runningTask")
        taskId = Aria.download(this)
            .load(downloadUrl)
            .setFilePath(file?.absolutePath)
            .ignoreFilePathOccupy()
            .create()
    }

    /**
     * 开始下载任务
     */
    @Download.onTaskStart
    fun startDownloadTask(task: DownloadTask) {
        "开始任务".log("runningTask")
        if (task.filePath == file?.absolutePath) {
            viewBinding.cancelUpdateButton.visibility = View.GONE
            viewBinding.downloadProgressBar.visibility = View.VISIBLE
            viewBinding.percentText.visibility = View.VISIBLE
        }
    }

    /**
     * 任务下载中
     */
    @SuppressLint("SetTextI18n")
    @Download.onTaskRunning
    fun runningTask(task: DownloadTask){
        if(task.filePath == file?.absolutePath){
            if(task.percent <= 100){
                requireActivity().runOnUiThread {
                    viewBinding.downloadProgressBar.progress = task.percent
                    viewBinding.percentText.text = "${task.percent}%"
                    "${task.percent}".log("runningTask")
                }

            }
                //setProgress(task.percent)
        }
    }

    @Download.onTaskFail
    fun taskFail(task: DownloadTask?,e : Exception?) {
        "下载失败:${task?.entity}---e:${e.toString()}".log("runningTask")
    }

    /**
     * 下载完成
     */
    @Download.onTaskComplete
    fun finishedTask(task: DownloadTask){
        if(task.filePath == file?.absolutePath){
            Aria.download(this).load(taskId).removeRecord()
            viewBinding.downloadProgressBar.progress = 100
            viewBinding.percentText.text = "100%"
            hasUnKnowAppRes ()
        }
    }

    private fun hasUnKnowAppRes(install : ()-> Unit = {}){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !requireContext().packageManager.canRequestPackageInstalls()){
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
            intent.data = Uri.parse("package:" + requireContext().packageName)
            startActivityForResult(intent, 1023)
        }else{
            AppUtils.installApp(file)
            dismiss()
            install.invoke()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1023){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && requireContext().packageManager.canRequestPackageInstalls()){
                AppUtils.installApp(file)
                dismiss()
            }else{
                val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                val uri = Uri.fromParts("package", requireContext().getPackageName(), null)
                intent.data = uri
                startActivityForResult(intent, 1)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        unregister()
    }

    companion object {
        const val UPDATE_APP_ENTITY = "update_app_entity"
    }
}