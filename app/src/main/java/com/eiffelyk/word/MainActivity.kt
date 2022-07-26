package com.eiffelyk.word

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    val eidtText by lazy { findViewById<EditText>(R.id.editText1) }
    val eidtText2 by lazy { findViewById<EditText>(R.id.editText2) }
    val eidtText3 by lazy { findViewById<EditText>(R.id.editText3) }
    val button1 by lazy { findViewById<Button>(R.id.button) }
    val button2 by lazy { findViewById<Button>(R.id.button2) }
    val text by lazy { findViewById<TextView>(R.id.textView) }
    val textView2 by lazy { findViewById<TextView>(R.id.textView2) }
    var fileName = "${System.currentTimeMillis()}.txt"
    val dirName = "Text_Combine"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var stringResult: StringBuilder = StringBuilder()
//        eidtText.setText(
//            "画 岸 拔 爸 败 攽 版 拌 姅 绊 孢 饱 抱 卑 备 奔 屄 彼 畀 贬 变 表 玢 秉 幷 並 拨 波 帛 瓝 泊 怖 法 范 贩 祊 肪 房 放 非 肥 肺 狒 沸 氛 奋 忿 奉 肤 拂 服 怫 绂 绋 拊 府 咐 阜 驸 侅 泔 岡 沽 卦 函 呵 和 劾 河 佫 轰 泓 郈 呼 狐 弧 虎 怙 诨 或 货 泾 沮 炬 泪 泠 泷 泸 泺 码 卖 盲 氓 牦 泖 玫 妹 門 孟 弥 觅 泌 宓 黾\n" +
//                    "苗 庙 玟 抿 泯 鸣 命 抹 殁 沫 陌 侔 拇 姆 牧 泥 泞 拍 泮 咆 狍 庖 泡 呸 帔 佩 抨 怦 朋 披 拚 贫 郱 泼 迫 泣 浅 泅 沭 饲 泗 沲 沱 味 武 物 郃 弦 冼 现 享 協 胁 泄 泻 绁 幸 泫 学 郇 沿 泱 泳 油 鱼 雨 郁 咂 泽 沾 沼 治 注 狀"
//        )
//        eidtText2.setText(
//            "疤 胈 拜 帮 绑 胞 保 鸨 背 贲 甭 泵 迸 秕 珌 毖 哔 陛 窆 扁 昪 便 飑 骉 饼 玻 勃 钚 测 浐 泚 荡 点 洞 洱 畈 飛 封 風 疯 罘 氟 俘 郛 祓 复 負 訃 阁 哈 孩 骇 顸 绗 阂 贺 很 狠 恨 恒 洪 紅 侯 厚 轷 浒 祜 哗 骅 徊 洹 奐 宦 皇 挥 虺 洄 哕 浍 诲 绘 浑 活 計 洎 济 挟 浃 茳 洚 浇 洁 津 荆 姥 洌 浏 洛 蚂 骂 脉 茫 昴\n" +
//                    "冒 贸 眉 美 昧 虻 咪 迷 祢 洣 弭 眄 勉 眇 秒 咩 珉 闽 哞 某 浓 哌 派 眅 盼 叛 胖 胚 毗 骈 拼 姘 品 俜 玶 洴 屏 珀 匍 柒 洽 泉 染 娆 洳 洒 飒 洮 洼 洧 闻 郚 郗 洗 虾 咸 涎 宪 香 响 饷 项 巷 卸 洩 洫 恤 泶 洵 浔 恂 徇 衍 洋 洇 荥 盈 拶 浈 洲 洙 浊"
//        )
        button1.setOnClickListener {
            var text1 = eidtText.text.toString()
            if (text1.isEmpty()) {
                Toast.makeText(this, "请在第一个输入框中输入文字", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            text1 = text1.replace(" ", ",").replace("\n", ",")
            val list = text1.split(",")
            var text2 = eidtText2.text.toString()
            if (text2.isEmpty()) {
                Toast.makeText(this, "请在第二个输入框中输入文字", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            text2 = text2.replace(" ", ",").replace("\n", ",")
            val list2 = text2.split(",")
            list.forEach {
                list2.forEach { it2 ->
                    val string = " $it$it2"
                    if (string.isNotEmpty()) {
                        stringResult.append(string)
                    }
                }
            }
            text.text = stringResult.toString().replaceFirst(" ", "")
            val file = eidtText3.text.toString()
            fileName = "${file.ifEmpty { System.currentTimeMillis().toDateStr() }}.txt"
            textView2.text = "文件存储路径：Download/Text_Combine/${fileName}"
            saveFile(fileName, text.text.toString())
            copyFileToDownloadDir(this, "${filesDir.path}/${fileName}", dirName)
        }
        button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val uri = listFiles(this, dirName, fileName)[0]
            intent.setDataAndType(uri, "text/plain")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(intent)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.toDateStr(pattern: String = "yyyy-MM-dd_HH:mm:ss"): String {
        val date = Date(this)
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    /***
     * 保存到文件
     * @param str
     */
    private fun saveFile(fileName: String?, str: String) {
        var fos: FileOutputStream? = null
        var writer: BufferedWriter? = null
        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE)
            writer = BufferedWriter(OutputStreamWriter(fos))
            try {
                writer.write(str)
                Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                try {
                    writer.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 复制私有目录的文件到公有Download目录
     * @param context 上下文
     * @param oldPath 私有目录的文件路径
     * @param targetDirName 公有目录下的目标文件夹名字。比如传test，则会复制到Download/test目录下。另外如果Download目录下test文件夹不存在，会自动创建。
     * @return 公有目录的uri，为空则代表复制失败
     */
    private fun copyFileToDownloadDir(
        context: Context,
        oldPath: String,
        targetDirName: String
    ): Uri? {
        try {
            val oldFile = File(oldPath)
            //设置目标文件的信息
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is a file.")
            values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, oldFile.name)
            values.put(MediaStore.Files.FileColumns.TITLE, oldFile.name)
            values.put(MediaStore.Files.FileColumns.MIME_TYPE, getMimeType(oldPath))
            val relativePath = Environment.DIRECTORY_DOWNLOADS + File.separator + targetDirName
            values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            val resolver = context.contentResolver
            val insertUri = resolver.insert(downloadUri, values)
            if (insertUri != null) {
                val fos = resolver.openOutputStream(insertUri)
                if (fos != null) {
                    val fis = FileInputStream(oldFile)
                    fis.copyTo(fos)
                    fis.close()
                    fos.close()
                    return insertUri
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getMimeType(path: String?): String {
        var mime = "*/*"
        path ?: return mime
        val mmr = MediaMetadataRetriever()
        try {
            mmr.setDataSource(path)
            mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE) ?: mime
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mmr.release()
        }
        return mime
    }

    private fun listFiles(context: Context, dirName: String, file: String?): List<Uri> {
        val resultList = ArrayList<Uri>()
        try {
            val resolver = context.contentResolver
            val downloadUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            val resultCursor = resolver?.query(
                downloadUri,
                null,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME + "=?",
                arrayOf(dirName),
                null
            )
            if (resultCursor != null) {
                val fileIdIndex =
                    resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val fileNameIndex =
                    resultCursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                while (resultCursor.moveToNext()) {
                    val fileId = resultCursor.getLong(fileIdIndex)
                    //文件名
                    val fileName = resultCursor.getString(fileNameIndex)
                    val pathUri = downloadUri.buildUpon().appendPath("$fileId").build()
                    if (file != null && file == fileName) {
                        resultList.add(pathUri)
                    }
//                    resultList.add(pathUri)
                }
                resultCursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resultList
    }

}