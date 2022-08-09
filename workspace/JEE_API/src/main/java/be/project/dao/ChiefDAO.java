package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.Chief;
import be.project.models.PoliceArea;

public class ChiefDAO implements DAO<Chief> {

	@Override
	public String insert(Chief obj) {
		Connection conn=DatabaseConnection.getConnection();
		String serialNumberCreated="";
		try {
			CallableStatement sql = conn.prepareCall("{call insert_chief(?,?,?,?,?)}");
			sql.setString(1, obj.getFirstname());
			sql.setString(2, obj.getLastname());
			sql.setString(3, obj.getPassword());
			sql.setInt(4,((Chief) obj).getPoliceArea().getId());
			sql.registerOutParameter(5, java.sql.Types.VARCHAR);
			sql.executeUpdate();
			serialNumberCreated = sql.getString(5);
			sql.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return "";
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return serialNumberCreated;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Chief obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Chief find(String serialNumber) {
		Connection conn=DatabaseConnection.getConnection();
		Chief chief=null;
		PoliceArea policeArea=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT chief_serialNumber, chief_firstname, chief_lastname, id, name "
					+ "from chief, police_area where chief.police_area_id=police_area.id and chief_serialNumber=?"
					);
			preparedStatement.setString(1, serialNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				String serialNumberFromDB=resultSet.getString("chief_serialNumber");
				String firstname=resultSet.getString("chief_firstname");
				String lastname=resultSet.getString("chief_lastname");
				String policeAreaName = resultSet.getString("name");
				int policeAreaId = resultSet.getInt("id");
				policeArea = new PoliceArea(policeAreaId, policeAreaName);
				chief=new Chief(serialNumberFromDB,firstname,lastname, null,policeArea);
			}
		} catch (Exception e) {
			System.out.println("Excpetion dans ChiefDAO de l'API" + e.getMessage());
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return chief;
	}

	@Override
	public ArrayList<Chief> findAll() {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<Chief> chiefs=new ArrayList<Chief>();
		Chief chief = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"SELECT chief_serialNumber, chief_firstname, chief_lastname FROM chief");
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				chief = new Chief();
				chief.setSerialNumber(resultSet.getString("chief_serialNumber"));
				chief.setFirstname(resultSet.getString("chief_firstname"));
				chief.setLastname(resultSet.getString("chief_lastname"));
				chiefs.add(chief);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans chiefdao de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return chiefs;
	}

	public boolean login(String serialNumber, String pwd) {
		Connection conn=DatabaseConnection.getConnection();
		String hash_password = null; 
		String password="";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT chief_serialNumber,password FROM chief WHERE chief_serialNumber=?");
			preparedStatement.setString(1, serialNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				password = resultSet.getString("password");	
			}
			CallableStatement sql = conn.prepareCall("{call hash_password(?,?)}");
			sql.setString(1, pwd);
			sql.registerOutParameter(2, java.sql.Types.VARCHAR);
			sql.executeUpdate();
			hash_password = sql.getString(2);
			sql.close();
			if(hash_password.equals(password)) {
				return true;
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
		return false;
	}

}
