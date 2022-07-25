package com.eiffelyk.word

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*


class MainActivity : AppCompatActivity() {
    val eidtText by lazy { findViewById<EditText>(R.id.editText1) }
    val eidtText2 by lazy { findViewById<EditText>(R.id.editText2) }
    val button1 by lazy { findViewById<Button>(R.id.button) }
    val button2 by lazy { findViewById<Button>(R.id.button2) }
    val text by lazy { findViewById<TextView>(R.id.textView) }
    val listResult: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var stringResult: StringBuilder = StringBuilder()
        eidtText.setText(
            "画 岸 拔 爸 败 攽 版 拌 姅 绊 孢 饱 抱 卑 备 奔 屄 彼 畀 贬 变 表 玢 秉 幷 並 拨 波 帛 瓝 泊 怖 法 范 贩 祊 肪 房 放 非 肥 肺 狒 沸 氛 奋 忿 奉 肤 拂 服 怫 绂 绋 拊 府 咐 阜 驸 侅 泔 岡 沽 卦 函 呵 和 劾 河 佫 轰 泓 郈 呼 狐 弧 虎 怙 诨 或 货 泾 沮 炬 泪 泠 泷 泸 泺 码 卖 盲 氓 牦 泖 玫 妹 門 孟 弥 觅 泌 宓 黾\n" +
                    "苗 庙 玟 抿 泯 鸣 命 抹 殁 沫 陌 侔 拇 姆 牧 泥 泞 拍 泮 咆 狍 庖 泡 呸 帔 佩 抨 怦 朋 披 拚 贫 郱 泼 迫 泣 浅 泅 沭 饲 泗 沲 沱 味 武 物 郃 弦 冼 现 享 協 胁 泄 泻 绁 幸 泫 学 郇 沿 泱 泳 油 鱼 雨 郁 咂 泽 沾 沼 治 注 狀"
        )
        eidtText2.setText(
            "疤 胈 拜 帮 绑 胞 保 鸨 背 贲 甭 泵 迸 秕 珌 毖 哔 陛 窆 扁 昪 便 飑 骉 饼 玻 勃 钚 测 浐 泚 荡 点 洞 洱 畈 飛 封 風 疯 罘 氟 俘 郛 祓 复 負 訃 阁 哈 孩 骇 顸 绗 阂 贺 很 狠 恨 恒 洪 紅 侯 厚 轷 浒 祜 哗 骅 徊 洹 奐 宦 皇 挥 虺 洄 哕 浍 诲 绘 浑 活 計 洎 济 挟 浃 茳 洚 浇 洁 津 荆 姥 洌 浏 洛 蚂 骂 脉 茫 昴\n" +
                    "冒 贸 眉 美 昧 虻 咪 迷 祢 洣 弭 眄 勉 眇 秒 咩 珉 闽 哞 某 浓 哌 派 眅 盼 叛 胖 胚 毗 骈 拼 姘 品 俜 玶 洴 屏 珀 匍 柒 洽 泉 染 娆 洳 洒 飒 洮 洼 洧 闻 郚 郗 洗 虾 咸 涎 宪 香 响 饷 项 巷 卸 洩 洫 恤 泶 洵 浔 恂 徇 衍 洋 洇 荥 盈 拶 浈 洲 洙 浊"
        )
        button1.setOnClickListener {
            var text1 = eidtText.text.toString()
            text1 = text1.replace(" ", ",").replace("\n", ",")
            val list = text1.split(",")
            var text2 = eidtText2.text.toString()
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
        }
        button2.setOnClickListener {
//            存储到文件
//            分享文件
            //获取剪切板管理器
//            val cm: ClipboardManager =
//                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            //设置内容到剪切板
//            cm.setPrimaryClip(ClipData.newPlainText("Word", text.text.toString()))
//            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show()
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT,"文字分享");
//            intent.setType("text/plain");
//            startActivity(intent);
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text.text.toString())
            startActivity(intent)
        }
    }

    /***
     * 保存到文件
     * @param str
     */
    fun saveFile(str: String?) {
        var fos: FileOutputStream? = null
        var writer: BufferedWriter? = null
        try {
            fos = openFileOutput("badao.txt", Context.MODE_PRIVATE)
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


}