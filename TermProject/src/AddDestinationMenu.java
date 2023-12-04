// 3번메뉴: 여행 일정에 여행지 추가
// 사용자로부터 여행지 정보(지역, 도시, 관광명소, 숙소)를 입력받아 Destination 테이블에 새로운 레코드를 추가

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddDestinationMenu {

    public static void handleAddDestinationMenu(Connection connection) {
        System.out.println("여행지 추가 메뉴를 선택하셨습니다.");

        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 여행지 정보 입력 받기
        System.out.print("지역(Region)을 입력하세요: ");
        String region = scanner.nextLine().trim();

        System.out.print("도시(City)를 입력하세요: ");
        String city = scanner.nextLine().trim();

        System.out.print("관광명소를 입력하세요(여러개는 콤마로 구분): ");
        String attractions = scanner.nextLine().trim();

        System.out.print("숙소를 입력하세요: ");
        String accommodation = scanner.nextLine().trim();

        // 여행지 추가
        try {
            String insertQuery = "INSERT INTO Destination (Region, City, Attractions, Accommodation) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, region);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, attractions);
            preparedStatement.setString(4, accommodation);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("여행지가 성공적으로 추가되었습니다.");
                System.out.println("------------");
            } else {
                System.out.println("여행지 추가에 실패했습니다.");
                System.out.println("------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
