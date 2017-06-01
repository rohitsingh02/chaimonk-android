package www.chaayos.com.chaimonkbluetoothapp.domain.model;

import java.io.Serializable;

/**
 * Created by rohitsingh on 10/07/16.
 */
public class UserSessionDetail implements Serializable {

    private int userId;
    private String userName;
    private String sessionKeyId;
    private int unitId;
    private String password;
    private String newPassword;
    private int terminalId;
    private String screenType;
    private String application;
    private String token;
    private String jwtToken;
    private Employee user;

    public UserSessionDetail(){
        super();
    }

    public UserSessionDetail(int userId,int unitId){
        super();
        this.userId = userId;
        this.unitId = unitId;
    }
    public UserSessionDetail(int userId, int unitId, String password) {
        super();
        this.userId = userId;
        this.unitId = unitId;
        this.password = password;
    }

    public UserSessionDetail(int userId, String sessinKeyId, int unitId, String password, String newPassword) {
        super();
        this.userId = userId;
        this.sessionKeyId = sessinKeyId;
        this.unitId = unitId;
        this.password = password;
        this.newPassword = newPassword;
    }

    public UserSessionDetail(int unitId,int userId,String password,int terminalId,String screenType,String application){
        super();
        this.unitId = unitId;
        this.userId = userId;
        this.password = password;
        this.terminalId = terminalId;
        this.screenType = screenType;
        this.application = application;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionKeyId() {
        return sessionKeyId;
    }

    public void setSessionKeyId(String sessionKeyId) {
        this.sessionKeyId = sessionKeyId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
