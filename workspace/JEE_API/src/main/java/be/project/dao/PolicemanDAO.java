package be.project.dao;

import java.util.ArrayList;

import be.project.models.Chief;
import be.project.models.PoliceArea;
import be.project.models.Policeman;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PolicemanDAO implements DAO<Policeman> {
	@Override
	public String insert(Policeman obj) {
		return null;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Policeman obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Policeman find(String serialNumber) {
		Connection conn=DatabaseConnection.getConnection();
		Policeman policeman=null;
		PoliceArea policeArea=null;
		Chief chief=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT p.policeman_serialNumber, p.policeman_firstname, p.policeman_lastname, p.police_area_id,"
					+ " po.name, c.chief_serialNumber, c.chief_lastname, c.chief_firstname "
					+ "from policeman p, police_area po, chief c where p.police_area_id = po.id "
					+ "and p.chief_serialNumber = c.chief_serialNumber and p.policeman_serialNumber=?"
					);
			preparedStatement.setString(1, serialNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
					String policemanSerialNumber =resultSet.getString("policeman_serialNumber");
					String policemanFirstname=resultSet.getString("policeman_firstname");
					String policemanLastname=resultSet.getString("policeman_lastname");
					String policeAreaName = resultSet.getString("name");
					int policeAreaId = resultSet.getInt("police_area_id");
					String chiefSerialNumber =resultSet.getString("chief_serialNumber");
					String chiefFirstname=resultSet.getString("chief_firstname");
					String chiefLastname=resultSet.getString("chief_lastname");
					policeArea = new PoliceArea(policeAreaId, policeAreaName);
					chief= new Chief(chiefSerialNumber,chiefFirstname,chiefLastname,null,null);
					policeman=new Policeman(policemanSerialNumber,policemanFirstname,policemanLastname, null, policeArea,chief);
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
		return policeman;
	}

	@Override
	public ArrayList<Policeman> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean login(String serialNumber, String pwd) {
		Connection conn=DatabaseConnection.getConnection();
		String hash_password = null; 
		String password="";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT policeman_serialNumber,password FROM policeman WHERE policeman_serialNumber=?");
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
