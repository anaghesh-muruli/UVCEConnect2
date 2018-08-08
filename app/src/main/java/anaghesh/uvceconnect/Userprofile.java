package anaghesh.uvceconnect;

public class Userprofile {
    private String profileName;
    private String profileUSN;
    private String profileEmailid;
    private String profileBranch;
    private String profileYear;

    public Userprofile(){

    }
    public String getProfileYear() {
        return profileYear;
    }

    public void setProfileYear(String name) {
        profileYear = name;
    }

    public String getProfileBranch() {
        return profileBranch;
    }

    public void setProfileBranch(String name) {
        profileBranch = name;
    }

    public String getprofileName() {
        return profileName;
    }

    public void setprofileName(String name) {
        profileName = name;
    }

    public String getprofileUSN() {
        return profileUSN;
    }

    public void setUSN(String USN) {
        this.profileUSN = USN;
    }

    public String getprofileEmailid() {
        return profileEmailid;
    }

    public void setEmailid(String email) {
        profileEmailid = email;
    }

    public Userprofile(String userName, String USN, String userEmail, String branch, String year) {
        this.profileName = userName;
        this.profileUSN = USN;
        this.profileEmailid = userEmail;
        this.profileBranch = branch;
        this.profileYear = year;

    }



}
