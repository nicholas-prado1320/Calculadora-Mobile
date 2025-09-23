package com.example.prjcalculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtValor1, edtValor2;
    TextView txvResultado, txvResultadoVI, txvMemoria, txvMemoriaVI;
    Button btnSomar, btnSubtrair, btnMultiplicar, btnDividir, btnLimpar, btnSair, btnMemoriaHist;
    Button btnMemoMais, btnMemoMenos, btnMemoRec, btnMemoClear;
    Button btnCelsiusToFah, btnFahToCelsius, btnPercentual;

    double vlMemoria = 0;
    double vlResultado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtValor1 = findViewById(R.id.edtValor1);
        edtValor2 = findViewById(R.id.edtValor2);
        txvResultado = findViewById(R.id.txvResultado);
        txvResultadoVI = findViewById(R.id.txvResultadoVI);
        txvMemoria = findViewById(R.id.txvMemoria);
        txvMemoriaVI = findViewById(R.id.txvMemoriaVI);

        btnSomar = findViewById(R.id.btnSomar);
        btnSubtrair = findViewById(R.id.btnSubtrair);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnMemoriaHist = findViewById(R.id.btnMemoriaHist);
        btnSair = findViewById(R.id.btnSair);

        btnMemoMais = findViewById(R.id.btnMemoMais);
        btnMemoMenos = findViewById(R.id.btnMemoMenos);
        btnMemoRec = findViewById(R.id.btnMemoRec);
        btnMemoClear = findViewById(R.id.btnMemoClear);

        btnCelsiusToFah = findViewById(R.id.btnCelsiusToFah);
        btnFahToCelsius = findViewById(R.id.btnFahToCelsius);
        btnPercentual = findViewById(R.id.btnPercentual);

        txvMemoria.setVisibility(TextView.INVISIBLE);
        txvMemoriaVI.setVisibility(TextView.INVISIBLE);
        btnMemoRec.setEnabled(false);
        btnMemoClear.setEnabled(false);

        btnSomar.setOnClickListener(v -> calcular("+"));
        btnSubtrair.setOnClickListener(v -> calcular("-"));
        btnMultiplicar.setOnClickListener(v -> calcular("*"));
        btnDividir.setOnClickListener(v -> calcular("/"));
        btnLimpar.setOnClickListener(v -> limpar());
        btnMemoriaHist.setOnClickListener(v -> salvarMemoria());
        btnSair.setOnClickListener(v -> finish());

        btnMemoMais.setOnClickListener(v -> {
            vlMemoria += vlResultado;
            txvMemoria.setVisibility(TextView.VISIBLE);
            txvMemoriaVI.setVisibility(TextView.VISIBLE);
            btnMemoRec.setEnabled(true);
            btnMemoClear.setEnabled(true);
            txvMemoriaVI.setText(String.valueOf(vlMemoria));
        });

        btnMemoMenos.setOnClickListener(v -> {
            vlMemoria -= vlResultado;
            txvMemoria.setVisibility(TextView.VISIBLE);
            txvMemoriaVI.setVisibility(TextView.VISIBLE);
            btnMemoRec.setEnabled(true);
            btnMemoClear.setEnabled(true);
            txvMemoriaVI.setText(String.valueOf(vlMemoria));
        });

        btnMemoRec.setOnClickListener(v -> edtValor1.setText(String.valueOf(vlMemoria)));

        btnMemoClear.setOnClickListener(v -> {
            vlMemoria = 0;
            txvMemoria.setVisibility(TextView.INVISIBLE);
            txvMemoriaVI.setVisibility(TextView.INVISIBLE);
            btnMemoRec.setEnabled(false);
            btnMemoClear.setEnabled(false);
        });

        btnCelsiusToFah.setOnClickListener(v -> {
            String v1 = edtValor1.getText().toString();
            String v2 = edtValor2.getText().toString();

            if (!v2.isEmpty()) {
                txvResultadoVI.setText("Limpe V2 para usar CF");
                return;
            }
            if (v1.isEmpty()) {
                txvResultadoVI.setText("Informe V1");
                return;
            }
            double celsius = Double.parseDouble(v1);
            double fahrenheit = (celsius * 9 / 5) + 32;
            vlResultado = fahrenheit;
            txvResultadoVI.setText(String.format("%.2f", fahrenheit));
        });

        btnFahToCelsius.setOnClickListener(v -> {
            String v1 = edtValor1.getText().toString();
            String v2 = edtValor2.getText().toString();

            if (!v2.isEmpty()) {
                txvResultadoVI.setText("Limpe V2 para usar FC");
                return;
            }
            if (v1.isEmpty()) {
                txvResultadoVI.setText("Informe V1");
                return;
            }
            double fahrenheit = Double.parseDouble(v1);
            double celsius = (fahrenheit - 32) * 5 / 9;
            vlResultado = celsius;
            txvResultadoVI.setText(String.format("%.2f", celsius));
        });

        btnPercentual.setOnClickListener(v -> {
            String v1 = edtValor1.getText().toString();
            String v2 = edtValor2.getText().toString();

            if (v1.isEmpty() || v2.isEmpty()) {
                txvResultadoVI.setText("Informe V1 e V2");
                return;
            }
            double n1 = Double.parseDouble(v1);
            double n2 = Double.parseDouble(v2);

            if (n1 == 0) {
                txvResultadoVI.setText("Erro: V1 = 0");
                return;
            }

            double resultado = (n2 / n1) * 100;
            vlResultado = resultado;
            txvResultadoVI.setText(String.format("%.2f%%", resultado));
        });
    }

    private void calcular(String op) {
        String v1 = edtValor1.getText().toString();
        String v2 = edtValor2.getText().toString();

        if (v1.isEmpty() || v2.isEmpty()) {
            txvResultadoVI.setText("Digite os dois valores!");
            return;
        }

        double n1 = Double.parseDouble(v1);
        double n2 = Double.parseDouble(v2);
        double res = 0;

        switch (op) {
            case "+": res = n1 + n2; break;
            case "-": res = n1 - n2; break;
            case "*": res = n1 * n2; break;
            case "/":
                if (n2 == 0) {
                    txvResultadoVI.setText("Erro: divisão por zero");
                    return;
                }
                res = n1 / n2;
                break;
        }

        vlResultado = res;
        txvResultadoVI.setText(String.valueOf(res));
    }

    private void limpar() {
        edtValor1.setText("");
        edtValor2.setText("");
        txvResultadoVI.setText("");
        vlResultado = 0;
    }

    private void salvarMemoria() {
        txvMemoriaVI.setText(String.valueOf(vlMemoria));
    }
}
