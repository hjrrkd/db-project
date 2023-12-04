// 2번메뉴: 여행자의 여행 일정 조회 및 수정
// Traveler,Destination,Itinerary 테이블을 활용하여 여행 일정을 조회하고 수정
// 사용자로부터 여행자의 이름을 입력받아 해당 여행자의 여행 일정을 조회하고, 필요에 따라 수정 가능

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TravelScheduleMenu {

    public static void handleTravelScheduleMenu(Connection connection) {
        System.out.println("여행 일정 조회 및 수정 메뉴를 선택하셨습니다.");

        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 여행자의 이름 입력받기
        System.out.print("여행자의 이름을 입력하세요: ");
        String travelerName = scanner.nextLine().trim();

        // 여행 일정 조회
        try {
            String query = "SELECT t.Name, i.ItineraryID, i.StartDate, i.EndDate, d.Region, d.City, d.Accommodation " +
                           "FROM Traveler t " +
                           "JOIN Itinerary i ON t.TravelerID = i.TravelerID " +
                           "JOIN Destination d ON i.DestinationID = d.DestinationID " +
                           "WHERE t.Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, travelerName);

            ResultSet resultSet = preparedStatement.executeQuery();

            // 조회 결과 출력
            while (resultSet.next()) {
                System.out.println("여행자 이름: " + resultSet.getString("Name"));
                System.out.println("여행 일정 ID: " + resultSet.getString("ItineraryID"));
                System.out.println("출발일: " + resultSet.getString("StartDate"));
                System.out.println("종료일: " + resultSet.getString("EndDate"));
                System.out.println("여행지: " + resultSet.getString("Region") + ", " + resultSet.getString("City"));
                System.out.println("숙소: " + resultSet.getString("Accommodation"));

                System.out.println();
            }

            // 여행 일정 수정 여부 확인
            System.out.print("여행 일정을 수정하시겠습니까? (Y/N): ");
            String modifyChoice = scanner.nextLine().trim().toUpperCase();

            if ("Y".equals(modifyChoice)) {
                System.out.println("여행 일정을 수정합니다.");

                // 사용자로부터 수정할 여행 일정 ID 입력 받기
                System.out.print("수정할 여행 일정의 ID를 입력하세요: ");
                int itineraryID = Integer.parseInt(scanner.nextLine());

                // 사용자로부터 수정할 정보 입력 받기
                System.out.print("출발일을 입력하세요: ");
                String startDate = scanner.nextLine().trim();

                System.out.print("종료일을 입력하세요: ");
                String endDate = scanner.nextLine().trim();

                // 여행 일정 업데이트 쿼리 작성
                String updateQuery = "UPDATE Itinerary " +
                        "SET StartDate = ?, EndDate = ? " +
                        "WHERE ItineraryID = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, startDate);
                updateStatement.setString(2, endDate);
                updateStatement.setInt(3, itineraryID);

                // 쿼리 실행
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("여행 일정이 성공적으로 수정되었습니다.");
                    System.out.println("------------");
                } else {
                    System.out.println("여행 일정 수정에 실패했습니다.");
                    System.out.println("------------");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


