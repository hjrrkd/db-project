// 4번메뉴: 숙소 예약
// 사용자로부터 여행자의 이름, 예약할 숙소, 예약일, 금액을 입력받아 Reservation 테이블에 새로운 레코드를 추가

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ReservationMenu {

    public static void handleReservationMenu(Connection connection) {
        System.out.println("숙소 예약 메뉴를 선택하셨습니다.");

        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 여행자의 이름 입력 받기
        System.out.print("여행자의 이름을 입력하세요: ");
        String travelerName = scanner.nextLine().trim();

        // 사용자로부터 예약 정보 입력 받기
        System.out.print("예약할 숙소를 입력하세요: ");
        String accommodation = scanner.nextLine().trim();

        System.out.print("예약일을 입력하세요 (YYYY-MM-DD): ");
        String reservationDate = scanner.nextLine().trim();

        System.out.print("금액을 입력하세요: ");
        int amount = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리

        // 예약 정보 추가
        try {
            // Traveler 테이블에서 여행자의 ID를 조회
            String travelerQuery = "SELECT TravelerID FROM Traveler WHERE Name = ?";
            PreparedStatement travelerStatement = connection.prepareStatement(travelerQuery);
            travelerStatement.setString(1, travelerName);

            int travelerID = -1; // 초기값 설정
            if (travelerStatement.execute()) {
                var resultSet = travelerStatement.getResultSet();
                if (resultSet.next()) {
                    travelerID = resultSet.getInt("TravelerID");
                }
            }

            if (travelerID != -1) {
                // 예약 정보 추가 쿼리 작성
                String reservationQuery = "INSERT INTO Reservation (TravelerID, Accommodation, ReservationDate, Amount) VALUES (?, ?, ?, ?)";
                PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery);
                reservationStatement.setInt(1, travelerID);
                reservationStatement.setString(2, accommodation);
                reservationStatement.setString(3, reservationDate);
                reservationStatement.setInt(4, amount);

                // 쿼리 실행
                int rowsAffected = reservationStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("숙소 예약이 성공적으로 완료되었습니다.");
                    System.out.println("------------");
                } else {
                    System.out.println("숙소 예약에 실패했습니다.");
                    System.out.println("------------");
                }
            } else {
                System.out.println("해당 여행자가 존재하지 않습니다.");
                System.out.println("------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

