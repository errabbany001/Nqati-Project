package element;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import tools.Connexion;
import tools.Session;

public class Etudiant {
    private String cne;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String dateDeNaissance;
    private String filier;
    private String niveau;
    private String semester;
    private String etu_class;
    private ArrayList<ArrayList<Note>> notes = new ArrayList<>();
    private ArrayList<Double> semsterNotes = new ArrayList<>();
    

    public Etudiant() {
    }
    
    public Etudiant(String cne, String nom, String prenom, String dateDeNaissance, String etu_class, String filier, String niveau,
            String semester) {
        this.cne = cne;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.etu_class=  etu_class;
        this.filier = filier;
        this.niveau = niveau;
        this.semester = semester;
        Session.setTheProfil(this.cne , true);
    }





    public Etudiant(String cne, String nom, String prenom, String email, String password, String dateDeNaissance,
            String filier, String niveau, String semester) {
        this.cne = cne;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateDeNaissance = dateDeNaissance;
        this.filier = filier;
        this.niveau = niveau;
        this.semester = semester;
        Session.setTheProfil(this.cne , true);
    }



public void notesCheck() {
    semsterNotes.clear();

    for (ArrayList<Note> list : notes) {

       
        if (list == null || list.isEmpty()) {
            semsterNotes.add(-1.0);
            continue;
        }

        double sum = 0;
        boolean missing = false;

        for (Note n : list) {
            double f = n.getFinalle();
            if (f == -1) {
                missing = true;
                break;
            }
            sum += f;
        }

        if (missing) {
            semsterNotes.add(-1.0);
        } else {
            double avg = sum / list.size();
            avg = Math.round(avg * 100.0) / 100.0;
            semsterNotes.add(avg);
        }
    }
}





    public void  updateNotes(){

    }


    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
        Session.setTheProfil(this.cne , true);
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

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getFilier() {
        return filier;
    }

    public void setFilier(String filier) {
        this.filier = filier;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEtu_class() {
        return etu_class;
    }

    public void setEtu_class(String etu_class) {
        this.etu_class = etu_class;
    }

    public ArrayList<ArrayList<Note>> getNotes() {
        notesCheck();
        return notes;
    }

    public void setNotes(ArrayList<ArrayList<Note>> notes) {
        notesCheck();
        this.notes = notes;
    }

    public ArrayList<Double> getSemsterNotes() {
        return semsterNotes;
    }

    public void setSemsterNotes(ArrayList<Double> semsterNotes) {
        this.semsterNotes = semsterNotes;
    }

    @Override
    public String toString() {
        notesCheck();
        String text = " ";
        for(ArrayList<Note> list : notes){
            for (Note note : list){
                text = text + note.show();
            }
        }
        
        for (Double num : semsterNotes){
            text += num + "\n";
        }
        text += "size = " + notes.size();
        return text;
    }


    


}
