// 5번메뉴: 예약 정보 조회
// 사용자로부터 여행자의 이름을 입력받아 해당 여행자의 예약 정보를 조회

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReservationInfoMenu {

    public static void handleReservationInfoMenu(Connection connection) {
        System.out.println("예약 정보 조회 메뉴를 선택하셨습니다.");

        // 사용자로부터 여행자의 이름 입력 받기
        Scanner scanner = new Scanner(System.in);
        System.out.print("여행자의 이름을 입력하세요: ");
        String travelerName = scanner.nextLine();

        // TravelerID 조회 쿼리 작성
        String travelerIdQuery = "SELECT TravelerID FROM Traveler WHERE Name = ?";

        try {
            // PreparedStatement를 사용하여 TravelerID 조회
            PreparedStatement travelerIdStatement = connection.prepareStatement(travelerIdQuery);
            travelerIdStatement.setString(1, travelerName);
            ResultSet travelerIdResultSet = travelerIdStatement.executeQuery();

            int travelerId = -1;

            // TravelerID가 존재하는 경우 값을 얻어옴
            if (travelerIdResultSet.next()) {
                travelerId = travelerIdResultSet.getInt("TravelerID");
            } else {
                System.out.println("해당하는 여행자의 예약 정보가 없습니다.");
                return;
            }

            // 예약 정보 조회 쿼리 작성
            String reservationQuery = "SELECT * FROM Reservation WHERE TravelerID = ?";

            // PreparedStatement를 사용하여 예약 정보 조회
            PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery);
            reservationStatement.setInt(1, travelerId);
            ResultSet resultSet = reservationStatement.executeQuery();

            // 조회 결과 출력
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("ReservationID");
                String accommodation = resultSet.getString("Accommodation");
                String reservationDate = resultSet.getString("ReservationDate");
                int amount = resultSet.getInt("Amount");

                System.out.println("예약 ID: " + reservationId);
                System.out.println("숙소: " + accommodation);
                System.out.println("예약일: " + reservationDate);
                System.out.println("금액: " + amount);
                System.out.println("------------");
            }

            // 자원 해제
            travelerIdResultSet.close();
            travelerIdStatement.close();
            resultSet.close();
            reservationStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

