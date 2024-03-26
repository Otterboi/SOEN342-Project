package flights;

public class Aircraft {
    private String identifier;
    private String status;
    private boolean isPrivate;
    public Aircraft(){
        this.identifier = "";
        this.status = "";
        this.isPrivate = false;
    }
    public Aircraft(String identifier, String status, boolean isPrivate){
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

    public String getIdentifier(){
        return this.identifier;
    }

    public void setIdentifier(String id){
        this.identifier = id;
    }
}
