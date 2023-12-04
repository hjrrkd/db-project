// 7번메뉴: 여행지 삭제
// 사용자로부터 삭제할 여행지 정보(지역, 도시)를 입력받아 Destination 테이블에서 해당 레코드 삭제

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteDestinationMenu {

    public static void handleDeleteDestinationMenu(Connection connection) {
        System.out.println("여행지 삭제 메뉴를 선택하셨습니다.");

        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 삭제할 여행지의 지역과 도시 입력 받기
        System.out.print("삭제할 여행지의 지역(Region)을 입력하세요: ");
        String region = scanner.nextLine().trim();
        
        System.out.print("삭제할 여행지의 도시(City)를 입력하세요: ");
        String city = scanner.nextLine().trim();

        try {
            // 여행지 삭제 쿼리 작성
            String deleteQuery = "DELETE FROM Destination WHERE Region = ? AND City = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setString(1, region);
            deleteStatement.setString(2, city);

            // 쿼리 실행
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("여행지가 성공적으로 삭제되었습니다.");
                System.out.println("------------");
            } else {
                System.out.println("여행지 삭제에 실패했습니다. 입력된 도시와 지역을 확인하세요.");
                System.out.println("------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
