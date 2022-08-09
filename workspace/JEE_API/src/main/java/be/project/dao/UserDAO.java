package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.Chief;
import be.project.models.Collector;
import be.project.models.PoliceArea;
import be.project.models.Policeman;
import be.project.models.User;
import be.project.utils.Utils;

public class UserDAO implements DAO<User>  {

	@Override
	public String insert(User obj) {
		Connection conn=DatabaseConnection.getConnection();
		String serialNumberCreated="";
		try {
			if(obj instanceof Policeman) {
				CallableStatement sql = conn.prepareCall("{call insert_policeman(?,?,?,?,?,?)}");
				sql.setString(1, obj.getFirstname());
				sql.setString(2, obj.getLastname());
				sql.setString(3, obj.getPassword());
				sql.setInt(4,((Policeman) obj).getPoliceArea().getId());
				sql.setString(5, ((Policeman) obj).getChief().getSerialNumber());
				sql.registerOutParameter(6, java.sql.Types.VARCHAR);
				sql.executeUpdate();
				serialNumberCreated = sql.getString(6);
				sql.close();
			}
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
		Connection conn=DatabaseConnection.getConnection();
		boolean success = false;
		try {
				if(id.substring(0,2).equals("PO")) {
					CallableStatement sql = conn.prepareCall("{call delete_policeman(?,?)}");
					sql.setString(1, id);
					sql.registerOutParameter(2, java.sql.Types.NUMERIC);
					sql.execute();
					success=Utils.intToBoolean(sql.getInt(2));
					sql.close();
				}
				if(id.substring(0,2).equals("CH")) {
					CallableStatement sql = conn.prepareCall("{call delete_chief(?,?)}");					
					sql.setString(1, id);
					sql.registerOutParameter(2,java.sql.Types.NUMERIC);
					sql.execute();
					success=Utils.intToBoolean(sql.getInt(2));
					sql.close();
				}
				if(id.substring(0,2).equals("CO")) {
					CallableStatement sql = conn.prepareCall("{call delete_collector(?,?)}");	
					sql.setString(1, id);
					sql.registerOutParameter(2, java.sql.Types.NUMERIC);
					sql.execute();
					success=Utils.intToBoolean(sql.getInt(2));
					sql.close();
				}
		}catch(SQLException e) {
			System.out.println("Excepption dans delete userDAO Api "+ e.getMessage());
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
	public int update(User obj) {
		Connection conn=DatabaseConnection.getConnection();
		int exception = -1;
		try {
			if(obj instanceof Collector) {
				CallableStatement sql = conn.prepareCall("{call update_collector(?,?,?,?,?,?)}");
				sql.setString(1, obj.getSerialNumber());
				sql.setString(2, obj.getFirstname());
				sql.setString(3, obj.getLastname());
				sql.setString(4, obj.getPassword());
				sql.setInt(5, ((Collector) obj).getPoliceArea().getId());
				sql.registerOutParameter(6, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				exception=sql.getInt(6);
				sql.close();
			}
			if(obj instanceof Chief) {
				CallableStatement sql = conn.prepareCall("{call update_chief(?,?,?,?,?,?)}");
				sql.setString(1, obj.getSerialNumber());
				sql.setString(2, obj.getFirstname());
				sql.setString(3, obj.getLastname());
				sql.setString(4, obj.getPassword());
				sql.setInt(5, ((Chief) obj).getPoliceArea().getId());
				sql.registerOutParameter(6, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				exception=sql.getInt(6);
				sql.close();
			}
			if(obj instanceof Policeman) {
				CallableStatement sql = conn.prepareCall("{call update_policeman(?,?,?,?,?,?,?)}");
				sql.setString(1, obj.getSerialNumber());
				sql.setString(2, obj.getFirstname());
				sql.setString(3, obj.getLastname());
				sql.setString(4, obj.getPassword());
				sql.setInt(5, ((Policeman) obj).getPoliceArea().getId());
				sql.setString(6, ((Policeman) obj).getChief().getSerialNumber());
				sql.registerOutParameter(7, java.sql.Types.NUMERIC);
				sql.executeUpdate();
				exception=sql.getInt(7);
				sql.close();
			}
		}catch(SQLException e) {
			System.out.println("Excepption dans update userDAO Api "+ e.getMessage());
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
	public User find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> findAll() {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<User> users=new ArrayList<User>();
		User user = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"select chief_serialNumber as serialNumber, chief_firstname as firstname, chief_lastname as lastname, name from chief , police_area where chief.police_area_id=police_area.id "
				+ "union select collector_serialNumber, collector_firstname, collector_lastname, name from collector, police_area where collector.police_area_id=police_area.id "
				+ "union select policeman_serialNumber, policeman_firstname, policeman_lastname, name from policeman, police_area where policeman.police_area_id=police_area.id"
				);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				String serialNumber = resultSet.getString("serialNumber").toLowerCase();
				PoliceArea policeaArea = new PoliceArea();
				policeaArea.setAreaName(resultSet.getString("name"));
				
				if(serialNumber.substring(0,2).equals("co")) {
					Collector collector = new Collector();
					collector.setSerialNumber(serialNumber);
					collector.setLastname(resultSet.getString("lastname"));
					collector.setFirstname(resultSet.getString("firstname"));
					collector.setPoliceArea(policeaArea);
					user = collector;	
				}
				if(serialNumber.substring(0,2).equals("ch")) {
					Chief chief = new Chief();
					chief.setSerialNumber(serialNumber);
					chief.setLastname(resultSet.getString("lastname"));
					chief.setFirstname(resultSet.getString("firstname"));
					chief.setPoliceArea(policeaArea);
					user = chief;
				}
				if(serialNumber.substring(0,2).equals("po")) {
					Policeman policeman = new Policeman();
					policeman.setSerialNumber(serialNumber);
					policeman.setLastname(resultSet.getString("lastname"));
					policeman.setFirstname(resultSet.getString("firstname"));
					policeman.setPoliceArea(policeaArea);
					user = policeman;
				}
				users.add(user);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans userdao de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return users;
	}

}
