//2번메뉴: 여행자의 여행 일정 조회
//Traveler,Destination,Itinerary 테이블을 활용하여 여행 일정 조회
//사용자로부터 여행자의 이름 또는 ID를 입력받아 해당 여행자의 여행 일정 조회

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TravelScheduleMenu {

    public static void handleTravelScheduleMenu(Connection connection) {
        System.out.println("여행자의 여행 일정 조회 및 수정 메뉴를 선택하셨습니다 aa.");

        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 여행자의 이름 또는 ID 입력 받기
        System.out.print("여행자의 이름 또는 ID를 입력하세요bb: ");
        String travelerInput = scanner.nextLine().trim();

        // 여행 일정 조회
        try {
            String query = "SELECT * FROM Traveler t " +
                           "JOIN Itinerary i ON t.TravelerID = i.TravelerID " +
                           "JOIN Destination d ON i.DestinationID = d.DestinationID " +
                           "WHERE t.Name = ? OR t.TravelerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, travelerInput);
            preparedStatement.setString(2, travelerInput);

            ResultSet resultSet = preparedStatement.executeQuery();

            // 조회 결과 출력
            while (resultSet.next()) {
                System.out.println("여행자 이름: " + resultSet.getString("Name"));
                System.out.println("여행 일정 ID: " + resultSet.getString("ItineraryID"));
                System.out.println("출발일: " + resultSet.getString("StartDate"));
                System.out.println("종료일: " + resultSet.getString("EndDate"));
                System.out.println("여행지: " + resultSet.getString("Region") + ", " + resultSet.getString("City"));

                System.out.println();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

