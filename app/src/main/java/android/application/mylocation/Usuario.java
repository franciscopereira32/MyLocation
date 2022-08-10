package android.application.mylocation;

import com.google.firebase.database.DatabaseReference;

public class Usuario {
   // private String nome;
    private String email;
    private String senha;
    private String id;
   // private Boolean adm;

  /**  public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   /** public Boolean getAdm() {
        return adm;
    }

    public void setAdm(Boolean adm) {
        this.adm = adm;
    }*/

    public void salvarDados() {
        /**DatabaseReference firebase = ConfiguracaoFirebase.getFireDatabase();
        firebase.child("Usuarios").child(this.id).setValue(this);**/
    }
}
