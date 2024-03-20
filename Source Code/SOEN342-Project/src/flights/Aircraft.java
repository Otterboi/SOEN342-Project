package flights;

public class Aircraft {
    private int identifier;
    private String status;
    private boolean isPrivate;

    public Aircraft(){
        this.identifier = 0;
        this.status = "";
        this.isPrivate = false;
    }

    public Aircraft(int identifier, String status, boolean isPrivate){
        this.identifier = identifier;
        this.status = status;
        this.isPrivate = isPrivate;
    }

    public boolean getIsPrivate(){
        return this.isPrivate;
    }

    public void setPrivate(boolean isPrivate){
        this.isPrivate = isPrivate;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getIdentifier(){
        return this.identifier;
    }

    public void setIdentifier(int id){
        this.identifier = id;
    }
}
