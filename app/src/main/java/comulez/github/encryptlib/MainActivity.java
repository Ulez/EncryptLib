package comulez.github.encryptlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResult;
    private EditText etKey;
    private TextView tvText;
    private Button btEncrypt;
    private Button btDecrypt;
    private TextView tvText2;
    private Encrypt4C encrypt4C;
//    private Encrypt4J encrypt4J;
    private String keyyy;
    private String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = findViewById(R.id.text);
        etKey = findViewById(R.id.text_key);
        btEncrypt = findViewById(R.id.bt_encrypt);
        tvResult = findViewById(R.id.result);
        btDecrypt = findViewById(R.id.bt_decrypt);
        tvText2 = findViewById(R.id.text2);

        encrypt4C = new Encrypt4C();
//        encrypt4J = new Encrypt4J();
        btEncrypt.setOnClickListener(this);
        btDecrypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_encrypt:
                encrypt();
                break;
            case R.id.bt_decrypt:
                decrypt();
                break;
        }

    }

    private void encrypt() {
        try {
            txt = tvText.getText().toString();
            keyyy = etKey.getText().toString();
            e("加密前，txt=" + txt);
            e("加密前，key=" + keyyy);
            byte[] origin = txt.getBytes(StandardCharsets.UTF_8);
            byte[] encryptionKey = keyyy.getBytes(StandardCharsets.UTF_8);

//            byte[] result = encrypt4J.aesEncrypt(origin, encryptionKey);
//            byte[] decryptedCipherText = encrypt4J.aesDecrypt(result, encryptionKey);
//            e("in java=" + new String(origin));
//            e("in java=" + new String(Base64.encode(result, Base64.DEFAULT)));
//            e("in java=" + new String(decryptedCipherText));

            byte[] bytes_result = encrypt4C.AES_ECB_encrypt_byte(origin, encryptionKey);
            byte[] bytes_result_base64 = Base64.encode(bytes_result, Base64.DEFAULT);
            e("ndk,加密后==" + new String(bytes_result_base64));
            tvResult.setText(new String(bytes_result_base64));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void decrypt() {
        try {
            byte[] encryptionKey = keyyy.getBytes(StandardCharsets.UTF_8);
            byte[] bytes_result_base64 = tvResult.getText().toString().getBytes();

            byte[] result4wrwer = Base64.decode(bytes_result_base64, Base64.DEFAULT);
            byte[] bytes1 = encrypt4C.AES_ECB_decrypt_byte(result4wrwer, encryptionKey);
            String dfsfsf = new String(bytes1);
            e("ndk,解密后==" + dfsfsf);
            tvText2.setText(dfsfsf);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public static void e(String s) {
        Log.e("lcy", s);
    }

    public static void i(String s) {
        Log.i("lcy", s);
    }
}
