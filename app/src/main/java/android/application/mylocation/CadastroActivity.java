package android.application.mylocation;

import static android.application.mylocation.R.id.txNome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.Principal;
//import com.example.sistema.modelo.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText txNome;
    private EditText txEmail;
    private EditText txSenha;
    private Button btCadastro;
    private FirebaseAuth mAuth;
    private CheckBox swAdm;
    private Usuario u;

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txEmail = findViewById(R.id.txEmail);
      //  txNome = findViewById(R.id.txNome);
        txSenha = findViewById(R.id.txSenha);
        btCadastro = findViewById(R.id.btCadastro);
      //  swAdm = findViewById(R.id.swAdmin);
        mAuth = FirebaseAuth.getInstance();

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarDados();
                criarLogin();
            }
        });

        }

    private void criarLogin() {
        mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            u.setId(user.getUid());
                            u.salvarDados();
                           /**if(u.getAdm()){
                                Toast.makeText(CadastroActivity.this,"Tela do administrador",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(CadastroActivity.this,AdmActivity.class));
                            }else{*/
                                startActivity(new Intent(CadastroActivity.this,MapsActivity.class));
                            //}
                    }else {
                            Toast.makeText(CadastroActivity.this,"Erro ao criar usuario",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void recuperarDados() {
        if(txEmail.getText().toString()==""||txSenha.getText().toString()==""){
            Toast.makeText(this,"Você deve preencher todos os dados",Toast.LENGTH_LONG);
        }else {
            u = new Usuario();
            //u.setNome(txNome.getText().toString());
            u.setEmail(txEmail.getText().toString());
            u.setSenha(txSenha.getText().toString());
           /** if (swAdm.isChecked()){
                u.setAdm(true);
            }else {
                u.setAdm(false);
            }*/
        }
}
}