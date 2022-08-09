package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.VehiculeType;
import be.project.utils.Utils;

public class VehiculeTypeDAO implements DAO<VehiculeType>  {

	@Override
	public String insert(VehiculeType obj) {
		Connection conn=DatabaseConnection.getConnection();
		int idCreated;
		try {
				CallableStatement sql = conn.prepareCall("{call insert_vehicule_type(?,?)}");
				sql.setString(1, obj.getType());
				sql.registerOutParameter(2, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				idCreated = sql.getInt(2);
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
		return String.valueOf(idCreated);
	}

	@Override
	public boolean delete(String id) {
		Connection conn=DatabaseConnection.getConnection();
		int idInt = Integer.valueOf(id);
		boolean success = false;
		try {
				CallableStatement sql = conn.prepareCall("{call delete_vehicule_type(?,?)}");
				sql.setInt(1, idInt);
				sql.registerOutParameter(2, java.sql.Types.NUMERIC);
				sql.execute();
				success=Utils.intToBoolean(sql.getInt(2));
				sql.close();
		}catch(SQLException e) {
			System.out.println("Excepption dans delete vehiculeTypeDAO Api "+ e.getMessage());
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return success;
	}

	@Override
	public int update(VehiculeType obj) {
		Connection conn=DatabaseConnection.getConnection();
		int exception = -1;
		try {
				CallableStatement sql = conn.prepareCall("{call update_vehicule_type(?,?,?)}");
				sql.setInt(1, obj.getId());
				sql.setString(2, obj.getType());
				sql.registerOutParameter(3, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				exception=sql.getInt(3);
				sql.close();
		}catch(SQLException e) {
			System.out.println("Excepption dans update vehiculeTypeDAO Api "+ e.getMessage());
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return exception;
	}

	@Override
	public VehiculeType find(String id) {
		Connection conn=DatabaseConnection.getConnection();
		VehiculeType vehicule=null;
		try {
			int idInt = Integer.valueOf(id);
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT * from vehicule_type where id=?"
					);
			preparedStatement.setInt(1, idInt);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int vehiculeId = resultSet.getInt("id");
				String type=resultSet.getString("type");
				vehicule = new VehiculeType(vehiculeId,type);
			}
		} catch (Exception e) {
			System.out.println("Excpetion dans vehiculeDAO de l'API" + e.getMessage());
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return vehicule;
	}

	@Override
	public ArrayList<VehiculeType> findAll() {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<VehiculeType> vehicules =new ArrayList<VehiculeType>();
		VehiculeType vehicule = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"select * from vehicule_type"
				);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id  = resultSet.getInt("id");
				String type = resultSet.getString("type");
				vehicule = new VehiculeType(id, type);
				vehicules.add(vehicule);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans vehiculetypeDAO de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return vehicules;
	}

}
