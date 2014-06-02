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
/*
 * Programa per a la carrega d'usuaris a la BDD del projecte.
 * 
 */
public class Principal {
	public static void main(String[] args) throws IOException  {
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
			
			case 1:
				p.veureUsuaris();
				break;
			case 2:
				p.afegirUsuaris();
				break;
			case 3:
				sortir = true;
				break;
			default:
				break;
			}
		}

	}
	
		

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

	public void afegirUsuaris() throws IOException {
		String url = "http://www.bdfutbol.com/es/a/j__madrid.html";
		Document doc = Jsoup.connect(url).get();
		Elements taula = doc.select("#taul");
		Elements files = taula.select("tr");
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("CarregarUsuaris");
		// I crear un entity manager
		EntityManager em = emf.createEntityManager();
		//Comencem per 3 perque ja hi havia 3 usuaris
		for (int i =3; i < files.size(); i++) {
			Element e = files.get(i);
			Elements tds = e.select("td");
			String user = tds.get(0).text() + i;
			String nom = tds.get(1).text();
			String grup = "alumne";
			System.out.println(nom);
			Usuari u = new Usuari(user, nom);
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();

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
