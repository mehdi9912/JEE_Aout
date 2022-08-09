package be.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.PoliceArea;


public class PoliceAreaDAO implements DAO<PoliceArea> {

	@Override
	public String insert(PoliceArea obj) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(PoliceArea obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PoliceArea find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PoliceArea> findAll() {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<PoliceArea> policeAreas=new ArrayList<PoliceArea>();
		PoliceArea policeArea = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"SELECT * FROM police_area");
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				policeArea = new PoliceArea();
				policeArea.setId(resultSet.getInt("id"));
				policeArea.setAreaName(resultSet.getString("name"));
				policeAreas.add(policeArea);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans policeareadao de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return policeAreas;
	}

}
