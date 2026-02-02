package element;

public class Note {
    private double normal = -1;
    private double rattrapage = -1;
    private double finalle = -1;
    private String statu = "-";

    public Note(){
        this.normal = -1;
        this.rattrapage = -1;
        this.finalle = -1;
        statu = "-";
    }

    public Note(double normal){
        this.normal = normal;
        this.finalle = -1;
        statu = "-";
        updateStatus();
        
    }

    public Note(double normal, double rattrapage) {
        this.normal = normal;
        this.rattrapage = rattrapage;
        statu = "-";
        updateStatus();
    }

    //============================================================

    public double getNormal() {
        statu = "-";
        updateStatus();
        return normal;
    }
    public void setNormal(double normal) {
        this.normal = normal;
        statu = "-";
        updateStatus();
    }
    public double getRattrapage() {
        updateStatus();
        return rattrapage;
    }
    public void setRattrapage(double rattrapage) {
        this.rattrapage = rattrapage;
        this.finalle = -1;
        statu = "-";
        updateStatus();
    }
    public double getFinalle() {
        updateStatus();
        return finalle;
    }
    public void setFinalle(double finalle) {
        this.finalle = finalle;
        updateStatus();
    }
   
    public String show() {
        return "Note [normal=" + this.normal + ", rattrapage=" + this.rattrapage +  ", finalle= "+this.finalle+  ", statu: "+statu+ " ]\n";
    }

    public String getStatu() {
        updateStatus();
        return statu;
    }
    public void setStatu(String statu) {
        this.statu = statu;
        updateStatus();
    }
    

        private void updateStatus() {
            // Case 1: Rattrapage exists
            if (this.rattrapage != -1) {
                if (this.rattrapage >= 10) {
                    this.finalle = 10.0;
                    this.statu = "V";
                } else {
                    this.finalle = Math.max(this.normal, this.rattrapage);
                    if (this.finalle >= 10) {
                        this.statu = "V";
                    }
                     else if (this.finalle != -1) {
                        this.statu = "NV";
                    }
                }
            } 
            // Case 2: Only Normal exists
            else if (this.normal != -1) {
                // DON'T set finalle here - only set it when rattrapage happens
                // this.finalle = this.normal;  // REMOVE THIS LINE
                
                if (this.normal >= 10) {
                    this.statu = "V";
                    this.finalle = this.normal;  
                } else {
                    this.statu = "R";
                    this.finalle = -1;  // Keep final as unset
                }
            }
        }
    
}
