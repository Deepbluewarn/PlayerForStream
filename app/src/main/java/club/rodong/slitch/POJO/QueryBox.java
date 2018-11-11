package club.rodong.slitch.POJO;

public class QueryBox {

    public static final String TBL_STID = "STID";

    public static final String COL_STID = "STID";
    public static final String COL_DISP_NAME = "DISP_NAME";
    public static final String COL_USER_LOGIN ="USER_LOGIN";
    public static final String COL_PROFILEIMGURL = "PROFILEIMGURL";

    public static final String Twitch_sqlCreateTbl = "CREATE TABLE IF NOT EXISTS " + TBL_STID + "("+COL_STID+" INTEGER PRIMARY KEY, "+COL_DISP_NAME+" TEXT, "+COL_USER_LOGIN+" TEXT, "+COL_PROFILEIMGURL+" TEXT)";

    public static final String Select_STID = "SELECT * FROM "+TBL_STID;

    public static final String Select_Count_Twitch = "SELECT COUNT(*) FROM " + TBL_STID;

    public static final String SelectWhere_STID = "SELECT * FROM "+TBL_STID+" WHERE "+COL_STID+" =";

    public static final String Insert_STID = "INSERT OR REPLACE INTO "+TBL_STID+" ("+COL_STID+", "+COL_DISP_NAME+", "+COL_USER_LOGIN+", "+COL_PROFILEIMGURL+") VALUES";

    public static final String Delete_STID = "DELETE FROM "+ TBL_STID;
}
