// 6번메뉴: 여행 상세 정보 조회
// 사용자의 여행 일정과 예약 정보를 함께 제공하여 여행에 대한 전반적인 내용을 확인할 수 있게 함

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TravelInfoViewerMenu {

    public static void handleTravelInfoViwerMenu(Connection connection) {
        System.out.println("여행 상세 정보 조회 메뉴를 선택하셨습니다.");

        try {
            // 여행자의 이름 입력 받기
            Scanner scanner = new Scanner(System.in);
            System.out.print("여행자의 이름을 입력하세요: ");
            String travelerName = scanner.nextLine().trim();

            String query = "SELECT t.Name, d.Region, d.City, d.Attractions, i.StartDate, i.EndDate, r.Accommodation, r.ReservationDate, r.Amount " +
                    "FROM Traveler t " +
                    "JOIN Itinerary i ON t.TravelerID = i.TravelerID " +
                    "JOIN Destination d ON i.DestinationID = d.DestinationID " +
                    "LEFT JOIN Reservation r ON t.TravelerID = r.TravelerID " +
                    "WHERE t.Name = ?";


            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, travelerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과 출력
            while (resultSet.next()) {
                System.out.println("여행자 이름: " + resultSet.getString("Name"));
                System.out.println("여행지: " + resultSet.getString("Region") + ", " + resultSet.getString("City"));
                System.out.println("여행 일정: " + resultSet.getString("StartDate") + " ~ " + resultSet.getString("EndDate"));
                System.out.println("관광명소: " + resultSet.getString("Attractions"));
                System.out.println("숙소: " + resultSet.getString("Accommodation"));
                System.out.println("예약 날짜: " + resultSet.getString("ReservationDate"));
                System.out.println("금액: " + resultSet.getString("Amount"));
                System.out.println("------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
