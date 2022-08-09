package be.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.project.utils.Utils;

public class InsuranceDAO {
	public boolean getInsuranceOrder(String id) {
		Connection conn=DatabaseConnection.getConnection();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT * from insurance where licenseplate=?"
					);
			preparedStatement.setString(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
					char in_order =resultSet.getString("in_order").charAt(0);
					if(Utils.charToBoolean(in_order) == false) {
						return false;
					}
				}			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return true;
	}

}
