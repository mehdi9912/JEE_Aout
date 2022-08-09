package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.project.enumerations.FineStatus;
import be.project.models.Policeman;
import be.project.models.Fine;
import be.project.models.InfractionType;
import be.project.models.VehiculeType;
import be.project.utils.Utils;

public class FineDAO implements DAO<Fine> {

	@Override
	public String insert(Fine obj) {
		Connection conn=DatabaseConnection.getConnection();
		int idCreated;
		try {
				Timestamp timestamp = Timestamp.valueOf(obj.getTimestamp());
				CallableStatement sql = conn.prepareCall("{call insert_fine(?,?,?,?,?,?,?,?,?,?)}");
				sql.setInt(1, obj.getInfractionType().getId());
				sql.setInt(2, obj.getVehiculeType().getId());
				sql.setString(3, obj.getCivilianFirstname());
				sql.setString(4, obj.getCivilianLastname());
				sql.setString(5, obj.getComment());
				sql.setString(6, obj.getLicensePlate());
				sql.setString(7, obj.getPoliceman().getSerialNumber());
				sql.setTimestamp(8, timestamp);
				sql.setDouble(9, obj.getTotalPrice());
				sql.registerOutParameter(10, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				idCreated = sql.getInt(10);
				sql.close();
		}catch(SQLException e) {	
			System.out.println(e.getMessage());
			return "";
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Exception dans insert API" +e.getMessage());
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
				CallableStatement sql = conn.prepareCall("{call delete_fine(?,?)}");
				sql.setInt(1, idInt);
				sql.registerOutParameter(2, java.sql.Types.NUMERIC);
				sql.execute();
				success=Utils.intToBoolean(sql.getInt(2));
				sql.close();
		}catch(SQLException e) {
			System.out.println("Excepption dans delete fineDAO Api "+ e.getMessage());
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
	public int update(Fine obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Fine find(String id) {
		Connection conn=DatabaseConnection.getConnection();
		Fine fine=null;
		try {
			int idInt = Integer.valueOf(id);
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT * from fine where fine_id=?"
					);
			preparedStatement.setInt(1, idInt);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int fineId  = resultSet.getInt("fine_id");
				LocalDateTime timestamp  = resultSet.getTimestamp("fine_timestamp").toLocalDateTime();
				double price  = resultSet.getDouble("total_price");
				String firstname  = resultSet.getString("civilian_firstname");
				String lastname  = resultSet.getString("civilian_lastname");
				String comment  = resultSet.getString("fine_comment");
				FineStatus status  =FineStatus.valueOf(resultSet.getString("fine_status")) ;
				String licensePlate  = resultSet.getString("licenseplate");
				int infractionId  = resultSet.getInt("infraction_id");
				String policemanId  = resultSet.getString("policeman_serialnumber");
				int vehiculeId  = resultSet.getInt("vehiculetype_id");
				VehiculeType vehicule = VehiculeType.getVehicule(String.valueOf(vehiculeId));
				InfractionType infraction = InfractionType.getInfraction(String.valueOf(infractionId));
				Policeman policeman = Policeman.getPoliceman(policemanId);
				fine = new Fine(fineId, timestamp, price, firstname, lastname, comment, status, vehicule, infraction, policeman,licensePlate);
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
		return fine;
	}

	@Override
	public ArrayList<Fine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Fine> findAllBySupervisedAgents(String serialNumber) {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<Fine> fines =new ArrayList<Fine>();
		Fine fine = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"select * from fine where policeman_serialnumber in "
				+ "(select policeman_serialnumber from policeman where chief_serialnumber=?) order by fine_id"
				);
			preparedStatement.setString(1, serialNumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int fineId  = resultSet.getInt("fine_id");
				LocalDateTime timestamp  = resultSet.getTimestamp("fine_timestamp").toLocalDateTime();
				double price  = resultSet.getDouble("total_price");
				String firstname  = resultSet.getString("civilian_firstname");
				String lastname  = resultSet.getString("civilian_lastname");
				String comment  = resultSet.getString("fine_comment");
				FineStatus status  =FineStatus.valueOf(resultSet.getString("fine_status")) ;
				String licensePlate  = resultSet.getString("licenseplate");
				int infractionId  = resultSet.getInt("infraction_id");
				String policemanId  = resultSet.getString("policeman_serialnumber");
				int vehiculeId  = resultSet.getInt("vehiculetype_id");
				VehiculeType vehicule = VehiculeType.getVehicule(String.valueOf(vehiculeId));
				InfractionType infraction = InfractionType.getInfraction(String.valueOf(infractionId));
				Policeman policeman = Policeman.getPoliceman(policemanId);
				
				fine = new Fine(fineId, timestamp, price, firstname, lastname, comment, status, vehicule, infraction, policeman,licensePlate);
				fines.add(fine);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans fineDAO de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return fines;
	}

	public int updateStatus(Fine fine) {
		Connection conn=DatabaseConnection.getConnection();
		int code=-1;
		try {
				CallableStatement sql = conn.prepareCall("{call update_fine_status(?,?,?)}");
				sql.setInt(1, fine.getFineId());
				sql.setString(2, String.valueOf(fine.getStatus()) );
				sql.registerOutParameter(3, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				code=sql.getInt(3);
				sql.close();
		}catch(SQLException e) {
			System.out.println("Exception dans update fineDAO Api "+ e.getMessage());
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return code;
	}

	public ArrayList<Fine> findAllValidatedFines() {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<Fine> fines =new ArrayList<Fine>();
		Fine fine = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"select * from fine where fine_status='validated'"
				);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int fineId  = resultSet.getInt("fine_id");
				LocalDateTime timestamp  = resultSet.getTimestamp("fine_timestamp").toLocalDateTime();
				double price  = resultSet.getDouble("total_price");
				String firstname  = resultSet.getString("civilian_firstname");
				String lastname  = resultSet.getString("civilian_lastname");
				String comment  = resultSet.getString("fine_comment");
				FineStatus status  =FineStatus.valueOf(resultSet.getString("fine_status")) ;
				String licensePlate  = resultSet.getString("licenseplate");
				int infractionId  = resultSet.getInt("infraction_id");
				String policemanId  = resultSet.getString("policeman_serialnumber");
				int vehiculeId  = resultSet.getInt("vehiculetype_id");
				VehiculeType vehicule = VehiculeType.getVehicule(String.valueOf(vehiculeId));
				InfractionType infraction = InfractionType.getInfraction(String.valueOf(infractionId));
				Policeman policeman = Policeman.getPoliceman(policemanId);
				
				fine = new Fine(fineId, timestamp, price, firstname, lastname, comment, status, vehicule, infraction, policeman,licensePlate);
				fines.add(fine);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans fineDAO de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return fines;
	}

}
