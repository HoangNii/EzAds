package cheoapp.admin.ezlife.com.ezad;

public class AppEz {
    private String appId;
    private String[] appTag;
    private String appIcon;
    private String appName;
    private String appDesc;
    private String AppReview;
    private String appRate;
    private String appDir;
    private String[] appScreen;
    private int appRank;

    public String getAppReview() {
        return AppReview;
    }

    public void setAppReview(String appReview) {
        AppReview = appReview;
    }

    public String getAppRate() {
        return appRate;
    }

    public void setAppRate(String appRate) {
        this.appRate = appRate;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String[] getAppTag() {
        return appTag;
    }

    public void setAppTag(String[] appTag) {
        this.appTag = appTag;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppDir() {
        return appDir;
    }

    public void setAppDir(String appDir) {
        this.appDir = appDir;
    }

    public String[] getAppScreen() {
        return appScreen;
    }

    public void setAppScreen(String[] appScreen) {
        this.appScreen = appScreen;
    }

    public int getAppRank() {
        return appRank;
    }

    public void setAppRank(int appRank) {
        this.appRank = appRank;
    }
}
