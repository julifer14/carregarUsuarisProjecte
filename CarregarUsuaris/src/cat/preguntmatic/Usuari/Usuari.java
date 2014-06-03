package cat.preguntmatic.Usuari;

import java.io.Serializable;
import java.lang.String;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.*;

import sun.security.util.Length;

/**
 * Entity implementation class for Entity: Usuari
 * 
 */
@Entity
@Table(name = "auth_user")
public class Usuari implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String password;
	private String username;
	private String first_name;
	private String last_name;
	private String email;
	private int is_staff;
	private int is_active;
	private int is_superuser;
	private String last_login;
	private String date_joined;
	private static final long serialVersionUID = 1L;

	public Usuari() {
		super();
	}

	private String generarData() {
		Calendar c = new GregorianCalendar();
		int any = c.get(Calendar.YEAR);
		int mes = c.get(Calendar.MONTH);
		int dia = c.get(Calendar.DAY_OF_MONTH);
		int hora = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		return (any + "-" + mes + "-" + dia + " " + hora + ":" + min + ":" + sec);
	}

	public Usuari(String usuari, String nom) {
		String[] noms = nom.split(" ");
		String cog1 = "";
		String cog2 = "";
		if (noms.length > 3) {
			cog1 = noms[noms.length - 2];
			cog2 = noms[noms.length - 1];
			last_name = cog1 + " " + cog2;
			for (int i = 0; i < noms.length - 2; i++) {
				if (first_name == "") {
					first_name = noms[i];
				} else {
					first_name = first_name + " " + noms[i];
				}
			}

		} else {
			first_name = noms[0];
			last_name="Tonto";
			if (noms.length > 1) {
				last_name = noms[1];
			}
			if (noms.length > 2) {
				last_name = last_name + " " + noms[2];
			}
		}
		username = usuari;
		password = "pbkdf2_sha256$12000$kx43KfYpN9i7$PXSTn7lnP6tXMSe1QjChJho5b0KhtDx1BNu1QbfJIIw=";
		email = usuari + "@example.com";
		is_staff = 0;
		is_active = 1;
		is_superuser = 0;
		last_login = generarData();
		date_joined = generarData();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIs_staff() {
		return this.is_staff;
	}

	public void setIs_staff(int is_staff) {
		this.is_staff = is_staff;
	}

	public int getIs_active() {
		return this.is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}

	public int getIs_superuser() {
		return this.is_superuser;
	}

	public void setIs_superuser(int is_superuser) {
		this.is_superuser = is_superuser;
	}

	public String getLast_login() {
		return this.last_login;
	}

	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

	public String getDate_joined() {
		return this.date_joined;
	}

	public void setDate_joined(String date_joined) {
		this.date_joined = date_joined;
	}

}
