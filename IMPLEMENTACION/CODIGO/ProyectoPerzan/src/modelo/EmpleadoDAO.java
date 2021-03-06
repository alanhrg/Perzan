package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoDAO implements iOp{
	boolean result;
	private int contt;
	private int dato;
	
	public EmpleadoVO[] getDatos(){
		int contador;
		EmpleadoVO[] datos;
		Conexion conex = new Conexion();
		if(conex.conectado()){
			try{
				PreparedStatement count = conex.getConnection().prepareStatement("SELECT COUNT(*) FROM empleado");
				ResultSet cont = count.executeQuery();
				if(cont.next()){
					contt = cont.getInt(1);
				}
				count.close();
				PreparedStatement consulta = conex.getConnection().prepareStatement("SELECT * FROM empleado");
				ResultSet res = consulta.executeQuery();
				datos = new EmpleadoVO[contt];
					contador = 0;
					while(res.next()){
						int id= res.getInt("id");
						String nombre = res.getString("nombre");
						String apPaterno = res.getString("apPaterno");
						String apMaterno = res.getString("apMaterno");
						String direccion = res.getString("direccion");
						String telefono = res.getString("telefono");
						String usuario = res.getString("usuario");
						String password = res.getString("pass");
						EmpleadoVO empleadoVO = new EmpleadoVO(id, nombre, apPaterno, apMaterno, direccion, telefono, usuario, password);
						datos[contador] = empleadoVO;
						contador++;
					}
				consulta.close();
				conex.desconectar();
				return datos;
			}		
			catch(SQLException e){
					System.out.println(e);
			}
		}
		return null;
	}
	public EmpleadoVO lastInsert(){		
		Conexion conex = new Conexion();
		if(conex.conectado()){
			try{
				PreparedStatement consulta = conex.getConnection().prepareStatement("SELECT * FROM empleado WHERE `id` = ?");
				consulta.setInt(1, dato);
				ResultSet res = consulta.executeQuery();
				if(res.next()){
					int id= res.getInt("id");
					String nombre = res.getString("nombre");
					String apPaterno = res.getString("apPaterno");
					String apMaterno = res.getString("apMaterno");
					String direccion = res.getString("direccion");
					String telefono = res.getString("telefono");
					String usuario = res.getString("usuario");
					String password = res.getString("pass");
					EmpleadoVO empleadoVO = new EmpleadoVO(id, nombre, apPaterno, apMaterno, direccion, telefono, usuario, password);
					return empleadoVO;
				}
				consulta.close();
				conex.desconectar();
				}		
			catch(SQLException e){
					System.out.println(e);
			}
		}
		
		return null;
	}
	public boolean eliminar(int id) {
		boolean result = false;
		Conexion conex = new Conexion();
		if(conex.conectado()){
			try{
				PreparedStatement consulta = conex.getConnection().prepareStatement("DELETE FROM empleado WHERE id = ?");
				consulta.setInt(1, id);
				int res = consulta.executeUpdate();
				if(res > 0){
					result = true;
				}
				//}
				consulta.close();
				conex.desconectar();
				}		
			catch(SQLException e){
					System.out.println(e);
			}
		}
		
		if(result){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public boolean registrar(Object obj) {
		EmpleadoVO empleadoVO = new EmpleadoVO();
		empleadoVO = (EmpleadoVO) obj;
		Conexion conex = new Conexion();
		if(conex.conectado()){
			try{
				PreparedStatement consulta = conex.getConnection().prepareStatement("INSERT INTO empleado VALUES (?,?,?,?,?,?,?,?)");
				consulta.setNull(1,0);
				consulta.setString(2,empleadoVO.getNombre());
				consulta.setString(3,empleadoVO.getApPaterno());
				consulta.setString(4, empleadoVO.getApMaterno());
				consulta.setString(5, empleadoVO.getDireccion());
				consulta.setString(6, empleadoVO.getTelefono());
				consulta.setString(7, empleadoVO.getUsuario());
				consulta.setString(8, empleadoVO.getPassword());
				int res = consulta.executeUpdate();
				if(res > 0){
					result= true;	
				}
				else{
					result = false;
				}
				consulta.close();
				PreparedStatement last = conex.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet lastIn = last.executeQuery();
				if(lastIn.next()){
					dato = lastIn.getInt(1);
					System.out.println(dato);
				}
				last.close();
				conex.desconectar();
				}		
				catch(SQLException e){
					System.out.println(e);}
		}
		if(result){
			return true;
		}
		else{
			return false;
		}	
	}
	@Override
	public boolean modificar(Object obj) {
		EmpleadoVO empleadoVO = new EmpleadoVO();
		empleadoVO = (EmpleadoVO) obj;
		Conexion conex = new Conexion();
		if(conex.conectado()){
			try{
				PreparedStatement consulta = conex.getConnection().prepareStatement("UPDATE empleado SET id = ?, nombre = ?, appaterno = ?, apMaterno = ?, direccion = ?, telefono = ?, usuario = ?, pass = ? WHERE Id= ?");
				consulta.setInt(1, empleadoVO.getId());
				consulta.setString(2,empleadoVO.getNombre());
				consulta.setString(3,empleadoVO.getApPaterno());
				consulta.setString(4, empleadoVO.getApMaterno());
				consulta.setString(5, empleadoVO.getDireccion());
				consulta.setString(6, empleadoVO.getTelefono());
				consulta.setString(7, empleadoVO.getUsuario());
				consulta.setString(8, empleadoVO.getPassword());
				consulta.setInt(9, empleadoVO.getId());
				
				int res = consulta.executeUpdate();
				if(res > 0){
					result= true;	
				}
				else{
					result = false;
				}
				consulta.close();
				conex.desconectar();
				}		
				catch(SQLException e){
					System.out.println(e);}
		}
		if(result){
			return true;
		}
		else{
			return false;
		}		
	}
}