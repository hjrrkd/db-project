// 1번메뉴: DB connect
// DB와의 연결이 올바르게 이루어졌는지를 확인

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectMenu {
    private static final String JDBC_URL = "jdbc:mysql://192.168.56.101:4567/project";
    private static final String USER = "heejukang";
    private static final String PASSWORD = "rkdgmlwn";

    public static void handleConnectionMenu() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            if (connection != null && !connection.isClosed()) {
                // 연결이 성공적으로 이루어졌을 때
                String userName = USER;  // 현재 user 이름
                String currentTime = getCurrentTime();  // 현재 시각 가져오기

                System.out.println("Connection successful! :)");
                System.out.println("Current user: " + userName);
                System.out.println("Current time: " + currentTime);
            } else {
                // 연결이 실패했을 때
                System.out.println("Connection failed :(");
            }

            connection.close();  // 연결 종료
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    private static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\n"); //현재 접속 시간 출력
        return sdf.format(new Date());
    }
}