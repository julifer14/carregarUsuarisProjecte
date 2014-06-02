package cat.preguntmatic.Usuari;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Principal {
	private int numUsuaris;

	public static void main(String[] args) throws IOException {
		/*
		 * Date d = new Date();
		 * System.out.println("2014"+"-"+d.getMonth()+1+"-"+
		 * d.getDay()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds());
		 */
		Principal p = new Principal();
		String data = p.getData();
		Scanner lector = new Scanner(System.in);

		boolean sortir = false;
		while (!sortir) {

			System.out.println("1.- Veure usuaris");
			System.out.println("2.- Carregar usuaris");
			System.out.println("3.- Sortir");
			int opcio = lector.nextInt();

			switch (opcio) {
			case 0:
				//p.comptarUsuaris();
				break;
			case 1:
				p.veureUsuaris();
				break;
			case 2:
				System.out.println("Tria l'equip de la llista:");
				System.out.println("1.- Madrid");
				System.out.println("2.- Barcelona");
				int escollit = lector.nextInt();
				String[] equips = {
						"http://www.bdfutbol.com/es/a/j__madrid.html",
						"http://www.bdfutbol.com/es/a/j___barcelona.html" };
				String urlEscollida = "http://www.bdfutbol.com/es/a/j___girona.html";
				try {
					urlEscollida = equips[escollit - 1];
					System.out.println("...." + urlEscollida);
				} catch (Exception e) {
					System.out
							.println("Ep! No existeix l'equip! Agafem per defecte el Girona");
				}

				p.afegirUsuaris(urlEscollida);
				break;
			case 3:
				sortir = true;
				break;
			default:
				break;
			}
		}

	}
	/*
	private void comptarUsuaris() {
		// ---------Connexió BDD--------------------
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		Connection conn = null;
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://192.168.2.102:3306/preguntmatic?user=preguntmatic&password=ies");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			Statement est = conn.createStatement();
			ResultSet resultat = est
					.executeQuery("Select count(*) from auth_user");
			while (resultat.next()) {
				int num = resultat.getInt(1);
				numUsuaris = num;
				System.out.println(num);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}*/

	private String getData() {
		Calendar c = new GregorianCalendar();
		int any = c.get(Calendar.YEAR);
		int mes = c.get(Calendar.MONTH);
		int dia = c.get(Calendar.DAY_OF_MONTH);
		int hora = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		return (any + "-" + mes + "-" + dia + " " + hora + ":" + min + ":" + sec);
	}

	public void afegirUsuaris(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();

			Elements taula = doc.select("#taul");
			Elements files = taula.select("tr");
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("CarregarUsuaris");
			// I crear un entity manager
			EntityManager em = emf.createEntityManager();
			for (int i = 0; i < files.size(); i++) {
				Element e = files.get(i);
				Elements tds = e.select("td");
				String user = tds.get(0).text() + i;
				String nom = tds.get(1).text();
				String grup = "alumne";
				//System.out.println(nom);
				Usuari u = new Usuari(user, nom);
				em.getTransaction().begin();
				em.persist(u);
				em.getTransaction().commit();
				numUsuaris++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void veureUsuaris() {
		// Primer crear un EntityManagerFactory amb el nom que se li dóna al
		// fitxer persistence.xml
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("CarregarUsuaris");
		// I crear un entity manager
		EntityManager em = emf.createEntityManager();
		TypedQuery<Usuari> u = em.createQuery("SELECT u FROM Usuari u",
				Usuari.class);
		List<Usuari> usuaris = u.getResultList();
		for (Usuari user : usuaris) {
			System.out.println(user.getUsername());
		}
	}

}
