package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.Collector;
import be.project.models.PoliceArea;


public class CollectorDAO implements DAO<Collector> {

	@Override
	public String insert(Collector obj) {
		Connection conn=DatabaseConnection.getConnection();
		String serialNumberCreated="";
		try {
			CallableStatement sql = conn.prepareCall("{call insert_collector(?,?,?,?,?)}");
			sql.setString(1, obj.getFirstname());
			sql.setString(2, obj.getLastname());
			sql.setString(3, obj.getPassword());
			sql.setInt(4,((Collector) obj).getPoliceArea().getId());
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
	public int update(Collector obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collector find(String serialNumber) {
		Connection conn=DatabaseConnection.getConnection();
		Collector collector=null;
		PoliceArea policeArea=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT collector_serialNumber, collector_firstname, collector_lastname, id, name "
					+ "FROM collector, police_area where collector.police_area_id = police_area.id and collector_serialNumber=?"
					);
			preparedStatement.setString(1, serialNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				String serialNumberFromDB=resultSet.getString("collector_serialNumber");
				String firstname=resultSet.getString("collector_firstname");
				String lastname=resultSet.getString("collector_lastname");
				String policeAreaName = resultSet.getString("name");
				int policeAreaId = resultSet.getInt("id");
				policeArea = new PoliceArea(policeAreaId, policeAreaName);
				collector=new Collector(serialNumberFromDB,firstname,lastname, null, policeArea);
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
		return collector;
	}

	@Override
	public ArrayList<Collector> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean login(String serialNumber, String pwd) {
		Connection conn=DatabaseConnection.getConnection();
		String hash_password = null; 
		String password="";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT collector_serialNumber,password FROM collector WHERE collector_serialNumber=?");
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
