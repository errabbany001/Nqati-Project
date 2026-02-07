package element;

import java.util.ArrayList;
import tools.Session;

public class Enseignant {
    private String id_ens;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String depatement;
    private String dateDeNaissance;
    private String gener;
    private ArrayList<String[]> cours = new ArrayList<>();


    
    public Enseignant() {
    }

    public Enseignant(String id_ens, String nom, String prenom, String email, String depatement, String dateDeNaissace,
            String gener) {
        this.id_ens = id_ens;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.depatement = depatement;
        this.dateDeNaissance = dateDeNaissance;
        this.gener = gener;
        Session.setTheProfil(id_ens, false);
    }


    public String getId_ens() {
        return id_ens;
    }
    public void setId_ens(String id_ens) {
        this.id_ens = id_ens;
        Session.setTheProfil(id_ens, false);
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDepatement() {
        return depatement;
    }
    public void setDepatement(String depatement) {
        this.depatement = depatement;
    }
    public String getDateDeNaissance() {
        return dateDeNaissance;
    }
    public void setDateDeNaissance(String dateDeNaissace) {
        this.dateDeNaissance = dateDeNaissace;
    }
    public String getGener() {
        return gener;
    }
    public void setGener(String gener) {
        this.gener = gener;
    }
    public ArrayList<String[]> getCours() {
        return cours;
    }
    public void setCours(ArrayList<String[]> cours) {
        this.cours = cours;
    }



    
}
