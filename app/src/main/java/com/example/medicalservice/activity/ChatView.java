package com.example.medicalservice.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.medicalservice.MyApplication;
import com.example.medicalservice.R;
import com.example.medicalservice.activity.healthcareChildActivity.useMadChildView.addMedicineChildView.FrequencyAdministration;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityChatViewBinding;
import com.example.medicalservice.recycleAdapter.AiBegHeaderAdapter;
import com.example.medicalservice.tools.API;
import com.example.medicalservice.tools.ChatBean;
import com.example.medicalservice.tools.GlideUtils;
import com.example.medicalservice.tools.JsonParser;
import com.example.medicalservice.tools.MyMigration;
import com.example.medicalservice.tools.OkHttpUtil;
import com.example.medicalservice.tools.TimeTool;
import com.google.gson.Gson;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChatView extends BaseActivity<ActivityChatViewBinding> {


    List<ChatBean> chatBeans = new ArrayList<>();

    ChatAdapter chatAdapter;

    public static ChatView mActivity;

    private SpeechRecognizer mIat;

    private RecognizerListener recognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            viewBinding.voicePlay.cancelAnimation();
            Log.d("TAG", "onResult: " + printResult(recognizerResult));

            if (!printResult(recognizerResult).isEmpty()) {
                viewBinding.message.setText(printResult(recognizerResult));
                seedMessage(printResult(recognizerResult));
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    private String printResult(RecognizerResult results) {
        Map<String, String> mIatResults = new HashMap<>();
        String text = JsonParser.parseIatResult(results.getResultString());


        String sn = null;
        String pgs = null;
        String rg = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
            pgs = resultJson.optString("pgs");
            rg = resultJson.optString("rg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //如果pgs是rpl就在已有的结果中删除掉要覆盖的sn部分
        if (pgs.equals("rpl")) {
            String[] strings = rg.replace("[", "").replace("]", "").split(",");
            int begin = Integer.parseInt(strings[0]);
            int end = Integer.parseInt(strings[1]);
            for (int i = begin; i <= end; i++) {
                mIatResults.remove(i + "");
            }
        }

        mIatResults.put(sn, text);
        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        if (resultBuffer.toString().isEmpty()) return "";
        isChinesePunctuation(resultBuffer.toString().charAt(0));

        if (isChinesePunctuation(resultBuffer.toString().charAt(0))) return "";

        return resultBuffer.toString();

    }

    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initView() {
        super.initView();

        mActivity = this;
        initTalk();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewBinding.chatList.setLayoutManager(linearLayoutManager);

        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.voice.setOnClickListener(v -> {

            if (viewBinding.talkBox.getVisibility() == View.GONE) {
                viewBinding.talkBox.setVisibility(View.VISIBLE);

            } else {
                viewBinding.talkBox.setVisibility(View.GONE);

            }
        });


        if (getIntent().getStringExtra("msg") != null) {
            viewBinding.talkBox.setVisibility(View.VISIBLE);
            viewBinding.voicePlay.playAnimation();

            if (mIat != null) {
                mIat.startListening(recognizerListener);
            }
        }

        viewBinding.voicePlay.setOnClickListener(v -> {

            if (viewBinding.voicePlay.isAnimating()) {
                viewBinding.voicePlay.cancelAnimation();
                mIat.stopListening();
            } else {
                viewBinding.voicePlay.playAnimation();
                mIat.startListening(recognizerListener);
            }

        });
    }

    @Override
    public void initData() {
        super.initData();

        chatAdapter = new ChatAdapter(chatBeans);
        viewBinding.chatList.setAdapter(chatAdapter);

        viewBinding.seed.setOnClickListener(v -> {
            if (viewBinding.message.getText().toString().isEmpty()) {
                showToast("请输入内容");
                return;
            }
            seedMessage(viewBinding.message.getText().toString());
            hideSoftWareInput();
        });
    }

    private void initTalk() {
        mIat = SpeechRecognizer.createRecognizer(this, null);
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
//设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
//此处engineType为“cloud”
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//设置结果返回语言
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
// 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
//取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
//设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
//自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
//设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

    }

    @SuppressLint("NotifyDataSetChanged")
    private void seedMessage(String message) {

        ChatBean chatBean = new ChatBean(message, TimeTool.Day(0, "YYYY-MM-dd HH:mm"), true, true);

        chatBeans.add(chatBean);

        chatAdapter.notifyDataSetChanged();


        mIat.stopListening();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("askStr", message);
            OkHttpUtil.getInstance().doPost(API.chatGpt, jsonObject.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    Log.d("TAG", "onFailure: " + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("TAG", "onResponse: " + "成功");
                    if (response.body() != null) {
                        try {
                            JSONObject data = new JSONObject(response.body().string());
                            if (data.getInt("code") == 200) {

                                ChatBean chat = new ChatBean(data.getJSONObject("data").getString("replyStr"), TimeTool.Day(0, "YYYY-MM-dd HH:mm"), false, false);
                                chatBeans.add(chat);


                            } else {
                                ChatBean chat = new ChatBean("请求失败，请联系管理员", TimeTool.Day(0, "YYYY-MM-dd HH:mm"), false, false);
                                chatBeans.add(chat);

                            }

                            chatBeans.get(chatBeans.size() - 2).setSeeding(false);

                            runOnUiThread(() -> {

                                chatAdapter.notifyDataSetChanged();

                            });
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewBinding.message.setText("");
        viewBinding.message.clearFocus();

    }

    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private List<ChatBean> chatBeans;


        public ChatAdapter(List<ChatBean> chatBeans) {
            this.chatBeans = chatBeans;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            ChatBean chatBean = chatBeans.get(position);


            if (chatBean.isSelf()) {
                holder.self_msg_box.setVisibility(View.VISIBLE);
                holder.other_msg_box.setVisibility(View.GONE);
                GlideUtils.load(holder.itemView.getContext(), MyApplication.getInstance().getUserHeader(), holder.self_header, R.drawable.header, 90);
                holder.self_msg.setText(chatBean.getMsg());

                if (!chatBean.isSeeding()) {
                    holder.loading.setVisibility(View.GONE);
                }
            } else {
                holder.self_msg_box.setVisibility(View.GONE);
                holder.other_msg_box.setVisibility(View.VISIBLE);
                holder.other_header.setImageResource(R.drawable.voice_icon);
                holder.other_msg.setText(chatBean.getMsg());
            }
        }

        @Override
        public int getItemCount() {
            return chatBeans.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView other_header, self_header;
            private TextView other_msg, self_msg;
            private LinearLayout other_msg_box, self_msg_box;

            private LottieAnimationView loading;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                loading = itemView.findViewById(R.id.loading);
                other_header = itemView.findViewById(R.id.other_header);
                self_header = itemView.findViewById(R.id.self_header);
                other_msg_box = itemView.findViewById(R.id.other_msg_box);
                self_msg_box = itemView.findViewById(R.id.self_msg_box);
                other_msg = itemView.findViewById(R.id.other_msg);
                self_msg = itemView.findViewById(R.id.self_msg);
            }
        }
    }

    //收起软键盘
    public void hideSoftWareInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(viewBinding.seed.getWindowToken(), 0);
    }

}