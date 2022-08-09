package be.project.dao;

import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.project.models.Admin;

public class AdminDAO implements DAO<Admin> {

	@Override
	public String insert(Admin obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Admin obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Admin find(String serialNumber) {
		Connection conn=DatabaseConnection.getConnection();
		Admin admin=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT serialNumber,firstname,lastname FROM admin WHERE serialNumber=?"
					);
			preparedStatement.setString(1, serialNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				String serialNumberFromDB=resultSet.getString("serialNumber");
				String firstname=resultSet.getString("firstname");
				String lastname=resultSet.getString("lastname");
				admin=new Admin(serialNumberFromDB,firstname,lastname, null);
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
		return admin;
	}

	@Override
	public ArrayList<Admin> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean login(String serialNumber, String pwd) {
		Connection conn=DatabaseConnection.getConnection();
		String hash_password = null; 
		String password="";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT serialNumber,password FROM admin WHERE serialNumber=?");
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
