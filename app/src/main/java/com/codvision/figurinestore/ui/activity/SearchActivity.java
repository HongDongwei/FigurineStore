package com.codvision.figurinestore.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.ui.adapter.SGAdapter;
import com.codvision.figurinestore.bean.SanGuoBean;
import com.codvision.figurinestore.utils.CommolySearchView;
import com.codvision.figurinestore.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * TAG
     */
    public static final String TAG = "SearchActivity";
    /**
     * 数据显示listview
     */
    private ListView mListView;
    private ImageButton ibSearchVoice;
    private ImageButton ibSearchHear;
    /**
     * 三国通用搜索框
     */
    private CommolySearchView<SanGuoBean> mSGCommolySearchView;
    /**
     * 三国数据源
     */
    private List<SanGuoBean> mSGDatas;
    /**
     * 三国适配器
     */
    private SGAdapter sgAdapter;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        requestRecordAudioPermission();
        initDataI();
        initViewI();
        initSpeech() ;
    }

    /**
     * 初始化数据
     */
    private void initDataI() {
        mSGDatas = new ArrayList<SanGuoBean>();
        SanGuoBean sgbean1 = new SanGuoBean();
        sgbean1.setSgName("刘备");
        sgbean1.setSgPetName("玄德");
        sgbean1.setSgHeadBp(R.drawable.head);
        sgbean1.setSgDescribe("刘备（161年－223年6月10日），字玄德，东汉末年幽州涿郡涿县（今河北省涿州市）人");
        SanGuoBean sgbean2 = new SanGuoBean();
        sgbean2.setSgName("关羽");
        sgbean2.setSgPetName("云长");
        sgbean2.setSgHeadBp(R.drawable.head);
        sgbean2.setSgDescribe("关羽（？－220年），本字长生，后改字云长，河东郡解良（今山西运城）人");
        SanGuoBean sgbean3 = new SanGuoBean();
        sgbean3.setSgName("张飞");
        sgbean3.setSgPetName("翼德");
        sgbean3.setSgHeadBp(R.drawable.head);
        sgbean3.setSgDescribe("张飞（？－221年），字益德[1] ，幽州涿郡（今河北省保定市涿州市）人氏");
        SanGuoBean sgbean4 = new SanGuoBean();
        sgbean4.setSgName("赵云");
        sgbean4.setSgPetName("子龙");
        sgbean4.setSgHeadBp(R.drawable.head);
        sgbean4.setSgDescribe("赵云（？－229年），字子龙，常山真定（今河北省正定）人");
        SanGuoBean sgbean5 = new SanGuoBean();
        sgbean5.setSgName("马超");
        sgbean5.setSgPetName("孟起");
        sgbean5.setSgHeadBp(R.drawable.head);
        sgbean5.setSgDescribe("马超（176年－222年），字孟起，司隶部扶风郡茂陵（今陕西兴平）人");
        SanGuoBean sgbean6 = new SanGuoBean();
        sgbean6.setSgName("黄忠");
        sgbean6.setSgPetName("汉升");
        sgbean6.setSgHeadBp(R.drawable.head);
        sgbean6.setSgDescribe("黄忠（？－220年），字汉升（一作“汉叔”[1] ），南阳（今河南南阳）人");
        SanGuoBean sgbean7 = new SanGuoBean();
        sgbean7.setSgName("张辽");
        sgbean7.setSgPetName("文远");
        sgbean7.setSgHeadBp(R.drawable.head);
        sgbean7.setSgDescribe("张辽（169年－222年），字文远，雁门马邑（今山西朔州）人");
        mSGDatas.add(sgbean1);
        mSGDatas.add(sgbean2);
        mSGDatas.add(sgbean3);
        mSGDatas.add(sgbean4);
        mSGDatas.add(sgbean5);
        mSGDatas.add(sgbean6);
        mSGDatas.add(sgbean7);

    }

    /**
     * 初始化控件
     */
    private void initViewI() {
        mSGCommolySearchView = (CommolySearchView) findViewById(R.id.csv_show);
        mListView = (ListView) findViewById(R.id.lv_show);
        ibSearchVoice = findViewById(R.id.ib_search_voice);
        ibSearchHear = findViewById(R.id.ib_search_hear);
        ibSearchVoice.setOnClickListener(this);
        ibSearchHear.setOnClickListener(this);

        sgAdapter = new SGAdapter(this, mSGDatas);
        mListView.setAdapter(sgAdapter);
        // 设置数据源
        mSGCommolySearchView.setDatas(mSGDatas);
        // 设置适配器
        mSGCommolySearchView.setAdapter(sgAdapter);
        // 设置筛选数据
        mSGCommolySearchView.setSearchDataListener(new CommolySearchView.SearchDatas<SanGuoBean>() {
            @Override
            public List<SanGuoBean> filterDatas(List<SanGuoBean> datas, List<SanGuoBean> filterdatas, String inputstr) {
                for (int i = 0; i < datas.size(); i++) {
                    // 筛选条件
                    if ((datas.get(i).getSgDescribe()).contains(inputstr) || datas.get(i).getSgName().contains(inputstr) || datas.get(i).getSgPetName().contains(inputstr)) {
                        filterdatas.add(datas.get(i));
                    }
                }
                return filterdatas;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchActivity.this, mSGCommolySearchView.getFilterDatas().get(i).getSgName() + "字" + mSGCommolySearchView.getFilterDatas().get(i).getSgPetName() + "\n" + mSGCommolySearchView.getFilterDatas().get(i).getSgDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initSpeech() {
        // 将“12345678”替换成您申请的 APPID，申请地址： http://www.xfyun.cn
        // 请勿在 “ =”与 appid 之间添加任务空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5cc3450e");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_search_voice: //语音识别（把声音转文字）
                startSpeechDialog();
                break;
            case R.id.ib_search_hear:// 语音合成（把文字转声音）
                speekText();
                break;
        }

    }

    private void speekText() {
        //1. 创建 SpeechSynthesizer 对象 , 第二个参数： 本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(this, null);
//2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//设置发音人（更多在线发音人，用户可参见 附录 13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "vixyun"); // 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在 “./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
//3.开始合成
        mTts.startSpeaking(mSGCommolySearchView.getText(), new MySynthesizerListener());

    }

    class MySynthesizerListener implements SynthesizerListener {

        @Override
        public void onSpeakBegin() {
            showTip(" 开始播放 ");
        }

        @Override
        public void onSpeakPaused() {
            showTip(" 暂停播放 ");
        }

        @Override
        public void onSpeakResumed() {
            showTip(" 继续播放 ");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                showTip("播放完成 ");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话 id，当业务出错时将会话 id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话 id为null
            //if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //     String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //     Log.d(TAG, "session id =" + sid);
            //}
        }
    }

    private void requestRecordAudioPermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("Activity", "Granted!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("Activity", "Denied!");
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void startSpeechDialog() {
//        if (mywakeuper.isListening()) {
//            mywakeuper.stopListening();
//        }
        //1. 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        //2. 设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置中文
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        //4. 显示dialog，接收语音输入
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = results.getResultString(); //为解析的
            showTip(result);
            System.out.println(" 没有解析的 :" + result);

            String text = JsonParser.parseIatResult(result);//解析过后的
            System.out.println(" 解析后的 :" + text);

            String sn = null;
            // 读取json结果中的 sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }

            mSGCommolySearchView.setText(resultBuffer.toString());// 设置输入框的文本
            mSGCommolySearchView.setSelection();//把光标定位末尾
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败 ");
            }

        }
    }

    /**
     * 语音识别
     */
    private void startSpeech() {
        //1. 创建SpeechRecognizer对象，第二个参数： 本地识别时传 InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(this, null); //语音识别器
        //2. 设置听写参数，详见《 MSC Reference Manual》 SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");// 短信和日常用语： iat (默认)
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置中文
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");// 设置普通话
        //3. 开始听写
        mIat.startListening(mRecoListener);
    }


    // 听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        // 听写结果回调接口 (返回Json 格式结果，用户可参见附录 13.1)；
//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//关于解析Json的代码可参见 Demo中JsonParser 类；
//isLast等于true 时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.e(TAG, results.getResultString());
            System.out.println(results.getResultString());
            showTip(results.getResultString());
        }

        // 会话发生错误回调接口
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
            // 获取错误码描述
            Log.e(TAG, "error.getPlainDescription(true)==" + error.getPlainDescription(true));
        }

        // 开始录音
        public void onBeginOfSpeech() {
            showTip(" 开始录音 ");
        }

        //volume 音量值0~30， data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            showTip(" 声音改变了 ");
        }

        // 结束录音
        public void onEndOfSpeech() {
            showTip(" 结束录音 ");
        }

        // 扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    private void showTip(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
