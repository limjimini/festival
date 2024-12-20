package greenart.festival;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleDBExample {
    public static void main(String[] args) {
        // JDBC URL, 사용자명, 비밀번호
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE"; // SID 사용 예시
        String username = "festival";
        String password = "1234";

        try {
            // 1. 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 2. 연결 설정
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

            // 3. SQL 쿼리 실행
            String sql = "SELECT * FROM festivals";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // 4. 결과 처리
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                String period = rs.getString("period");
                String location = rs.getString("location");
                System.out.println("Festival: " + title + ", " + content + ", " + period + ", " + location);
            }

            // 5. 연결 종료
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
