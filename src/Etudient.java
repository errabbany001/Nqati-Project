
import java.sql.Date;






public class Etudient {
    private String cne;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Date dateDeNaissance;
    private String option;
    private String niveau;
    private int nb_sem;
    






    public Etudient(int nb_sem){
        cne = "xxx";
        nom = "floan";
        prenom = "el folani";
        email = "folan.elfolani@gmail.com";
        password = "12345678";
        dateDeNaissance = Date.valueOf("01-01-2001");
        option = "sidi";
        niveau = "master";
        this.nb_sem = nb_sem;
        
    }



    public Etudient(String cne, String nom, String prenom, String email, String password, Date dateDeNaissance,
            String option, String niveau) {
        this.cne = cne;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateDeNaissance = dateDeNaissance;
        this.option = option;
        this.niveau = niveau;
    }
    public String getCne() {
        return cne;
    }
    public void setCne(String cne) {
        this.cne = cne;
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
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }
    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
    public String getOption() {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }
    public String getNiveau() {
        return niveau;
    }
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }



    public int getNb_sem() {
        return nb_sem;
    }


    public void setNb_sem(int nb_sem) {
        this.nb_sem = nb_sem;
    } 
    
    
}
