package com.example.androidfinal_project;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidfinal_project.R;

public class FragmentMap extends Fragment {
    EditText editText;
    WebView webView;
    Button btnGo, btnDel;

    public FragmentMap() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        editText = rootView.findViewById(R.id.webText);
        webView = rootView.findViewById(R.id.webview);
        btnGo = rootView.findViewById(R.id.buttongo);
        btnDel = rootView.findViewById(R.id.buttonback);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        String youtubeVideoUrl = "https://www.youtube.com/results?search_query=%ED%97%AC%EC%8A%A4+%EC%B4%88%EB%B3%B4"; // Example YouTube video
        webView.loadUrl(youtubeVideoUrl);

        btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String address = editText.getText().toString();
                if (!address.startsWith("http://")) {
                    address = "http://" + address;
                }
                webView.loadUrl(address);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editText.setText(null);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        return rootView;
    }
}