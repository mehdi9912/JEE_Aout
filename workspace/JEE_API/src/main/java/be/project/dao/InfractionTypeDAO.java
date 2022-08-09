package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.InfractionType;
import be.project.utils.Utils;

public class InfractionTypeDAO implements DAO<InfractionType> {

	@Override
	public String insert(InfractionType obj) {
		Connection conn=DatabaseConnection.getConnection();
		int idCreated;
		try {
				CallableStatement sql = conn.prepareCall("{call insert_infraction_type(?,?,?)}");
				sql.setString(1, obj.getType());
				sql.setDouble(2, obj.getAmount());
				sql.registerOutParameter(3, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				idCreated = sql.getInt(3);
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
				CallableStatement sql = conn.prepareCall("{call delete_infraction_type(?,?)}");
				sql.setInt(1, idInt);
				sql.registerOutParameter(2, java.sql.Types.NUMERIC);
				sql.execute();
				success=Utils.intToBoolean(sql.getInt(2));
				sql.close();
		}catch(SQLException e) {
			System.out.println("Excepption dans delete infractionTypeDAO Api "+ e.getMessage());
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
	public int update(InfractionType obj) {
		Connection conn=DatabaseConnection.getConnection();
		int exception = -1;
		try {
				CallableStatement sql = conn.prepareCall("{call update_infraction_type(?,?,?,?)}");
				sql.setInt(1, obj.getId());
				sql.setString(2, obj.getType());
				sql.setDouble(3, obj.getAmount());
				sql.registerOutParameter(4, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				exception=sql.getInt(4);
				sql.close();
		}catch(SQLException e) {
			System.out.println("Excepption dans update infractionTypeDAO Api "+ e.getMessage());
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
	public InfractionType find(String id) {
		Connection conn=DatabaseConnection.getConnection();
		InfractionType infraction=null;
		try {
			int idInt = Integer.valueOf(id);
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT * from infraction where infraction_id=?"
					);
			preparedStatement.setInt(1, idInt);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int infractionId = resultSet.getInt("infraction_id");
				String type=resultSet.getString("infraction_type");
				double amount=resultSet.getDouble("amount");
				infraction = new InfractionType(infractionId,type, amount);
			}
		} catch (Exception e) {
			System.out.println("Excpetion dans infractionDAO de l'API" + e.getMessage());
		}
		finally {
			try {
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return infraction;
	}

	@Override
	public ArrayList<InfractionType> findAll() {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<InfractionType> infractions =new ArrayList<InfractionType>();
		InfractionType infraction;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"select * from infraction"
				);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id  = resultSet.getInt("infraction_id");
				String type = resultSet.getString("infraction_type");
				double amount = resultSet.getDouble("amount");
				infraction = new InfractionType(id, type, amount);
				infractions.add(infraction);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans infractiontypeDAO de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return infractions;
	}

}
