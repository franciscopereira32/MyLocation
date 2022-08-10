package android.application.mylocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ValidacaoActivity extends AppCompatActivity {

    private EditText txSenha;
    private EditText txEmail;
    private Button btLogar;
    //private Button btCadastro

    private FirebaseAuth mAuth;;
    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacao);


        txEmail = findViewById(R.id.txEmailValida);
        txSenha = findViewById(R.id.txSenhaValida);
        btLogar = findViewById(R.id.btLogar);
        //btCadastro = findViewById(R.id.btCadastro)
        mAuth = FirebaseAuth.getInstance();

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receberDados();
                logar();
            }
        });
    }

    private void logar() {
        mAuth.signInWithEmailAndPassword(u.getEmail(), u.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(ValidacaoActivity.this, MapsActivity.class));
                        }else{
                            Toast.makeText(ValidacaoActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void receberDados() {
        u =  new Usuario();
        u.setEmail(txEmail.getText().toString());
        u.setSenha(txSenha.getText().toString());
    }
}
